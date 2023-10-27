package network.nerve.heterogeneous.core;

import network.nerve.heterogeneous.HtgTool;
import network.nerve.heterogeneous.constant.Constant;
import network.nerve.heterogeneous.crypto.StructuredDataEncoder;
import network.nerve.heterogeneous.model.Transaction;
import network.nerve.heterogeneous.model.*;
import network.nerve.heterogeneous.utils.*;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.Sign;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static network.nerve.heterogeneous.constant.Constant.*;


/**
 * HTG API
 */
public class HtgWalletApi implements WalletApi, MetaMaskWalletApi {

    private static Logger Log = LoggerFactory.getLogger(HtgWalletApi.class.getName());

    private String rpcAddress;
    private String symbol;
    private String chainName;
    private int chainId = -1;

    private HtgWalletApi(String symbol, String chainName, String rpcAddress) {
        this.symbol = symbol;
        this.chainName = chainName;
        this.rpcAddress = rpcAddress;
        init();
    }

    private HtgWalletApi(String symbol, String chainName) {
        this.symbol = symbol;
        this.chainName = chainName;
    }

    public static HtgWalletApi getInstance(String symbol, String chainName, String rpcAddress) {
        return new HtgWalletApi(symbol, chainName, rpcAddress);
    }

    public static HtgWalletApi getInstance(String symbol, String chainName, String rpcAddress, int chainId) {
        HtgWalletApi walletApi = new HtgWalletApi(symbol, chainName, rpcAddress);
        walletApi.chainId = chainId;
        return walletApi;
    }

    public static HtgWalletApi getInstance(String symbol, String chainName) {
        return new HtgWalletApi(symbol, chainName);
    }

    protected Web3j web3j;

    private void init() {
        initialize();
        if (web3j == null) {
            web3j = newInstanceWeb3j(rpcAddress);
        }
    }

    public int chainId() {
        if (chainId <= 0) {
            try {
                BigInteger _chainId = web3j.ethChainId().send().getChainId();
                if (_chainId == null) {
                    throw new RuntimeException("empty chain id");
                }
                chainId = _chainId.intValue();
                return chainId;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return chainId;
    }

    public boolean restartApi(String rpcAddress, int chainId) {
        if (StringUtils.isBlank(rpcAddress)) {
            throw new RuntimeException("empty rpcAddress");
        }
        try {
            this.rpcAddress = rpcAddress;
            this.chainId = chainId;
            shutdownWeb3j();
            init();
            return true;
        } catch (Exception e) {
            Log.error("初始化异常", e);
            return false;
        }
    }

    private void shutdownWeb3j() {

        if (web3j != null) {
            web3j.shutdown();
            web3j = null;
        }
    }

    /**
     * ERC-20Token交易
     */
    public EthSendTransaction transferERC20Token(String from,
                                                 String to,
                                                 BigInteger value,
                                                 String privateKey,
                                                 String contractAddress,
                                                 BigInteger gasLimit,
                                                 BigInteger gasPrice) throws Exception {

        String hexValue = createTransferERC20Token(from, to, value, privateKey, contractAddress, gasLimit, gasPrice).getTxHex();
        //发送交易
        EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
        return ethSendTransaction;
    }

    public RawTransaction createTransferERC20TokenWithoutSign(String from,
                                                              String to,
                                                              BigInteger value,
                                                              String contractAddress,
                                                              BigInteger gasLimit,
                                                              BigInteger gasPrice) throws Exception {
        //获取nonce，交易笔数
        BigInteger nonce = getNonce(from);

        //创建RawTransaction交易对象
        Function function = new Function(
                "transfer",
                Arrays.asList(new Address(to), new Uint256(value)),
                Arrays.asList(new TypeReference<Type>() {
                }));

        String encodedFunction = FunctionEncoder.encode(function);

        RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce,
                gasPrice,
                gasLimit,
                contractAddress, encodedFunction
        );
        return rawTransaction;
    }

    /**
     * 转账ERC20 只组装交易 不发送
     *
     * @return
     * @throws Exception
     */
    @Override
    public EthSendTransactionPo createTransferERC20Token(String from,
                                                         String to,
                                                         BigInteger value,
                                                         String privateKey,
                                                         String contractAddress,
                                                         BigInteger gasLimit,
                                                         BigInteger gasPrice) throws Exception {
        RawTransaction rawTransaction = createTransferERC20TokenWithoutSign(from, to, value, contractAddress, gasLimit, gasPrice);
        //加载转账所需的凭证，用私钥
        Credentials credentials = Credentials.create(privateKey);
        //签名Transaction，这里要对交易做签名
        byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, chainId(), credentials);
        String hexValue = Numeric.toHexString(signMessage);
        return new EthSendTransactionPo(from, rawTransaction, hexValue);
    }


    /**
     * 发送ETH
     */
    public String sendMainAsset(String fromAddress, String privateKey, String toAddress, BigDecimal value, BigInteger gasLimit, BigInteger gasPrice) throws Exception {
        String hexValue = createSendMainAsset(fromAddress, privateKey, toAddress, value, gasLimit, gasPrice).getTxHex();
        //发送广播
        EthSendTransaction send = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
        return send.getTransactionHash();
    }


    public RawTransaction createSendMainAssetWithoutSign(String fromAddress, String toAddress, BigDecimal value, BigInteger gasLimit, BigInteger gasPrice) throws Exception {
        BigDecimal ethBalance = getBalance(fromAddress);
        if (ethBalance == null) {
            throw new RuntimeException(String.format("获取当前地址%s余额失败", symbol));
        }
        BigInteger bigIntegerValue = convertMainAssetToWei(value);
        if (ethBalance.toBigInteger().compareTo(bigIntegerValue.add(gasLimit.multiply(gasPrice))) < 0) {
            //余额小于转账金额与手续费之和
            throw new RuntimeException("账户金额小于转账金额与手续费之和!");
        }
        BigInteger nonce = getNonce(fromAddress);
        if (nonce == null) {
            throw new RuntimeException("获取当前地址nonce失败");
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Log.error(e.getMessage(), e);
        }
        RawTransaction etherTransaction = RawTransaction.createEtherTransaction(nonce, gasPrice, gasLimit, toAddress, bigIntegerValue);
        return etherTransaction;
    }

    /**
     * 转账ETH 只组装交易 不发送
     *
     * @return
     * @throws Exception
     */
    @Override
    public EthSendTransactionPo createSendMainAsset(String fromAddress, String privateKey, String toAddress, BigDecimal value, BigInteger gasLimit, BigInteger gasPrice) throws Exception {
        RawTransaction etherTransaction = createSendMainAssetWithoutSign(fromAddress, toAddress, value, gasLimit, gasPrice);
        //交易签名
        Credentials credentials = Credentials.create(privateKey);
        byte[] signedMessage = TransactionEncoder.signMessage(etherTransaction, chainId(), credentials);
        String hexValue = Numeric.toHexString(signedMessage);
        return new EthSendTransactionPo(fromAddress, etherTransaction, hexValue);
    }


    public void initialize() {
    }

    public long getBlockHeight() throws Exception {
        BigInteger blockHeight = this.timeOutWrapperFunction("getBlockHeight", null, args -> {
            return web3j.ethBlockNumber().send().getBlockNumber();
        });
        if (blockHeight != null) {
            return blockHeight.longValue();
        }
        return 0L;
    }

    private <T, R> R timeOutWrapperFunction(String functionName, T arg, ExceptionFunction<T, R> fucntion) throws Exception {
        return this.timeOutWrapperFunctionReal(functionName, fucntion, 0, arg);
    }

