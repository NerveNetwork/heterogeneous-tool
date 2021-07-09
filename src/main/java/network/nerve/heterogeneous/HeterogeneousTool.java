package network.nerve.heterogeneous;

import network.nerve.heterogeneous.core.HtgWalletApi;
import network.nerve.heterogeneous.core.MetaMaskWalletApi;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * 异构链交易聚合工具类
 *
 * @author: Charlie
 * @date: 2021/5/18
 */
public class HeterogeneousTool {

    private String symbol;
    private String chainName;
    private String rpcAddress;
    private HtgWalletApi htgWalletApi;

    public HeterogeneousTool(String symbol, String chainName, String rpcAddress) {
        this.symbol = symbol;
        this.chainName = chainName;
        this.rpcAddress = rpcAddress;
        this.htgWalletApi = HtgWalletApi.getInstance(this.symbol, this.chainName, this.rpcAddress);
    }

    public MetaMaskWalletApi metaMask() {
        return this.htgWalletApi;
    }

    /**
     * 转账主资产[ETH/BNB/HT/OKT](只组装交易 不签名 不广播)
     *
     * @param fromAddress 转出地址
     * @param toAddress   接收地址
     * @param amount      转出数量
     * @param gasLimit
     * @param gasPrice
     * @return 交易hex
     * @throws Exception
     */
    public String assembleTransferMainAsset(String fromAddress, String toAddress, BigDecimal amount, BigInteger gasLimit, BigInteger gasPrice) throws Exception {
        RawTransaction rawTransaction = htgWalletApi.createSendMainAssetWithoutSign(fromAddress, toAddress, amount, gasLimit, gasPrice);
        return Numeric.toHexString(TransactionEncoder.encode(rawTransaction));

    }

    /**
     * 转账合约资产[ERC20/BEP20 ... ](只组装交易 不签名 不广播)
     *
     * @param fromAddress
     * @param toAddress
     * @param amount
     * @param contractAddress
     * @param gasLimit
     * @param gasPrice
     * @return 交易hex
     * @throws Exception
     */
    public String assembleTransferERC20Token(String fromAddress, String toAddress, BigInteger amount, String contractAddress, BigInteger gasLimit, BigInteger gasPrice) throws Exception {
        RawTransaction rawTransaction = htgWalletApi.createTransferERC20TokenWithoutSign(fromAddress, toAddress, amount, contractAddress, gasLimit, gasPrice);
        return Numeric.toHexString(TransactionEncoder.encode(rawTransaction));
    }

    /**
     * 充值主资产 [ETH/BNB/HT/OKT] (只组装交易 不签名 不广播)
     *
     * @param fromAddress              转出地址
     * @param value                    转出数量(根据小数位数换算后的整数)
     * @param toAddress                接收token的NERVE地址
     * @param multySignContractAddress 多签合约地址
     * @return 交易hex
     * @throws Exception
     */
    public String assembleRechargeMainAsset(String fromAddress, BigInteger value, String toAddress, String multySignContractAddress) throws Exception {
        RawTransaction rawTransaction = htgWalletApi.createRechargeMainAssetWithoutSign(fromAddress, value, toAddress, multySignContractAddress);
        return Numeric.toHexString(TransactionEncoder.encode(rawTransaction));
    }

    public String addFeeRechargeMainAsset(String fromAddress, BigInteger value, String toAddress, String multySignContractAddress, BigInteger gasPrice, BigInteger nonce) throws Exception {
        RawTransaction rawTransaction = htgWalletApi.createRechargeMainAssetWithoutSign(fromAddress, value, toAddress, multySignContractAddress, gasPrice, nonce);
        return Numeric.toHexString(TransactionEncoder.encode(rawTransaction));
    }

    /**
     * 充值合约资产[ERC20/BEP20 ... ](只组装交易 不签名 不广播)
     *
     * @param fromAddress
     * @param value
     * @param toAddress
     * @param multySignContractAddress
     * @param bep20ContractAddress
     * @return
     * @throws Exception
     */
    public String assembleRechargeERC20Token(String fromAddress, BigInteger value, String toAddress, String multySignContractAddress, String bep20ContractAddress) throws Exception {
        RawTransaction rawTransaction = htgWalletApi.createRechargeErc20WithoutSign(fromAddress, value, toAddress, multySignContractAddress, bep20ContractAddress);
        return Numeric.toHexString(TransactionEncoder.encode(rawTransaction));
    }

