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

import network.nerve.heterogeneous.context.BnbContext;
import network.nerve.heterogeneous.core.HtgWalletApi;
import network.nerve.heterogeneous.core.MetaMaskWalletApi;
import network.nerve.heterogeneous.model.EthSendTransactionPo;
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
 * BSC Tool
 *
 * @author: Loki
 * @date: 2020/11/18
 */
public class BSCTool {

    private static HtgWalletApi bnbWalletApi = HtgWalletApi.getInstance(BnbContext.symbol, BnbContext.chainName, BnbContext.rpcAddress);

    public static MetaMaskWalletApi metaMask() {
        return bnbWalletApi;
    }

    /**
     * 自定义BSC RPC地址
     *
     * @param rpcAddress
     */
    public static void init(String rpcAddress) {
        BnbContext.rpcAddress = rpcAddress;
        bnbWalletApi.restartApi(rpcAddress);
    }

    /**
     * 在BSC上转账BNB资产
     *
     * @param fromAddress 转出地址
     * @param privateKey  转出地址私钥
     * @param toAddress   接收地址
     * @param amount      转出数量
     * @param gasLimit
     * @param gasPrice
     * @return 交易hash
     * @throws Exception
     */
    public static String transferBnb(String fromAddress, String privateKey, String toAddress, BigDecimal amount, BigInteger gasLimit, BigInteger gasPrice) throws Exception {
        return bnbWalletApi.sendMainAsset(fromAddress, privateKey, toAddress, amount, gasLimit, gasPrice);
    }

    /**
     * 在BSC上转账BNB资产(只组装交易 不广播)
     *
     * @param fromAddress 转出地址
     * @param privateKey  转出地址私钥
     * @param toAddress   接收地址
     * @param amount      转出数量
     * @param gasLimit
     * @param gasPrice
     * @return 交易hex
     * @throws Exception
     */
    public static EthSendTransactionPo createTransferBnb(String fromAddress, String privateKey, String toAddress, BigDecimal amount, BigInteger gasLimit, BigInteger gasPrice) throws Exception {
        return bnbWalletApi.createSendMainAsset(fromAddress, privateKey, toAddress, amount, gasLimit, gasPrice);
    }


    /**
     * 在BSC上转账BEP20资产
     *
     * @param fromAddress     转出地址
     * @param privateKey      转出地址私钥
     * @param toAddress       接收地址
     * @param amount          转出数量(根据小数位数换算后的整数)
     * @param contractAddress BEP20资产合约地址
     * @return
     * @throws Exception
     */
    public static EthSendTransaction transferBep20(String fromAddress, String privateKey, String toAddress, BigInteger amount, String contractAddress, BigInteger gasLimit, BigInteger gasPrice) throws Exception {
        return bnbWalletApi.transferERC20Token(fromAddress, toAddress, amount, privateKey, contractAddress, gasLimit, gasPrice);
    }

    /**
     * 在BSC上转账BEP20资产(只组装交易 不广播)
     *
     * @param fromAddress     转出地址
     * @param privateKey      转出地址私钥
     * @param toAddress       接收地址
     * @param amount          转出数量(根据小数位数换算后的整数)
     * @param contractAddress BEP20资产合约地址
     * @return
     * @throws Exception
     */
    public static EthSendTransactionPo createTransferBep20(String fromAddress, String privateKey, String toAddress, BigInteger amount, String contractAddress, BigInteger gasLimit, BigInteger gasPrice) throws Exception {
        return bnbWalletApi.createTransferERC20Token(fromAddress, toAddress, amount, privateKey, contractAddress, gasLimit, gasPrice);
    }
    /**
     * 获取BNB资产余额
     *
     * @param address
     * @return
     * @throws Exception
     */
    public static BigDecimal getBnbBalance(String address) throws Exception {
        return bnbWalletApi.getBalance(address);
    }

    /**
     * 获取BEP20资产余额
     *
     * @param address
     * @param contractAddress
     * @return
     * @throws Exception
     */
    public static BigInteger getBep20Balance(String address, String contractAddress) throws Exception {
        return bnbWalletApi.getERC20Balance(address, contractAddress);
    }

    /**
     * (只组装交易)充值BNB
     * BSC网络向NERVE网络充值BNB
     *
     * @param fromAddress              转出地址
     * @param privateKey               转出地址私钥
     * @param value                    转出数量(根据小数位数换算后的整数)
     * @param toAddress                接收token的NERVE地址
     * @param multySignContractAddress 多签合约地址
     * @return 交易hex
     * @throws Exception
     */
    public static EthSendTransactionPo createRechargeBnb(String fromAddress, String privateKey, BigInteger value, String toAddress, String multySignContractAddress) throws Exception {
        return bnbWalletApi.createRechargeMainAsset(fromAddress, privateKey, value, toAddress, multySignContractAddress);
    }
    /**
     * 充值BNB
     * BSC网络向NERVE网络充值BNB
     *
     * @param fromAddress              转出地址
     * @param privateKey               转出地址私钥
     * @param value                    转出数量(根据小数位数换算后的整数)
     * @param toAddress                接收token的NERVE地址
     * @param multySignContractAddress 多签合约地址
     * @return 交易hash
     * @throws Exception
     */
    public static EthSendTransactionPo rechargeBnb(String fromAddress, String privateKey, BigInteger value, String toAddress, String multySignContractAddress) throws Exception {
        return bnbWalletApi.rechargeMainAsset(fromAddress, privateKey, value, toAddress, multySignContractAddress);
    }