    private <T, R> R timeOutWrapperFunctionReal(String functionName, ExceptionFunction<T, R> fucntion, int times, T arg) throws Exception {
        try {
            return fucntion.apply(arg);
        } catch (Exception e) {
            if (e instanceof BusinessRuntimeException || times > 1) {
                throw e;
            }
            restartApi(rpcAddress, chainId);
            return timeOutWrapperFunctionReal(functionName, fucntion, times + 1, arg);
        }
    }

    protected void checkIfResetWeb3j(int times) {
        int mod = times % 3;
        if (mod == 2 && web3j != null && rpcAddress != null) {
            restartApi(rpcAddress, chainId);
        }
    }

    private Web3j newInstanceWeb3j(String rpcAddress) {
        return Web3j.build(new HttpService(rpcAddress));
    }

    public Block getBlock(String hash) throws Exception {
        EthBlock.Block block = this.timeOutWrapperFunction("getBlock", hash, args -> {
            return web3j.ethGetBlockByHash(args, true).send().getBlock();
        });
        if (block == null) {
            return null;
        }
        return createBlock(block);
    }

    public Block getBlock(long height) throws Exception {
        EthBlock.Block block = getBlockByHeight(height);
        return createBlock(block);
    }

    private Block createBlock(EthBlock.Block block) {
        Block simpleBlock = new Block();
        simpleBlock.setHeight(block.getNumber().longValue());
        simpleBlock.setHash(block.getHash());
        List<EthBlock.TransactionResult> transactions = block.getTransactions();
        if (transactions != null && transactions.size() > 0) {
            List<Transaction> list = new ArrayList<>();
            for (int i = 0; i < transactions.size(); i++) {
                org.web3j.protocol.core.methods.response.Transaction transaction = getTransaction(block.getNumber().longValue(), i);
                Transaction transferTransaction = new Transaction();
                transferTransaction.setFromAddress(transaction.getFrom());
                transferTransaction.setToAddress(transaction.getTo());
                BigDecimal value = convertWeiToHt(transaction.getValue());
                transferTransaction.setAmount(value);
                transferTransaction.setTxHash(transaction.getHash());
                list.add(transferTransaction);
            }
            simpleBlock.setTransactions(list);
        }
        return simpleBlock;
    }

    /**
     * Method:获取链上交易
     * Description:
     * Author: xinjl
     * Date: 2018/4/16 15:23
     */
    public org.web3j.protocol.core.methods.response.Transaction getTransaction(Long height, Integer index) {
        Request<?, EthTransaction> ethTransactionRequest = web3j.ethGetTransactionByBlockNumberAndIndex(new DefaultBlockParameterNumber(height), new BigInteger(index.toString()));
        org.web3j.protocol.core.methods.response.Transaction transaction = null;
        try {
            EthTransaction send = ethTransactionRequest.send();
            if (send.getTransaction().isPresent()) {
                transaction = send.getTransaction().get();
            } else {
                Log.error("交易详情获取失败:" + transaction + ",height:" + height + ",index:" + index);
            }
        } catch (IOException e) {
            Log.error(e.getMessage());
        }
        return transaction;
    }

    public static BigDecimal convertWeiToHt(BigInteger balance) {
        BigDecimal cardinalNumber = new BigDecimal("1000000000000000000");
        BigDecimal decimalBalance = new BigDecimal(balance);
        BigDecimal value = decimalBalance.divide(cardinalNumber, 18, RoundingMode.DOWN);
        return value;
    }

    /**
     * Method:getBlockByHeight
     * Description: 根据高度获取区块
     * Author: xinjl
     * Date: 2018/4/16 15:23
     */
    public EthBlock.Block getBlockByHeight(Long height) throws Exception {
        EthBlock.Block block = this.timeOutWrapperFunction("getBlockByHeight", height, args -> {
            return web3j.ethGetBlockByNumber(new DefaultBlockParameterNumber(args), true).send().getBlock();
        });
        return block;
    }

    /**
     * HT余额
     *
     * @param address
     * @return
     * @throws Exception
     */
    public BigDecimal getBalance(String address) throws Exception {
        BigDecimal balance = this.timeOutWrapperFunction("getBalance", address, args -> {
            EthGetBalance send = web3j.ethGetBalance(args, DefaultBlockParameterName.LATEST).send();
            if (send != null) {
                return new BigDecimal(send.getBalance());
            } else {
                return BigDecimal.ZERO;
            }
        });
        return balance;
    }


    /**
     * 获取ERC20 token指定地址余额
     *
     * @param address         查询地址
     * @param contractAddress 合约地址
     * @return
     * @throws InterruptedException
     */
    public BigInteger getERC20Balance(String address, String contractAddress) throws Exception {
        return this.getERC20BalanceReal(address, contractAddress, DefaultBlockParameterName.PENDING, 0);
    }

    public BigInteger getERC20Balance(String address, String contractAddress, DefaultBlockParameterName status) throws Exception {
        return this.getERC20BalanceReal(address, contractAddress, status, 0);
    }

    private BigInteger getERC20BalanceReal(String address, String contractAddress, DefaultBlockParameterName status, int times) throws Exception {
        try {
            this.checkIfResetWeb3j(times);
            Function function = new Function("balanceOf",
                    Arrays.asList(new Address(address)),
                    Arrays.asList(new TypeReference<Address>() {
                    }));

            String encode = FunctionEncoder.encode(function);
            org.web3j.protocol.core.methods.request.Transaction ethCallTransaction = org.web3j.protocol.core.methods.request.Transaction.createEthCallTransaction(address, contractAddress, encode);
            EthCall ethCall = web3j.ethCall(ethCallTransaction, status).sendAsync().get();
            String value = ethCall.getResult();
            BigInteger balance = new BigInteger(value.substring(2), 16);
            return balance;
        } catch (Exception e) {
            String message = e.getMessage();
            boolean isTimeOut = Tools.isTimeOutError(message);
            if (isTimeOut) {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                return getERC20BalanceReal(address, contractAddress, status, times + 1);
            } else {
                throw e;
            }
        }

    }

    public EthSendTransaction sendTransaction(String fromAddress, String secretKey, Map<String, BigDecimal> transferRequests) {
        return null;
    }

    public String sendTransaction(String toAddress, String fromAddress, String secretKey, BigDecimal amount) {
        String result = null;
        //发送eth
        if (toAddress.length() != 42) {
            return null;
        }
        if (secretKey == null) {
            Log.error("账户私钥不存在!");
        }
        try {
            result = sendMainAsset(fromAddress, secretKey, toAddress, amount, GAS_LIMIT_OF_MAIN, this.getCurrentGasPrice());
        } catch (Exception e) {
            Log.error("send fail", e);
        }
        return result;
    }

    public EthSendTransaction sendTransaction(String toAddress, String fromAddress, String secretKey, BigDecimal amount, String contractAddress) throws Exception {
        //发送token
        if (toAddress.length() != 42) {
            return null;
        }
        if (secretKey == null) {
            Log.error("账户私钥不存在!");
        }
        EthSendTransaction result = transferERC20Token(
                fromAddress,
                toAddress,
                amount.toBigInteger(),
                secretKey,
                contractAddress,
                this.getCurrentGasPrice(),
                GAS_LIMIT_OF_ERC20);
        return result;
    }


    /**
     * 获取nonce，Pending模式 适用于连续转账
     *
     * @param from
     * @return
     * @throws InterruptedException
     */
    public BigInteger getNonce(String from) throws Exception {
        BigInteger nonce = this.timeOutWrapperFunction("getNonce", from, args -> {
            EthGetTransactionCount transactionCount = web3j.ethGetTransactionCount(args, DefaultBlockParameterName.LATEST).sendAsync().get();
            return transactionCount.getTransactionCount();
        });
        return nonce;
    }

