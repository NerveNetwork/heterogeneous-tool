/**
 * MIT License
 * <p>
 * Copyright (c) 2017-2018 nuls.io
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package network.nerve;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import network.nerve.core.crypto.HexUtil;
import network.nerve.heterogeneous.core.NulsWalletApi;
import network.nerve.heterogeneous.model.ContractResultSimpleDto;
import network.nerve.heterogeneous.model.HtgSendTransactionPo;
import network.nerve.heterogeneous.utils.HtgCommonTools;
import network.nerve.heterogeneous.utils.JSONUtils;
import network.nerve.heterogeneous.utils.NulsContractUtil;
import network.nerve.heterogeneous.utils.RpcResult;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: PierreLuo
 * @date: 2025/11/7
 */
public class ItacToolTest {

    NulsWalletApi nulsWalletApi;

    protected String multySignContractAddress;
    protected String from;
    protected String fromEvm;
    protected String fromPriKey;
    protected String erc20Address;
    protected int erc20Decimals;

    String USD18 = "ITACdAD3G85TMPwSG8Cu9VBMWCcJmtAXdzx6hD";
    @BeforeClass
    public static void initClass() {
        ObjectMapper objectMapper = JSONUtils.getInstance();
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Before
    public void before() {
        // 初始化walletApi
        this.nulsWalletApi = new NulsWalletApi("ITAC", "ITAC", "https://api.itac.club/", 101, 18, BigDecimal.valueOf(25).movePointRight(10).longValue(), "ITAC");
        this.multySignContractAddress = "ITACdAD3G5f3XPxudkzKeJVN7371nP5Ng2HgFx";
    }

    private void updateAddr() {
        from = NulsContractUtil.genNulsAddressByCompressedPublickey(
                nulsWalletApi.chainId(),
                nulsWalletApi.addressPrefix(),
                network.nerve.core.crypto.ECKey.fromPrivate(HexUtil.decode(fromPriKey)).getPublicKeyAsHex());
        System.out.println(String.format("from addr: %s", from));
    }

    protected void setAccount_EFa1() {
        fromEvm = "0xc11D9943805e56b630A401D4bd9A29550353EFa1";
        from = "tNULSeBaMrQaVh1V7LLvbKa5QSN54bS4sdbXaF";
        fromPriKey = "4594348E3482B751AA235B8E580EFEF69DB465B3A291C5662CEDA6459ED12E39";
        updateAddr();
    }

    protected void setErc20USD18() {
        erc20Address = USD18;
        erc20Decimals = 18;
    }

    /**
     * 只充值主资产
     */
    @Test
    public void depositMainAsset() throws Exception {
        // initialization account
        setAccount_EFa1();
        // MainAssetquantity
        String sendAmount = "0.11";
        // Nerve Receiving address
        String to = "TNVTdTSPJJMGh7ijUGDqVZyucbeN1z4jqb1ad";
        BigInteger convertAmount = new BigDecimal(sendAmount).movePointRight(nulsWalletApi.decimals()).toBigInteger();
        String hash = this.sendTx(from, fromPriKey, multySignContractAddress,
                NulsContractUtil.METHOD_CROSS_OUT_II, null,
                new Object[]{to, 0, NulsContractUtil.ZERO_ADDRESS, null},
                NulsContractUtil.CROSSOUTII_TYPE, convertAmount);
        System.out.println(String.format("MainAsset Recharge [%s], hash: %s", sendAmount, hash));
    }

    /**
     * 只充值ERC20资产
     */
    @Test
    public void depositERC20() throws Exception {
        // initialization account
        setAccount_EFa1();
        // ERC20 Transfer quantity
        String sendAmount = "12.123456789123456789";
        setErc20USD18();

        // Nerve Receiving address
        String to = "TNVTdTSPJJMGh7ijUGDqVZyucbeN1z4jqb1ad";

        BigInteger convertAmount = new BigDecimal(sendAmount).multiply(BigDecimal.TEN.pow(erc20Decimals)).toBigInteger();
        // 查询授权额度
        RpcResult rpcResult = nulsWalletApi.callViewFunction(erc20Address, "allowance", new Object[]{from, multySignContractAddress});
        Map map = (Map) rpcResult.getResult();
        Object object = map.get("result");
        BigInteger allowanceAmount = new BigInteger(object.toString());
        // 检查授权
        if (allowanceAmount.compareTo(convertAmount) < 0) {
            // erc20authorization
            String approveAmount = "99999999";
            String authHash = this.sendTx(from, fromPriKey, erc20Address,
                    NulsContractUtil.METHOD_APPROVE, null,
                    new Object[]{multySignContractAddress, new BigInteger(approveAmount).multiply(BigInteger.TEN.pow(erc20Decimals)).toString()},
                    NulsContractUtil.APPROVE_TYPE,
                    BigInteger.ZERO);
            System.out.println(String.format("erc20 Authorization recharge [%s], authorization hash: %s", approveAmount, authHash));
            ContractResultSimpleDto dto = null;
            while (dto == null) {
                System.out.println("wait for 3 Second query [ERC20 authorization] Transaction packaging results");
                TimeUnit.SECONDS.sleep(3);
                try {
                    dto = nulsWalletApi.getContractTxResult(authHash);
                } catch (Exception e) {
                    // skip
                    e.printStackTrace();
                }
            }
            rpcResult = nulsWalletApi.callViewFunction(erc20Address, "allowance", new Object[]{from, multySignContractAddress});
            map = (Map) rpcResult.getResult();
            object = map.get("result");
            BigInteger tempAllowanceAmount = new BigInteger(object.toString());
            while (tempAllowanceAmount.compareTo(convertAmount) < 0) {
                System.out.println("wait for 3 Second query [ERC20 authorization] Transaction limit");
                TimeUnit.SECONDS.sleep(3);
                rpcResult = nulsWalletApi.callViewFunction(erc20Address, "allowance", new Object[]{from, multySignContractAddress});
                map = (Map) rpcResult.getResult();
                object = map.get("result");
                tempAllowanceAmount = new BigInteger(object.toString());
            }
        }
        System.out.println("[ERC20 authorization] The limit has met the conditions");
        // crossOut erc20 Transfer
        String hash = this.sendTx(from, fromPriKey, multySignContractAddress,
                NulsContractUtil.METHOD_CROSS_OUT_II, null,
                new Object[]{to, convertAmount, erc20Address, null},
                NulsContractUtil.CROSSOUTII_TYPE, BigInteger.ZERO);
        System.out.println(String.format("erc20 Recharge[%s], Recharge hash: %s", sendAmount, hash));
    }

    /**
     * 同时充值主资产和ERC20
     */
    @Test
    public void depositByCrossOutIITest() throws Exception {
        // initialization account
        setAccount_EFa1();
        // Main asset expenses
        String mainValue = "0.1";
        // ERC20 Transfer quantity
        String sendAmount = "2.15";
        // initialization ERC20 Address information
        setErc20USD18();
        // Nerve Receiving address
        String to = "TNVTdTSPJJMGh7ijUGDqVZyucbeN1z4jqb1ad";

        BigInteger convertAmount = new BigDecimal(sendAmount).movePointRight(erc20Decimals).toBigInteger();
        BigInteger mainValueBi = new BigDecimal(mainValue).movePointRight(nulsWalletApi.decimals()).toBigInteger();
        String hash = this.sendTx(from, fromPriKey, multySignContractAddress,
                NulsContractUtil.METHOD_CROSS_OUT_II, null,
                new Object[]{to, convertAmount, erc20Address, null},
                NulsContractUtil.CROSSOUTII_TYPE, mainValueBi);
        System.out.println(String.format("CrossOutII [%s], transaction hash: %s", sendAmount, hash));
    }

    /**
     * 计算nerve跨链转出到nuls系网络的手续费
     */
    @Test
    public void withdrawFeeTest() {
        // nerve api jsonrpc 请求接口 gasLimitOfHeterogeneousChains
        // 取出相应chainId的 gasLimitOfWithdraw 和 extend
        // 两个值相乘
        BigInteger gasLimitOfWithdraw = null;
        BigInteger extend = null;
        BigInteger result = gasLimitOfWithdraw.multiply(extend);
        BigInteger nulsL1Fee = HtgCommonTools.getNulsL1Fee(nulsWalletApi.chainId(), nulsWalletApi.decimals());
        BigInteger totalFee = result.add(nulsL1Fee);
        System.out.println(String.format("totalFee: %s", totalFee));
    }

    private String sendTx(String fromAddress, String priKey, String contract, String method, String methodDesc,
                            Object[] args, String[] argsType, BigInteger value) throws Exception {
        // estimateGasLimit
        BigInteger estimateGas = BigInteger.ZERO;
        RpcResult rpcResult = nulsWalletApi.estimateGas(fromAddress, value, contract, method, methodDesc, args);
        if (rpcResult.getError() != null) {
            String error = rpcResult.getError().toString();
            throw new RuntimeException(String.format("Transaction estimation GasLimit Failure, reason for failure: %s", error));
        } else {
            estimateGas = new BigInteger(((Map) rpcResult.getResult()).get("gasLimit").toString());
        }

        System.out.println(String.format("Estimated GasLimit: %s", estimateGas));
        if (estimateGas.compareTo(BigInteger.ZERO) == 0) {
            throw new RuntimeException("Transaction verification failed, reason: estimateGasLimitfail");
        }
        BigInteger gasLimit = estimateGas;
        HtgSendTransactionPo htSendTransactionPo = nulsWalletApi.callContract(fromAddress, priKey, contract, gasLimit,
                method, methodDesc, args, argsType,
                value, null, null);
        String nulsTxHash = htSendTransactionPo.getTxHash();
        return nulsTxHash;
    }
}