    /**
     * (充值BNB) 追加手续费
     * BSC网络向NERVE网络充值BNB
     *
     * @param fromAddress              转出地址
     * @param privateKey               转出地址私钥
     * @param value                    转出数量(根据小数位数换算后的整数)
     * @param toAddress                接收token的NERVE地址
     * @param multySignContractAddress 多签合约地址
     * @return 交易hash
     * @throws Exception
     */
    public static EthSendTransactionPo addFeeRechargeBnb(String fromAddress, String privateKey, BigInteger value, String toAddress, String multySignContractAddress, BigInteger gasPrice, BigInteger nonce) throws Exception {
        return bnbWalletApi.rechargeMainAsset(fromAddress, privateKey, value, toAddress, multySignContractAddress, gasPrice, nonce);
    }

    /**
     * (只组装交易)充值BEP20资产
     * BSC网络向NERVE网络充值BEP20资产
     *
     * @param fromAddress              转出地址
     * @param privateKey               转出地址私钥
     * @param value                    转出数量(根据小数位数换算后的整数)
     * @param toAddress                接收token的NERVE地址
     * @param multySignContractAddress 多签合约地址
     * @param bep20ContractAddress     BEP20 token合约地址
     * @return hex
     * @throws Exception
     */
    public static EthSendTransactionPo createRechargeBep20(String fromAddress, String privateKey, BigInteger value, String toAddress, String multySignContractAddress, String bep20ContractAddress) throws Exception {
        return bnbWalletApi.createRechargeErc20(fromAddress, privateKey, value, toAddress, multySignContractAddress, bep20ContractAddress);
    }

    /**
     * 充值BEP20资产
     * BSC网络向NERVE网络充值BEP20资产
     *
     * @param fromAddress              转出地址
     * @param privateKey               转出地址私钥
     * @param value                    转出数量(根据小数位数换算后的整数)
     * @param toAddress                接收token的NERVE地址
     * @param multySignContractAddress 多签合约地址
     * @param bep20ContractAddress     BEP20 token合约地址
     * @return 交易hash
     * @throws Exception
     */
    public static EthSendTransactionPo rechargeBep20(String fromAddress, String privateKey, BigInteger value, String toAddress, String multySignContractAddress, String bep20ContractAddress) throws Exception {
        return bnbWalletApi.rechargeErc20(fromAddress, privateKey, value, toAddress, multySignContractAddress, bep20ContractAddress);
    }

    /**
     * （充值BEP20资产）追加手续费
     * BSC网络向NERVE网络充值BEP20资产
     *
     * @param fromAddress              转出地址
     * @param privateKey               转出地址私钥
     * @param value                    转出数量(根据小数位数换算后的整数)
     * @param toAddress                接收token的NERVE地址
     * @param multySignContractAddress 多签合约地址
     * @param bep20ContractAddress     BEP20 token合约地址
     * @return 交易hash
     * @throws Exception
     */
    public static EthSendTransactionPo addFeeRechargeBep20(String fromAddress, String privateKey, BigInteger value, String toAddress, String multySignContractAddress, String bep20ContractAddress, BigInteger gasPrice, BigInteger nonce) throws Exception {
        return bnbWalletApi.rechargeErc20(fromAddress, privateKey, value, toAddress, multySignContractAddress, bep20ContractAddress, gasPrice, nonce);
    }

    /**
     * 授权使用BEP20资产
     *
     * @param fromAddress              转出地址
     * @param privateKey               转出地址私钥
     * @param multySignContractAddress 多签合约地址
     * @param bep20ContractAddress     BEP20 token合约地址
     * @return
     * @throws Exception
     */
    public static String authorization(String fromAddress, String privateKey, String multySignContractAddress, String bep20ContractAddress) throws Exception {
        return bnbWalletApi.authorization(fromAddress, privateKey, multySignContractAddress, bep20ContractAddress);
    }

    /**
     * 查询是否已经授权使用BEP20资产
     *
     * @param fromAddress              转出地址
     * @param multySignContractAddress 多签合约地址
     * @param bep20ContractAddress     BEP20 token合约地址
     * @return
     * @throws Exception
     */
    public static boolean isAuthorized(String fromAddress, String multySignContractAddress, String bep20ContractAddress) throws Exception {
        return bnbWalletApi.isAuthorized(fromAddress, multySignContractAddress, bep20ContractAddress);
    }

    /**
     * 调用合约的view/constant函数
     * @param contractAddress
     * @param function
     * @return
     * @throws Exception
     */
    public static List<Type> callViewFunction(String contractAddress, Function function) throws Exception {
        return bnbWalletApi.callViewFunction(contractAddress, function);
    }

    public static TransactionReceipt getTxReceipt(String txHash) throws Exception {
        return bnbWalletApi.getTxReceipt(txHash);
    }

    public static int getContractTokenDecimals(String tokenContract) throws Exception {
        return bnbWalletApi.getContractTokenDecimals(tokenContract);
    }

    public static BigInteger totalSupply(String contractAddress) throws Exception {
        return bnbWalletApi.totalSupply(contractAddress);
    }

    public static BigInteger getCurrentGasPrice() throws IOException {
        return bnbWalletApi.getCurrentGasPrice();
    }

    public static BigDecimal calNVTOfWithdraw(BigDecimal nvtUSD, BigDecimal gasPrice, BigDecimal ethUSD, boolean isBNBToken) {
        BigDecimal gasLimit;
        if (isBNBToken) {
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

}