    /**
     * Method:send
     * Description: 发送交易
     * Author: xinjl
     * Date: 2018/4/16 15:22
     */
    public EthSendTransaction send(String hexValue) {
        EthSendTransaction ethSendTransaction = null;
        try {
            ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();
        } catch (IOException e) {
            Log.error(e.getMessage(), e);
        }
        return ethSendTransaction;
    }

    public EthSendTransaction sendAsync(String hexValue) throws Exception {
        return web3j.ethSendRawTransaction(hexValue).sendAsync().get();
    }

    public BigInteger convertMainAssetToWei(BigDecimal value) {
        BigDecimal cardinalNumber = new BigDecimal("1000000000000000000");
        value = value.multiply(cardinalNumber);
        return value.toBigInteger();
    }

    public String convertToNewAddress(String address) {
        return address;
    }

    /**
     * 充值主资产
     */
    public EthSendTransactionPo rechargeMainAsset(String fromAddress, String prikey, BigInteger value, String toAddress, String multySignContractAddress) throws Exception {
        return rechargeMainAsset(fromAddress, prikey, value, toAddress, multySignContractAddress, null, null);
    }

    /**
     * 充值主资产 加速
     */
    public EthSendTransactionPo rechargeMainAsset(String fromAddress, String prikey, BigInteger value, String toAddress, String multySignContractAddress, BigInteger gasPrice, BigInteger nonce) throws Exception {
        Function txFunction = getCrossOutFunction(toAddress, value, ZERO_ADDRESS);
        return sendTx(fromAddress, prikey, txFunction, value, multySignContractAddress, gasPrice, nonce);
    }

    /**
     * 充值主资产（不发送）
     */
    @Override
    public EthSendTransactionPo createRechargeMainAssetWithGas(String fromAddress, String prikey, BigInteger value, String toAddress, String multySignContractAddress, BigInteger gasLimit, BigInteger gasPrice) throws Exception {
        Function txFunction = getCrossOutFunction(toAddress, value, ZERO_ADDRESS);
        return createSendTxWithGas(fromAddress, prikey, txFunction, value, multySignContractAddress, gasLimit, gasPrice);
    }

    public EthSendTransactionPo createRechargeMainAsset(String fromAddress, String prikey, BigInteger value, String toAddress, String multySignContractAddress) throws Exception {
        Function txFunction = getCrossOutFunction(toAddress, value, ZERO_ADDRESS);
        return createSendTx(fromAddress, prikey, txFunction, value, multySignContractAddress);
    }

    /**
     * 充值主资产（不签名）
     */
    public RawTransaction createRechargeMainAssetWithoutSign(String fromAddress, BigInteger value, String toAddress, String multySignContractAddress) throws Exception {
        Function txFunction = getCrossOutFunction(toAddress, value, ZERO_ADDRESS);
        return createSendTxWithoutSign(fromAddress, txFunction, value, multySignContractAddress);
    }

    /**
     * 充值主资产 加速（不签名）
     */
    public RawTransaction createRechargeMainAssetWithoutSign(String fromAddress, BigInteger value, String toAddress, String multySignContractAddress, BigInteger gasPrice, BigInteger nonce) throws Exception {
        Function txFunction = getCrossOutFunction(toAddress, value, ZERO_ADDRESS);
        return createSendTxWithoutSign(fromAddress, txFunction, value, multySignContractAddress, gasPrice, nonce);
    }

    /**
     * 充值ERC20
     * 1.授权使用ERC20资产
     * 2.充值
     */
    public EthSendTransactionPo rechargeErc20(String fromAddress, String prikey, BigInteger value, String toAddress, String multySignContractAddress, String bep20ContractAddress) throws Exception {
        return rechargeErc20(fromAddress, prikey, value, toAddress, multySignContractAddress, bep20ContractAddress, null, null);
    }

    /**
     * 充值ERC20 加速 直接发送交易
     */
    public EthSendTransactionPo rechargeErc20(String fromAddress, String prikey, BigInteger value, String toAddress, String multySignContractAddress, String bep20ContractAddress, BigInteger gasPrice, BigInteger nonce) throws Exception {
        Function crossOutFunction = getCrossOutFunction(toAddress, value, bep20ContractAddress);
        return this.sendTx(fromAddress, prikey, crossOutFunction, value, multySignContractAddress, gasPrice, nonce);
    }

    /**
     * 充值ERC20(不发送)
     * 1.授权使用ERC20资产
     * 2.充值
     */
    @Override
    public EthSendTransactionPo createRechargeErc20WithGas(String fromAddress, String prikey, BigInteger value, String toAddress, String multySignContractAddress, String bep20ContractAddress, BigInteger gasLimit, BigInteger gasPrice) throws Exception {
        Function crossOutFunction = getCrossOutFunction(toAddress, value, bep20ContractAddress);
        return createSendTxWithGas(fromAddress, prikey, crossOutFunction, null, multySignContractAddress, gasLimit, gasPrice);
    }

    public EthSendTransactionPo createRechargeErc20(String fromAddress, String prikey, BigInteger value, String toAddress, String multySignContractAddress, String bep20ContractAddress) throws Exception {
        Function crossOutFunction = getCrossOutFunction(toAddress, value, bep20ContractAddress);
        return createSendTx(fromAddress, prikey, crossOutFunction, null, multySignContractAddress);
    }

    /**
     * 充值ERC20 (不签名)
     * 1.授权使用ERC20资产
     * 2.充值
     */
    public RawTransaction createRechargeErc20WithoutSign(String fromAddress, BigInteger value, String toAddress, String multySignContractAddress, String bep20ContractAddress) throws Exception {
        Function crossOutFunction = getCrossOutFunction(toAddress, value, bep20ContractAddress);
        return createSendTxWithoutSign(fromAddress, crossOutFunction, null, multySignContractAddress);
    }

    /**
     * 充值ERC20 加速 (不签名)
     */
    public RawTransaction createRechargeErc20WithoutSign(String fromAddress, BigInteger value, String toAddress, String multySignContractAddress, String bep20ContractAddress, BigInteger gasPrice, BigInteger nonce) throws Exception {
        Function crossOutFunction = getCrossOutFunction(toAddress, value, bep20ContractAddress);
        return createSendTxWithoutSign(fromAddress, crossOutFunction, null, multySignContractAddress, gasPrice, nonce);
    }

    /**
     * ERC20 授权
     */
    public String authorization(String fromAddress, String prikey, String multySignContractAddress, String bep20Address) throws Exception {
        BigInteger approveAmount = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF", 16);
        Function approveFunction = this.getERC20ApproveFunction(multySignContractAddress, approveAmount);
        String authHash = this.sendTx(fromAddress, prikey, approveFunction, null, bep20Address).getTxHash();
        return authHash;
    }

    /**
     * ERC20 授权
     */
    public RawTransaction authorizationWithoutSign(String fromAddress, String multySignContractAddress, String bep20Address) throws Exception {
        BigInteger approveAmount = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF", 16);
        Function approveFunction = this.getERC20ApproveFunction(multySignContractAddress, approveAmount);
        RawTransaction rawTransaction = createSendTxWithoutSign(fromAddress, approveFunction, null, bep20Address);
        return rawTransaction;
    }

    /**
     * 是否已授权过
     *
     * @throws Exception
     */
    public boolean isAuthorized(String fromAddress, String multySignContractAddress, String bep20Address) throws Exception {
        Function allowanceFunction = new Function("allowance",
                Arrays.asList(new Address(fromAddress), new Address(multySignContractAddress)),
                Arrays.asList(new TypeReference<Uint256>() {
                }));
        BigInteger approveAmount = new BigInteger("39600000000000000000000000000");
        BigInteger allowanceAmount = (BigInteger) callViewFunction(bep20Address, allowanceFunction).get(0).getValue();
        if (allowanceAmount.compareTo(approveAmount) > 0) {
            return true;
        }
        return false;
    }

