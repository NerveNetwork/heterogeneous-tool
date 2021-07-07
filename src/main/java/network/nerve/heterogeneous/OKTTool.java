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

package network.nerve.heterogeneous;

import network.nerve.heterogeneous.context.OktContext;
import network.nerve.heterogeneous.core.HtgWalletApi;
import network.nerve.heterogeneous.core.MetaMaskWalletApi;
import network.nerve.heterogeneous.model.EthSendTransactionPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;


/**
 * OKT Tool
 *
 * @author: Loki
 * @date: 2021/04/27
 */
public class OKTTool {

    private static Logger Log = LoggerFactory.getLogger(OKTTool.class.getName());

    private static HtgWalletApi ethWalletApi = HtgWalletApi.getInstance(OktContext.symbol, OktContext.chainName);

    public static MetaMaskWalletApi metaMask() {
        return ethWalletApi;
    }

    /**
     * 自定义 OKT RPC地址
     *
     * @param rpcAddress
     */
    public static boolean init(String rpcAddress) {
        OktContext.rpcAddress = rpcAddress;
        return ethWalletApi.restartApi(rpcAddress);
    }

    /**
     * 在OKT上转账OKT资产
     *
     * @param fromAddress 转出地址
     * @param privateKey  转出地址私钥
     * @param toAddress   接收地址
     * @param value       转出数量
     * @param gasLimit
     * @param gasPrice
     * @return 交易hash
     * @throws Exception
     */
    public static String transferOkt(String fromAddress, String privateKey, String toAddress, BigDecimal value, BigInteger gasLimit, BigInteger gasPrice) throws Exception {
        return ethWalletApi.sendMainAsset(fromAddress, privateKey, toAddress, value, gasLimit, gasPrice);
    }

    /**
     * 在OKT上转账OKT资产(只组装交易 不广播)
     *
     * @param fromAddress 转出地址
     * @param privateKey  转出地址私钥
     * @param toAddress   接收地址
     * @param value       转出数量
     * @param gasLimit
     * @param gasPrice
     * @return 交易hex
     * @throws Exception
     */
    public static EthSendTransactionPo createTransferOkt(String fromAddress, String privateKey, String toAddress, BigDecimal value, BigInteger gasLimit, BigInteger gasPrice) throws Exception {
        return ethWalletApi.createSendMainAsset(fromAddress, privateKey, toAddress, value, gasLimit, gasPrice);
    }


    /**
     * 在OKT上转账KIP20资产
     *
     * @param fromAddress     转出地址
     * @param privateKey      转出地址私钥
     * @param toAddress       接收地址
     * @param value           转出数量(根据小数位数换算后的整数)
     * @param contractAddress ERC20资产合约地址
     * @return
     * @throws Exception
     */
    public static EthSendTransaction transferKip20(String fromAddress, String privateKey, String toAddress, BigInteger value, String contractAddress, BigInteger gasLimit, BigInteger gasPrice) throws Exception {
        return ethWalletApi.transferERC20Token(fromAddress, toAddress, value, privateKey, contractAddress, gasLimit, gasPrice);
    }

    /**
     * 在OKT上转账KIP20资产(只组装交易 不广播)
     *
     * @param fromAddress     转出地址
     * @param privateKey      转出地址私钥
     * @param toAddress       接收地址
     * @param value           转出数量(根据小数位数换算后的整数)
     * @param contractAddress ERC20资产合约地址
     * @return
     * @throws Exception
     */
    public static EthSendTransactionPo createTransferKip20(String fromAddress, String privateKey, String toAddress, BigInteger value, String contractAddress, BigInteger gasLimit, BigInteger gasPrice) throws Exception {
        return ethWalletApi.createTransferERC20Token(fromAddress, toAddress, value, privateKey, contractAddress, gasLimit, gasPrice);
    }

    /**
     * 获取OKT资产余额
     *
     * @param address
     * @return
     * @throws Exception
     */
    public static BigDecimal getOktBalance(String address) throws Exception {
        return ethWalletApi.getBalance(address);
    }