    /**
     * 充值合约资产 加速 [ERC20/BEP20 ... ](只组装交易 不签名 不广播)
     */
    public String addFeeRechargeErc20(String fromAddress, BigInteger value, String toAddress, String multySignContractAddress, String bep20ContractAddress, BigInteger gasPrice, BigInteger nonce) throws Exception {
        RawTransaction rawTransaction = htgWalletApi.createRechargeErc20WithoutSign(fromAddress, value, toAddress, multySignContractAddress, bep20ContractAddress, gasPrice, nonce);
        return Numeric.toHexString(TransactionEncoder.encode(rawTransaction));
    }


    /**
     * 获取主资产余额
     *
     * @param address
     * @return
     * @throws Exception
     */
    public BigDecimal getBalance(String address) throws Exception {
        return htgWalletApi.getBalance(address);
    }

    /**
     * 获取合约资产余额
     *
     * @param address
     * @param contractAddress
     * @return
     * @throws Exception
     */
    public BigInteger getErc20Balance(String address, String contractAddress) throws Exception {
        return htgWalletApi.getERC20Balance(address, contractAddress);
    }

    /**
     * 授权使用ERC20资产 (只组装交易 不签名 不广播)
     *
     * @param fromAddress              转出地址
     * @param multySignContractAddress 多签合约地址
     * @param erc20ContractAddress     ERC20 token合约地址
     * @return
     * @throws Exception
     */
    public String authorization(String fromAddress, String multySignContractAddress, String erc20ContractAddress) throws Exception {
        RawTransaction rawTransaction = htgWalletApi.authorizationWithoutSign(fromAddress, multySignContractAddress, erc20ContractAddress);
        return Numeric.toHexString(TransactionEncoder.encode(rawTransaction));
    }

    /**
     * 查询是否已经授权使用ERC20资产
     *
     * @param fromAddress              转出地址
     * @param multySignContractAddress 多签合约地址
     * @param erc20ContractAddress     ERC20 token合约地址
     * @return
     * @throws Exception
     */
    public boolean isAuthorized(String fromAddress, String multySignContractAddress, String erc20ContractAddress) throws Exception {
        return htgWalletApi.isAuthorized(fromAddress, multySignContractAddress, erc20ContractAddress);
    }

    /**
     * 查询交易打包结果
     *
     * @param txHash
     * @return
     * @throws Exception
     */
    public TransactionReceipt getTxReceipt(String txHash) throws Exception {
        return htgWalletApi.getTxReceipt(txHash);
    }

    public int getContractTokenDecimals(String tokenContract) throws Exception {
        return htgWalletApi.getContractTokenDecimals(tokenContract);
    }

    public BigInteger totalSupply(String contractAddress) throws Exception {
        return htgWalletApi.totalSupply(contractAddress);
    }

    public BigInteger getCurrentGasPrice() throws IOException {
        return htgWalletApi.getCurrentGasPrice();
    }

    /**
     * `
     * 调用合约的view/constant函数
     *
     * @param contractAddress
     * @param function
     * @return
     * @throws Exception
     */
    public List<Type> callViewFunction(String contractAddress, Function function) throws Exception {
        return htgWalletApi.callViewFunction(contractAddress, function);
    }

    /**
     * 广播交易
     *
     * @param txHex 交易HEX
     * @return
     * @throws Exception
     */
    public EthSendTransaction sendTxAsync(String txHex) throws Exception {
        return htgWalletApi.sendAsync(txHex);
    }

    public String ethSign(String priKey, String dataHex) {
        return htgWalletApi.ethSign(priKey, dataHex);
    }

    public String personalSign(String priKey, String data) {
        return htgWalletApi.personalSign(priKey, data);
    }

    public String signTypedDataV4(String priKey, String json) throws IOException {
        return htgWalletApi.signTypedDataV4(priKey, json);
    }
}