    private Function getCrossOutFunction(String toAddress, BigInteger value, String erc20) {
        List<Type> inputParameters = new ArrayList<>();
        inputParameters.add(new Utf8String(toAddress));
        inputParameters.add(new Uint256(value));
        inputParameters.add(new Address(erc20));

        List<TypeReference<?>> outputParameters = new ArrayList<>();
        outputParameters.add(new TypeReference<Type>() {
        });

        return new Function(Constant.METHOD_CROSS_OUT, inputParameters, outputParameters);
    }

    protected Function getERC20ApproveFunction(String spender, BigInteger value) {
        List<Type> inputParameters = new ArrayList<>();
        inputParameters.add(new Address(spender));
        inputParameters.add(new Uint256(value));

        List<TypeReference<?>> outputParameters = new ArrayList<>();
        outputParameters.add(new TypeReference<Type>() {
        });
        return new Function(
                "approve",
                inputParameters,
                outputParameters
        );
    }

    public int getContractTokenDecimals(String tokenContract) throws Exception {
        List<Type> decimalsResult = this.callViewFunction(tokenContract, HtgCommonTools.getDecimalsERC20Function());
        if (decimalsResult == null || decimalsResult.isEmpty()) {
            return -1;
        }
        String decimals = decimalsResult.get(0).getValue().toString();
        return Integer.parseInt(decimals);
    }

    public BigInteger totalSupply(String contractAddress) throws Exception {
        Function allowanceFunction = new Function("totalSupply",
                new ArrayList<>(),
                Arrays.asList(new TypeReference<Uint256>() {
                }));
        List<Type> totalSupplyResult = this.callViewFunction(contractAddress, allowanceFunction);
        if (totalSupplyResult == null || totalSupplyResult.isEmpty()) {
            return null;
        }
        return (BigInteger) totalSupplyResult.get(0).getValue();
    }

    public EthSendTransactionPo sendTx(String fromAddress, String priKey, Function txFunction, String contract) throws Exception {
        return this.sendTx(fromAddress, priKey, txFunction, null, contract);
    }

    public EthSendTransactionPo sendTx(String fromAddress, String priKey, Function txFunction, BigInteger value, String contract) throws Exception {
        return sendTx(fromAddress, priKey, txFunction, value, contract, null, null);
    }

    public EthSendTransactionPo sendTx(String fromAddress, String priKey, Function txFunction, BigInteger value, String contract, BigInteger gasPrice, BigInteger nonce) throws Exception {
        // 估算GasLimit
        EthEstimateGas ethEstimateGas = this.ethEstimateGasInner(fromAddress, contract, FunctionEncoder.encode(txFunction), value);
        if (ethEstimateGas.getError() != null) {
            throw new Exception(ethEstimateGas.getError().getMessage());
        }
        BigInteger gasLimit = ethEstimateGas.getAmountUsed();
        return callContract(fromAddress, priKey, contract, gasLimit, txFunction, value, gasPrice, nonce);
    }

    public RawTransaction createSendTxWithoutSign(String fromAddress, Function txFunction, BigInteger value, String contract) throws Exception {
        // 估算GasLimit
        EthEstimateGas ethEstimateGas = this.ethEstimateGasInner(fromAddress, contract, FunctionEncoder.encode(txFunction), value);
        if (ethEstimateGas.getError() != null) {
            throw new Exception(ethEstimateGas.getError().getMessage());
        }
        BigInteger gasLimit = ethEstimateGas.getAmountUsed();
        return createCallContractWithoutSign(fromAddress, contract, gasLimit, txFunction, value, null);
    }

    public RawTransaction createSendTxWithoutSign(String fromAddress, Function txFunction, BigInteger value, String contract, BigInteger gasPrice, BigInteger nonce) throws Exception {
        // 估算GasLimit
        EthEstimateGas ethEstimateGas = this.ethEstimateGasInner(fromAddress, contract, FunctionEncoder.encode(txFunction), value);
        if (ethEstimateGas.getError() != null) {
            throw new Exception(ethEstimateGas.getError().getMessage());
        }
        BigInteger gasLimit = ethEstimateGas.getAmountUsed();
        return createCallContractWithoutSign(fromAddress, contract, gasLimit, txFunction, value, gasPrice, nonce);
    }

    public EthSendTransactionPo createSendTx(String fromAddress, String priKey, Function txFunction, BigInteger value, String contract) throws Exception {
        // 估算GasLimit
        EthEstimateGas ethEstimateGas = this.ethEstimateGasInner(fromAddress, contract, FunctionEncoder.encode(txFunction), value);
        if (ethEstimateGas.getError() != null) {
            throw new Exception(ethEstimateGas.getError().getMessage());
        }
        BigInteger gasLimit = ethEstimateGas.getAmountUsed();
        return createCallContract(fromAddress, priKey, contract, gasLimit, txFunction, value, null);
    }

    private EthSendTransactionPo createSendTxWithGas(String fromAddress, String priKey, Function txFunction, BigInteger value, String contract, BigInteger gasLimit, BigInteger gasPrice) throws Exception {
        // 验证合约交易合法性
        //EthCall ethCall = validateContractCall(fromAddress, contract, txFunction, value);
        //if (ethCall.isReverted()) {
        //    throw new Exception("verify error - " + ethCall.getRevertReason());
        //}
        return createCallContract(fromAddress, priKey, contract, gasLimit, txFunction, value, gasPrice);
    }


    public TransactionReceipt getTxReceipt(String txHash) throws Exception {
        return this.timeOutWrapperFunction("getTxReceipt", txHash, args -> {
            Optional<TransactionReceipt> result = web3j.ethGetTransactionReceipt(args).send().getTransactionReceipt();
            if (result == null || !result.isPresent()) {
                return null;
            }
            return result.get();
        });
    }

    /**
     * 调用合约的view/constant函数
     */
    public List<Type> callViewFunction(String contractAddress, Function function) throws Exception {
        return this.callViewFunction(contractAddress, function, false);
    }

    public List<Type> callViewFunction(String contractAddress, Function function, boolean latest) throws Exception {
        String encode = FunctionEncoder.encode(function);
        List argsList = new ArrayList();
        argsList.add(contractAddress);
        argsList.add(encode);
        argsList.add(latest);
        List<Type> typeList = this.timeOutWrapperFunction("callViewFunction", argsList, args -> {
            String _contractAddress = (String) args.get(0);
            String _encode = (String) args.get(1);
            boolean _latest = (Boolean) args.get(2);
            org.web3j.protocol.core.methods.request.Transaction ethCallTransaction = org.web3j.protocol.core.methods.request.Transaction.createEthCallTransaction(null, _contractAddress, _encode);
            DefaultBlockParameterName parameterName = DefaultBlockParameterName.PENDING;
            if (_latest) {
                parameterName = DefaultBlockParameterName.LATEST;
            }
            EthCall ethCall = web3j.ethCall(ethCallTransaction, parameterName).sendAsync().get();
            String value = ethCall.getResult();
            if (StringUtils.isBlank(value)) {
                return null;
            }
            return FunctionReturnDecoder.decode(value, function.getOutputParameters());
        });
        return typeList;
    }

    private EthSendTransactionPo callContract(String from, String privateKey, String contractAddress, BigInteger gasLimit, Function function) throws Exception {
        return this.callContract(from, privateKey, contractAddress, gasLimit, function, null, null, null);
    }