    /**
     * 获取KIP20资产余额
     *
     * @param address
     * @param contractAddress
     * @return
     * @throws Exception
     */
    public static BigInteger getKip20Balance(String address, String contractAddress) throws Exception {
        return ethWalletApi.getERC20Balance(address, contractAddress);
    }

    /**
     * 充值OKT
     * 以太坊网络向NERVE网络充值OKT
     *
     * @param fromAddress              转出地址
     * @param privateKey               转出地址私钥
     * @param value                    转出数量(根据小数位数换算后的整数)
     * @param toAddress                接收token的NERVE地址
     * @param multySignContractAddress 多签合约地址
     * @return
     * @throws Exception
     */
    public static EthSendTransactionPo rechargeOkt(String fromAddress, String privateKey, BigInteger value, String toAddress, String multySignContractAddress) throws Exception {
        return ethWalletApi.rechargeMainAsset(fromAddress, privateKey, value, toAddress, multySignContractAddress);
    }
    /**
     * (充值OKT) 追加手续费
     * HECO网络向NERVE网络充值HT
     *
     * @param fromAddress              转出地址
     * @param privateKey               转出地址私钥
     * @param value                    转出数量(根据小数位数换算后的整数)
     * @param toAddress                接收token的NERVE地址
     * @param multySignContractAddress 多签合约地址
     * @return 交易hash
     * @throws Exception
     */
    public static EthSendTransactionPo addFeeRechargeOkt(String fromAddress, String privateKey, BigInteger value, String toAddress, String multySignContractAddress, BigInteger gasPrice, BigInteger nonce) throws Exception {
        return ethWalletApi.rechargeMainAsset(fromAddress, privateKey, value, toAddress, multySignContractAddress, gasPrice, nonce);
    }

    /**
     * (只组装交易)充值OKT
     * 以太坊网络向NERVE网络充值OKT
     *
     * @param fromAddress              转出地址
     * @param privateKey               转出地址私钥
     * @param value                    转出数量(根据小数位数换算后的整数)
     * @param toAddress                接收token的NERVE地址
     * @param multySignContractAddress 多签合约地址
     * @return
     * @throws Exception
     */
    public static EthSendTransactionPo createRechargeOkt(String fromAddress, String privateKey, BigInteger value, String toAddress, String multySignContractAddress) throws Exception {
        return ethWalletApi.createRechargeMainAsset(fromAddress, privateKey, value, toAddress, multySignContractAddress);
    }

    /**
     * 充值KIP20资产
     * 以太坊网络向NERVE网络充值KIP20资产
     *
     * @param fromAddress              转出地址
     * @param privateKey               转出地址私钥
     * @param value                    转出数量(根据小数位数换算后的整数)
     * @param toAddress                接收token的NERVE地址
     * @param multySignContractAddress 多签合约地址
     * @param erc20ContractAddress     KIP20 token合约地址
     * @return
     * @throws Exception
     */
    public static EthSendTransactionPo rechargeKip20(String fromAddress, String privateKey, BigInteger value, String toAddress, String multySignContractAddress, String erc20ContractAddress) throws Exception {
        return ethWalletApi.rechargeErc20(fromAddress, privateKey, value, toAddress, multySignContractAddress, erc20ContractAddress);
    }

    /**
     * 充值KIP20资产
     * 以太坊网络向NERVE网络充值KIP20资产
     *
     * @param fromAddress              转出地址
     * @param privateKey               转出地址私钥
     * @param value                    转出数量(根据小数位数换算后的整数)
     * @param toAddress                接收token的NERVE地址
     * @param multySignContractAddress 多签合约地址
     * @param erc20ContractAddress     ERC20 token合约地址
     * @return
     * @throws Exception
     */
    public static EthSendTransactionPo createRechargeKip20(String fromAddress, String privateKey, BigInteger value, String toAddress, String multySignContractAddress, String erc20ContractAddress) throws Exception {
        return ethWalletApi.createRechargeErc20(fromAddress, privateKey, value, toAddress, multySignContractAddress, erc20ContractAddress);
    }