    private EthSendTransactionPo callContract(String from, String privateKey, String contractAddress, BigInteger gasLimit, Function function, BigInteger value, BigInteger gasPrice, BigInteger nonce) throws Exception {
        value = value == null ? BigInteger.ZERO : value;
        gasPrice = gasPrice == null || gasPrice.compareTo(BigInteger.ZERO) == 0 ? this.getCurrentGasPrice() : gasPrice;
        String encodedFunction = FunctionEncoder.encode(function);
        List argsList = new ArrayList();
        argsList.add(from);
        argsList.add(privateKey);
        argsList.add(contractAddress);
        argsList.add(gasLimit);
        argsList.add(encodedFunction);
        argsList.add(value);
        argsList.add(gasPrice);
        argsList.add(nonce);

        EthSendTransactionPo txPo = this.timeOutWrapperFunction("callContract", argsList, args -> {
            int i = 0;
            String _from = args.get(i++).toString();
            String _privateKey = args.get(i++).toString();
            String _contractAddress = args.get(i++).toString();
            BigInteger _gasLimit = (BigInteger) args.get(i++);
            String _encodedFunction = args.get(i++).toString();
            BigInteger _value = (BigInteger) args.get(i++);
            BigInteger _gasPrice = (BigInteger) args.get(i++);
            BigInteger nonceArg = (BigInteger) args.get(i++);
            Credentials credentials = Credentials.create(_privateKey);

            nonceArg = nonceArg == null || nonceArg.compareTo(BigInteger.ZERO) == 0 ? this.getNonce(_from) : nonceArg;

            RawTransaction rawTransaction = RawTransaction.createTransaction(
                    nonceArg,
                    _gasPrice,
                    _gasLimit,
                    _contractAddress,
                    _value,
                    _encodedFunction
            );
            //签名Transaction，这里要对交易做签名
            byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, chainId(), credentials);
            String hexValue = Numeric.toHexString(signMessage);
            //发送交易
            EthSendTransaction send = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
            if (send == null) {
                throw new BusinessRuntimeException("send transaction request error");
            }
            if (send.hasError()) {
                throw new BusinessRuntimeException(send.getError().getMessage());
            }
            return new EthSendTransactionPo(send.getTransactionHash(), _from, rawTransaction);
        });
        return txPo;
    }


    private RawTransaction createCallContractWithoutSign(String from,
                                                         String contractAddress,
                                                         BigInteger gasLimit,
                                                         Function function,
                                                         BigInteger value,
                                                         BigInteger gasPrice) throws Exception {
        return createCallContractWithoutSign(from, contractAddress, gasLimit, function, value, gasPrice, null);
    }

    private RawTransaction createCallContractWithoutSign(String from,
                                                         String contractAddress,
                                                         BigInteger gasLimit,
                                                         Function function,
                                                         BigInteger value,
                                                         BigInteger gasPrice,
                                                         BigInteger nonce) throws Exception {
        value = value == null ? BigInteger.ZERO : value;
        gasPrice = gasPrice == null || gasPrice.compareTo(BigInteger.ZERO) == 0 ? this.getCurrentGasPrice() : gasPrice;
        String encodedFunction = FunctionEncoder.encode(function);
        nonce = nonce == null || nonce.compareTo(BigInteger.ZERO) == 0 ? this.getNonce(from) : nonce;
        RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce,
                gasPrice,
                gasLimit,
                contractAddress,
                value,
                encodedFunction
        );
        return rawTransaction;
    }

    private EthSendTransactionPo createCallContract(String from,
                                                    String privateKey,
                                                    String contractAddress,
                                                    BigInteger gasLimit,
                                                    Function function,
                                                    BigInteger value,
                                                    BigInteger gasPrice) throws Exception {

        RawTransaction rawTransaction = createCallContractWithoutSign(from, contractAddress, gasLimit, function, value, gasPrice);
        Credentials credentials = Credentials.create(privateKey);
        //签名Transaction，这里要对交易做签名
        byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, chainId(), credentials);
        String hexValue = Numeric.toHexString(signMessage);
        return new EthSendTransactionPo(from, rawTransaction, hexValue);
    }