    /**
     * （充值KIP20资产）追加手续费
     * BSC网络向NERVE网络充值KIP20资产
     *
     * @param fromAddress              转出地址
     * @param privateKey               转出地址私钥
     * @param value                    转出数量(根据小数位数换算后的整数)
     * @param toAddress                接收token的NERVE地址
     * @param multySignContractAddress 多签合约地址
     * @param bep20ContractAddress     KIP20 token合约地址
     * @return 交易hash
     * @throws Exception
     */
    public static EthSendTransactionPo addFeeRechargeKip20(String fromAddress, String privateKey, BigInteger value, String toAddress, String multySignContractAddress, String bep20ContractAddress, BigInteger gasPrice, BigInteger nonce) throws Exception {
        return ethWalletApi.rechargeErc20(fromAddress, privateKey, value, toAddress, multySignContractAddress, bep20ContractAddress, gasPrice, nonce);
    }

    /**
     * 授权使用KIP20资产
     *
     * @param fromAddress              转出地址
     * @param privateKey               转出地址私钥
     * @param multySignContractAddress 多签合约地址
     * @param erc20ContractAddress     KIP20 token合约地址
     * @return
     * @throws Exception
     */
    public static String authorization(String fromAddress, String privateKey, String multySignContractAddress, String erc20ContractAddress) throws Exception {
        return ethWalletApi.authorization(fromAddress, privateKey, multySignContractAddress, erc20ContractAddress);
    }

    /**
     * 查询是否已经授权使用KIP20资产
     *
     * @param fromAddress              转出地址
     * @param multySignContractAddress 多签合约地址
     * @param erc20ContractAddress     KIP20 token合约地址
     * @return
     * @throws Exception
     */
    public static boolean isAuthorized(String fromAddress, String multySignContractAddress, String erc20ContractAddress) throws Exception {
        return ethWalletApi.isAuthorized(fromAddress, multySignContractAddress, erc20ContractAddress);
    }

    /**
     * 调用合约的view/constant函数
     * @param contractAddress
     * @param function
     * @return
     * @throws Exception
     */
    public static List<Type> callViewFunction(String contractAddress, Function function) throws Exception {
        return ethWalletApi.callViewFunction(contractAddress, function);
    }

    public static TransactionReceipt getTxReceipt(String txHash) throws Exception {
        return ethWalletApi.getTxReceipt(txHash);
    }

    public static int getContractTokenDecimals(String tokenContract) throws Exception {
        return ethWalletApi.getContractTokenDecimals(tokenContract);
    }

    public static BigInteger totalSupply(String contractAddress) throws Exception {
        return ethWalletApi.totalSupply(contractAddress);
    }

    public static BigInteger getCurrentGasPrice() throws IOException {
        return ethWalletApi.getCurrentGasPrice();
    }

    public static BigDecimal calNVTOfWithdraw(BigDecimal nvtUSD, BigDecimal gasPrice, BigDecimal ethUSD, boolean isETHToken) {
        BigDecimal gasLimit;
        if (isETHToken) {
            gasLimit = BigDecimal.valueOf(210000L);
        } else {
            gasLimit = BigDecimal.valueOf(190000L);
        }
        BigDecimal nvtAmount = calNVTByGasPrice(nvtUSD, gasPrice, ethUSD, gasLimit);
        nvtAmount = nvtAmount.divide(BigDecimal.TEN.pow(8), 0, RoundingMode.UP).movePointRight(8);
        return nvtAmount;
    }

    public static BigDecimal calNVTByGasPrice(BigDecimal nvtUSD, BigDecimal gasPrice, BigDecimal ethUSD, BigDecimal gasLimit) {
        BigDecimal nvtAmount = ethUSD.multiply(gasPrice).multiply(gasLimit).divide(nvtUSD.multiply(BigDecimal.TEN.pow(10)), 0, RoundingMode.UP);
        return nvtAmount;
    }

    public static String ethSign(String priKey, String dataHex) {
        return metaMask().ethSign(priKey, dataHex);
    }

    public static String personalSign(String priKey, String dataHex) {
        return metaMask().personalSign(priKey, dataHex);
    }

    public static String signTypedDataV4(String priKey, String json) throws IOException {
        return metaMask().signTypedDataV4(priKey, json);
    }
}