/*
    private EthSendTransactionPo createCallContract(String from,
                                      String privateKey,
                                      String contractAddress,
                                      BigInteger gasLimit,
                                      Function function,
                                      BigInteger value,
                                      BigInteger gasPrice) throws Exception {
        value = value == null ? BigInteger.ZERO : value;
        gasPrice = gasPrice == null || gasPrice.compareTo(BigInteger.ZERO) == 0 ? this.getCurrentGasPrice() : gasPrice;
        String encodedFunction = FunctionEncoder.encode(function);
        List argsList = new ArrayList();
        argsList.add(from);
        argsList.add(privateKey);
        argsList.add(contractAddress);
        argsList.add(gasLimit);
        argsList.add(encodedFunction);
        argsList.add(value);
        argsList.add(gasPrice);
        EthSendTransactionPo txPo = this.timeOutWrapperFunction("callContract", argsList, args -> {
            int i = 0;
            String _from = args.get(i++).toString();
            String _privateKey = args.get(i++).toString();
            String _contractAddress = args.get(i++).toString();
            BigInteger _gasLimit = (BigInteger) args.get(i++);
            String _encodedFunction = args.get(i++).toString();
            BigInteger _value = (BigInteger) args.get(i++);
            BigInteger _gasPrice = (BigInteger) args.get(i++);
            Credentials credentials = Credentials.create(_privateKey);
            BigInteger nonce = this.getNonce(_from);
            RawTransaction rawTransaction = RawTransaction.createTransaction(
                    nonce,
                    _gasPrice,
                    _gasLimit,
                    _contractAddress,
                    _value,
                    _encodedFunction
            );
            //签名Transaction，这里要对交易做签名
            byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, chainId(), credentials);
            String hexValue = Numeric.toHexString(signMessage);
            return new EthSendTransactionPo(_from, rawTransaction, hexValue);
        });
        return txPo;
    }
*/

    private EthCall validateContractCall(String from, String contractAddress, Function function) throws Exception {
        String encodedFunction = FunctionEncoder.encode(function);
        return this.validateContractCall(from, contractAddress, encodedFunction, null);
    }

    private EthCall validateContractCall(String from, String contractAddress, Function function, BigInteger value) throws Exception {
        String encodedFunction = FunctionEncoder.encode(function);
        return this.validateContractCall(from, contractAddress, encodedFunction, value);
    }

    private EthCall validateContractCall(String from, String contractAddress, String encodedFunction) throws Exception {
        return this.validateContractCall(from, contractAddress, encodedFunction, null);
    }

    private EthCall validateContractCall(String from, String contractAddress, String encodedFunction, BigInteger value) throws Exception {
        value = value == null ? BigInteger.ZERO : value;

        List argsList = new ArrayList();
        argsList.add(from);
        argsList.add(contractAddress);
        argsList.add(encodedFunction);
        argsList.add(value);
        EthCall ethCall = this.timeOutWrapperFunction("validateContractCall", argsList, args -> {
            String _from = args.get(0).toString();
            String _contractAddress = args.get(1).toString();
            String _encodedFunction = (String) args.get(2);
            BigInteger _value = (BigInteger) args.get(3);

            org.web3j.protocol.core.methods.request.Transaction tx = new org.web3j.protocol.core.methods.request.Transaction(
                    _from,
                    null,
                    null,
                    null,
                    _contractAddress,
                    _value,
                    _encodedFunction
            );

            EthCall _ethCall = web3j.ethCall(tx, DefaultBlockParameterName.LATEST).send();
            return _ethCall;
        });
        return ethCall;
    }

    private BigInteger ethEstimateGas(String from, String contractAddress, Function function) throws Exception {
        String encodedFunction = FunctionEncoder.encode(function);
        return this.ethEstimateGas(from, contractAddress, encodedFunction, null);
    }

    private BigInteger ethEstimateGas(String from, String contractAddress, Function function, BigInteger value) throws Exception {
        String encodedFunction = FunctionEncoder.encode(function);
        return this.ethEstimateGas(from, contractAddress, encodedFunction, value);
    }

    private BigInteger ethEstimateGas(String from, String contractAddress, String encodedFunction, BigInteger value) throws Exception {
        EthEstimateGas ethEstimateGas = this.ethEstimateGasInner(from, contractAddress, encodedFunction, value);
        if (StringUtils.isBlank(ethEstimateGas.getResult())) {
            return BigInteger.ZERO;
        }
        return ethEstimateGas.getAmountUsed();
    }

    private EthEstimateGas ethEstimateGasInner(String from, String contractAddress, String encodedFunction, BigInteger value) throws Exception {
        value = value == null ? BigInteger.ZERO : value;
        List argsList = new ArrayList();
        argsList.add(from);
        argsList.add(contractAddress);
        argsList.add(encodedFunction);
        argsList.add(value);
        EthEstimateGas gas = this.timeOutWrapperFunction("ethEstimateGas", argsList, args -> {
            String _from = args.get(0).toString();
            String _contractAddress = args.get(1).toString();
            String _encodedFunction = (String) args.get(2);
            BigInteger _value = (BigInteger) args.get(3);

            org.web3j.protocol.core.methods.request.Transaction tx = new org.web3j.protocol.core.methods.request.Transaction(
                    _from,
                    null,
                    null,
                    null,
                    _contractAddress,
                    _value,
                    _encodedFunction
            );
            EthEstimateGas estimateGas = web3j.ethEstimateGas(tx).send();
            return estimateGas;
        });
        return gas;
    }

    public BigInteger getCurrentGasPrice() throws IOException {
        return web3j.ethGasPrice().send().getGasPrice();
    }

    @Override
    public EthSendTransactionPo sendRawTransaction(String privateKey, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to, BigInteger value, String data) throws Exception {
        Credentials credentials = Credentials.create(privateKey);
        String from = credentials.getAddress();
        nonce = nonce == null ? this.getNonce(from) : nonce;
        gasPrice = gasPrice == null || gasPrice.compareTo(BigInteger.ZERO) == 0 ? this.getCurrentGasPrice() : gasPrice;
        if (gasLimit == null || gasLimit.compareTo(BigInteger.ZERO) == 0) {
            if (StringUtils.isBlank(data) || "0x".equals(data)) {
                gasLimit = Constant.GAS_LIMIT_OF_MAIN;
            } else {
                gasLimit = new BigDecimal(this.ethEstimateGas(from, to, data, value)).multiply(new BigDecimal("1.2")).toBigInteger();
            }
        }
        value = value == null ? BigInteger.ZERO : value;
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, to, value, data);
        //签名Transaction，这里要对交易做签名
        byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, chainId(), credentials);
        String hexValue = Numeric.toHexString(signMessage);
        //发送交易
        EthSendTransaction send = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
        if (send == null) {
            throw new RuntimeException("send transaction request error");
        }
        if (send.hasError()) {
            throw new RuntimeException(send.getError().getMessage());
        }
        return new EthSendTransactionPo(null, from, rawTransaction, hexValue);
    }

    @Override
    public String sendRawTransactionWithoutBroadcast(String privateKey, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to, BigInteger value, String data) throws Exception {
        Credentials credentials = Credentials.create(privateKey);
        String from = credentials.getAddress();
        nonce = nonce == null ? this.getNonce(from) : nonce;
        gasPrice = gasPrice == null || gasPrice.compareTo(BigInteger.ZERO) == 0 ? this.getCurrentGasPrice() : gasPrice;
        if (gasLimit == null || gasLimit.compareTo(BigInteger.ZERO) == 0) {
            if (StringUtils.isBlank(data) || "0x".equals(data)) {
                gasLimit = Constant.GAS_LIMIT_OF_MAIN;
            } else {
                gasLimit = new BigDecimal(this.ethEstimateGas(from, to, data, value)).multiply(new BigDecimal("1.2")).toBigInteger();
            }
        }
        value = value == null ? BigInteger.ZERO : value;
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, to, value, data);
        //签名Transaction，这里要对交易做签名
        byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, chainId(), credentials);
        String txHex = Numeric.toHexString(signMessage);
        return txHex;
    }

    @Override
    public EthCall validateRawTransaction(String from, String to, String data, BigInteger value) throws Exception {
        value = value == null ? BigInteger.ZERO : value;
        org.web3j.protocol.core.methods.request.Transaction tx = new org.web3j.protocol.core.methods.request.Transaction(
                from,
                null,
                null,
                null,
                to,
                value,
                data
        );
        EthCall _ethCall = web3j.ethCall(tx, DefaultBlockParameterName.LATEST).send();
        return _ethCall;
    }

    @Override
    public EthCall ethCall(String from, String to, BigInteger gasLimit, BigInteger gasPrice, BigInteger value, String data, boolean latest) throws Exception {
        value = value == null ? BigInteger.ZERO : value;

        org.web3j.protocol.core.methods.request.Transaction tx = new org.web3j.protocol.core.methods.request.Transaction(
                from,
                null,
                gasPrice,
                gasLimit,
                to,
                value,
                data
        );
        DefaultBlockParameterName parameterName = DefaultBlockParameterName.PENDING;
        if (latest) {
            parameterName = DefaultBlockParameterName.LATEST;
        }
        EthCall ethCall = web3j.ethCall(tx, parameterName).sendAsync().get();
        return ethCall;
    }

    @Override
    public EthEstimateGas ethEstimateGas(String from, String to, BigInteger gasLimit, BigInteger gasPrice, BigInteger value, String data) throws Exception {
        value = value == null ? BigInteger.ZERO : value;

        org.web3j.protocol.core.methods.request.Transaction tx = new org.web3j.protocol.core.methods.request.Transaction(
                from,
                null,
                gasPrice,
                gasLimit,
                to,
                value,
                data
        );
        EthEstimateGas estimateGas = web3j.ethEstimateGas(tx).send();
        return estimateGas;
    }

    @Override
    public RpcResult request(String requestURL, String method, List<Object> params) {
        String url = requestURL;
        if (requestURL.endsWith("/")) {
            url = url + FORWARD_PATH;
        } else {
            url = url + "/" + FORWARD_PATH;
        }
        return JsonRpcUtil.requestForMetaMask(requestURL, chainName, method, params);
    }

    public Web3j getWeb3j() {
        return web3j;
    }

    public String ethSign(String priKey, String dataHex) {
        dataHex = Numeric.cleanHexPrefix(dataHex);
        byte[] bytes = HexUtil.decode(dataHex);
        return ethSign(priKey, bytes);
    }

    public String personalSign(String priKey, String data) {
        byte[] bytes = this.dataToBytes(data);
        Credentials credentials = Credentials.create(priKey);
        Sign.SignatureData signatureData = Sign.signPrefixedMessage(bytes, credentials.getEcKeyPair());
        byte[] bytesValue = org.apache.commons.lang3.ArrayUtils.addAll(signatureData.getR(), signatureData.getS());
        bytesValue = ArrayUtils.addAll(bytesValue, signatureData.getV());
        String result = "0x" + HexUtil.encode(bytesValue);
        return result;
    }

    public String personalSign(String priKey, byte[] bytes) {
        Credentials credentials = Credentials.create(priKey);
        Sign.SignatureData signatureData = Sign.signPrefixedMessage(bytes, credentials.getEcKeyPair());
        byte[] bytesValue = org.apache.commons.lang3.ArrayUtils.addAll(signatureData.getR(), signatureData.getS());
        bytesValue = ArrayUtils.addAll(bytesValue, signatureData.getV());
        String result = "0x" + HexUtil.encode(bytesValue);
        return result;
    }

    @Override
    public String signTypedDataV4(String priKey, String json) throws IOException {
        json = json.replace("\\\"", "\"");
        StructuredDataEncoder encoder = new StructuredDataEncoder(json);
        byte[] hash = encoder.hashStructuredData();
        return ethSign(priKey, hash);
    }

    private String ethSign(String priKey, byte[] bytes) {
        Credentials credentials = Credentials.create(priKey);
        //得到签名
        Sign.SignatureData signatureData = Sign.signMessage(bytes, credentials.getEcKeyPair(), false);
        byte[] bytesValue = org.apache.commons.lang3.ArrayUtils.addAll(signatureData.getR(), signatureData.getS());
        bytesValue = ArrayUtils.addAll(bytesValue, signatureData.getV());
        String result = "0x" + HexUtil.encode(bytesValue);
        return result;
    }

    private byte[] dataToBytes(String data) {
        if (StringUtils.isBlank(data)) {
            return null;
        }
        String cleanData = Numeric.cleanHexPrefix(data);
        try {
            boolean isHex = true;
            char[] chars = cleanData.toCharArray();
            for (char c : chars) {
                int digit = Character.digit(c, 16);
                if (digit == -1) {
                    isHex = false;
                    break;
                }
            }
            if (isHex) {
                return HexUtil.decode(cleanData);
            }
            return data.getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            return data.getBytes(StandardCharsets.UTF_8);
        }
    }

    public MultiCallResult multiCall(String multiCallAddress, List<MultiCallModel> multiCallModelList) throws Exception {
        Function aggregateFunction = createAggregateFunction(multiCallModelList);
        String encodeFunctionData = FunctionEncoder.encode(aggregateFunction);
        org.web3j.protocol.core.methods.request.Transaction ethCallTransaction = org.web3j.protocol.core.methods.request.Transaction.createEthCallTransaction(
                Address.DEFAULT.getValue(), multiCallAddress, encodeFunctionData);

        EthCall ethCall = web3j.ethCall(ethCallTransaction, DefaultBlockParameterName.PENDING).sendAsync().get();
        MultiCallResult result = new MultiCallResult();
        if (ethCall.getError() != null) {
            CallError callError = new CallError(ethCall.getError().getCode(), ethCall.getError().getMessage());
            result.setCallError(callError);
            return result;
        }
        if (ethCall.getValue().equals("0x")) {
            CallError callError = new CallError(-32000, "multiCallAddress error");
            result.setCallError(callError);
            return result;
        }
        List<Type> multiType = FunctionReturnDecoder.decode(ethCall.getValue(), aggregateFunction.getOutputParameters());
        //获取当前区块高度
        Uint256 uint256 = (Uint256) multiType.get(0);
        long blockHeight = uint256.getValue().longValue();

        DynamicArray<DynamicBytes> dynamicArray = (DynamicArray<DynamicBytes>) multiType.get(1);
        List<DynamicBytes> dynamicBytesList = dynamicArray.getValue();

        List<List<Type>> multiResultList = processMultiCallResult(dynamicBytesList, multiCallModelList);
        result.setBlockHeight(blockHeight);
        result.setMultiResultList(multiResultList);
        return result;
    }

    public MultiCallResult tryMultiCall(String multiCallAddress, List<MultiCallModel> multiCallModelList) throws Exception {
        Function aggregateFunction = createTryAggregateFunction(multiCallModelList);
        String encodeFunctionData = FunctionEncoder.encode(aggregateFunction);
        org.web3j.protocol.core.methods.request.Transaction ethCallTransaction = org.web3j.protocol.core.methods.request.Transaction.createEthCallTransaction(
                Address.DEFAULT.getValue(), multiCallAddress, encodeFunctionData);

        EthCall ethCall = web3j.ethCall(ethCallTransaction, DefaultBlockParameterName.LATEST).sendAsync().get();
        MultiCallResult result = new MultiCallResult();
        if (ethCall.getError() != null) {
            CallError callError = new CallError(ethCall.getError().getCode(), ethCall.getError().getMessage());
            result.setCallError(callError);
            return result;
        }
        if (ethCall.getValue().equals("0x")) {
            CallError callError = new CallError(-32000, "multiCallAddress error");
            result.setCallError(callError);
            return result;
        }
        List<Type> multiType = FunctionReturnDecoder.decode(ethCall.getValue(), aggregateFunction.getOutputParameters());
        DynamicArray dynamicArray = (DynamicArray) multiType.get(0);

        List<List<Type>> multiResultList = processTryMultiCallResult(dynamicArray.getValue(), multiCallModelList);
        result.setMultiResultList(multiResultList);
        return result;
    }

    private List<List<Type>> processMultiCallResult(List<DynamicBytes> dynamicBytesList, List<MultiCallModel> multiCallModelList) {
        return HtgTool.processMultiCallResult(dynamicBytesList, multiCallModelList);
    }

    private List<List<Type>> processTryMultiCallResult(List<TryMultiCallReturn> returnList, List<MultiCallModel> multiCallModelList) {
        return HtgTool.processTryMultiCallResult(returnList, multiCallModelList);
    }

    public BigInteger estimateGasForTransferERC721(String contractAddress, String from, String to, BigInteger tokenId, String data) throws Exception {
        Function function = EthFunctionUtil.getERC721TransferFunction(from, to, tokenId, data);
        EthEstimateGas ethEstimateGas = this.ethEstimateGasInner(from, contractAddress, FunctionEncoder.encode(function), null);
        if (ethEstimateGas.getError() != null) {
            throw new Exception(ethEstimateGas.getError().getMessage());
        }
        BigInteger gasLimit = ethEstimateGas.getAmountUsed();
        return gasLimit.add(BI_10000);
    }

    public BigInteger estimateGasForTransferERC1155(String contractAddress, String from, String to, List<Uint256> tokenIdList, List<Uint256> values, String data) throws Exception {
        Function function = EthFunctionUtil.getERC1155TransferFunction(from, to, tokenIdList, values, data);
        EthEstimateGas ethEstimateGas = this.ethEstimateGasInner(from, contractAddress, FunctionEncoder.encode(function), null);
        if (ethEstimateGas.getError() != null) {
            throw new Exception(ethEstimateGas.getError().getMessage());
        }
        BigInteger gasLimit = ethEstimateGas.getAmountUsed();
        return gasLimit.add(BI_10000);
    }


    @Override
    public EthSendTransactionPo createTransferERC721Token(String contractAddress, String from, String to, BigInteger tokenId, String data, String privateKey, BigInteger gasLimit, BigInteger gasPrice) throws Exception {
        RawTransaction rawTransaction = createTransferERC721TokenWithoutSign(from, to, tokenId, data, contractAddress, gasLimit, gasPrice);
        //加载转账所需的凭证，用私钥
        Credentials credentials = Credentials.create(privateKey);
        //签名Transaction，这里要对交易做签名
        byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, chainId(), credentials);
        String hexValue = Numeric.toHexString(signMessage);
        return new EthSendTransactionPo(from, rawTransaction, hexValue);
    }

    @Override
    public EthSendTransactionPo createTransferERC1155Token(String contractAddress, String from, String to, List<Uint256> tokenIdList, List<Uint256> values, String data, String privateKey, BigInteger gasLimit, BigInteger gasPrice) throws Exception {
        RawTransaction rawTransaction = createTransferERC1155TokenWithoutSign(from, to, tokenIdList, values, data, contractAddress, gasLimit, gasPrice);
        //加载转账所需的凭证，用私钥
        Credentials credentials = Credentials.create(privateKey);
        //签名Transaction，这里要对交易做签名
        byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, chainId(), credentials);
        String hexValue = Numeric.toHexString(signMessage);
        return new EthSendTransactionPo(from, rawTransaction, hexValue);
    }


    public RawTransaction createTransferERC721TokenWithoutSign(String from,
                                                               String to,
                                                               BigInteger tokenId,
                                                               String data,
                                                               String contractAddress,
                                                               BigInteger gasLimit,
                                                               BigInteger gasPrice) throws Exception {
        //获取nonce，交易笔数
        BigInteger nonce = getNonce(from);
        //创建RawTransaction交易对象
        Function function = EthFunctionUtil.getERC721TransferFunction(from, to, tokenId, data);
        String encodedFunction = FunctionEncoder.encode(function);
        RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce,
                gasPrice,
                gasLimit,
                contractAddress, encodedFunction
        );
        return rawTransaction;
    }

    public RawTransaction createTransferERC1155TokenWithoutSign(String from,
                                                                String to,
                                                                List<Uint256> tokenIdList,
                                                                List<Uint256> values,
                                                                String data,
                                                                String contractAddress,
                                                                BigInteger gasLimit,
                                                                BigInteger gasPrice) throws Exception {
        //获取nonce，交易笔数
        BigInteger nonce = getNonce(from);
        //创建RawTransaction交易对象
        Function function = EthFunctionUtil.getERC1155TransferFunction(from, to, tokenIdList, values, data);
        String encodedFunction = FunctionEncoder.encode(function);
        RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce,
                gasPrice,
                gasLimit,
                contractAddress, encodedFunction
        );
        return rawTransaction;
    }


    public BigInteger getERC1155Balance(String address, String contractAddress, BigInteger tokenId) throws Exception {
        return this.getERC1155BalanceReal(address, contractAddress, tokenId, DefaultBlockParameterName.LATEST, 0);
    }

    @Override
    public BigInteger getTotalSupply(String tokenAddress) throws Exception {
        //  return getTotalSupply(contractAddress, 0);
        Function function = EthFunctionUtil.getTotalSupply();
        String encodeFunctionData = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.request.Transaction tx = org.web3j.protocol.core.methods.request.Transaction.createEthCallTransaction(Address.DEFAULT.getValue(), tokenAddress, encodeFunctionData);
        EthCall ethCall = web3j.ethCall(tx, DefaultBlockParameterName.LATEST).sendAsync().get();
        List<Type> resultList = FunctionReturnDecoder.decode(ethCall.getResult(), function.getOutputParameters());
        if (resultList == null || resultList.isEmpty()) {
            return BigInteger.ZERO;
        }
        return (BigInteger) resultList.get(0).getValue();
    }

    public BigInteger getTotalSupply(String contractAddress, int times) throws Exception {
        try {
            this.checkIfResetWeb3j(times);
            List<Type> resultList = callViewFunction(contractAddress, EthFunctionUtil.getTotalSupply(), true);
            if (resultList == null || resultList.isEmpty()) {
                return BigInteger.ZERO;
            }
            return (BigInteger) resultList.get(0).getValue();
        } catch (Exception e) {
            String message = e.getMessage();
            boolean isTimeOut = HtgCommonTools.isTimeOutError(message);
            if (isTimeOut) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                return getTotalSupply(contractAddress, times + 1);
            } else {
                throw e;
            }
        }
    }

    private BigInteger getERC1155BalanceReal(String address, String contractAddress, BigInteger tokenId, DefaultBlockParameterName status, int times) throws Exception {
        try {
            this.checkIfResetWeb3j(times);
            Function function = new Function("balanceOf",
                    Arrays.asList(new Address(address), new Uint256(tokenId)),
                    Arrays.asList(new TypeReference<Uint256>() {
                    }));

            String encode = FunctionEncoder.encode(function);
            org.web3j.protocol.core.methods.request.Transaction ethCallTransaction = org.web3j.protocol.core.methods.request.Transaction.createEthCallTransaction(address, contractAddress, encode);
            EthCall ethCall = web3j.ethCall(ethCallTransaction, status).sendAsync().get();
            String value = ethCall.getResult();
            if (value.equals("0x")) {
                return BigInteger.ZERO;
            }
            BigInteger balance = new BigInteger(value.substring(2), 16);
            return balance;
        } catch (Exception e) {
            String message = e.getMessage();
            boolean isTimeOut = HtgCommonTools.isTimeOutError(message);
            if (isTimeOut) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                return getERC20BalanceReal(address, contractAddress, status, times + 1);
            } else {
                throw e;
            }
        }
    }

    public List<BigInteger> getERC1155BatchBalance(List<String> addressList, String contractAddress, List<Uint256> tokenIds) throws Exception {
        return this.getERC1155BatchBalanceReal(addressList, contractAddress, tokenIds, DefaultBlockParameterName.LATEST, 0);
    }

    private List<BigInteger> getERC1155BatchBalanceReal(List<String> addressList, String contractAddress, List<Uint256> tokenIds, DefaultBlockParameterName status, int times) throws Exception {
        try {
            this.checkIfResetWeb3j(times);
            List<Address> addresses = new ArrayList<>();
            for (int i = 0; i < addressList.size(); i++) {
                addresses.add(new Address(addressList.get(i)));
            }
            DynamicArray<Address> addressArray = new DynamicArray<Address>(Address.class, addresses);
            DynamicArray<Uint256> tokenIdArray = new DynamicArray<Uint256>(Uint256.class, tokenIds);

            Function function = new Function("balanceOfBatch",
                    Arrays.asList(addressArray, tokenIdArray),
                    Arrays.asList(new TypeReference<DynamicArray<Uint256>>() {
                    }));

            String encode = FunctionEncoder.encode(function);
            org.web3j.protocol.core.methods.request.Transaction ethCallTransaction = org.web3j.protocol.core.methods.request.Transaction.createEthCallTransaction(Address.DEFAULT.getValue(), contractAddress, encode);
            EthCall ethCall = web3j.ethCall(ethCallTransaction, status).sendAsync().get();
            String value = ethCall.getResult();
            List<Type> resultType = FunctionReturnDecoder.decode(value, function.getOutputParameters());
            DynamicArray<Uint256> resultArray = (DynamicArray<Uint256>) resultType.get(0);
            List<Uint256> resultList = resultArray.getValue();
            List<BigInteger> valueList = new ArrayList<>();
            for (int i = 0; i < resultList.size(); i++) {
                Uint256 uint256 = resultList.get(i);
                valueList.add(uint256.getValue());
            }

            return valueList;
        } catch (Exception e) {
            String message = e.getMessage();
            boolean isTimeOut = HtgCommonTools.isTimeOutError(message);
            if (isTimeOut) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                return getERC1155BatchBalanceReal(addressList, contractAddress, tokenIds, status, times + 1);
            } else {
                throw e;
            }
        }
    }

	public Function createAggregateFunction(List<MultiCallModel> multiCallModelList) {
        return HtgTool.createAggregateFunction(multiCallModelList);
    }


    public Function createTryAggregateFunction(List<MultiCallModel> multiCallModelList) {
        return HtgTool.createTryAggregateFunction(multiCallModelList);
    }

    public String getERC20TokenSymbol(String contractAddress) throws Exception {
        List<Type> symbolResult = this.callViewFunction(contractAddress, HtgCommonTools.getSymbolERC20Function());
        if (symbolResult == null || symbolResult.isEmpty()) {
            return null;
        }
        String symbol = symbolResult.get(0).getValue().toString();
        return symbol;
    }

    public String getERC20TokenName(String contractAddress) throws Exception {
        List<Type> nameResult = this.callViewFunction(contractAddress, HtgCommonTools.getNameERC20Function());
        if (nameResult == null || nameResult.isEmpty()) {
            return null;
        }
        String name = nameResult.get(0).getValue().toString();
        return name;
    }

    public int getERC20TokenDecimals(String contractAddress) throws Exception {
        return this.getContractTokenDecimals(contractAddress);
    }

    public BigInteger getERC20TokenTotalSupply(String contractAddress) throws Exception {
        return this.totalSupply(contractAddress);
    }

}
