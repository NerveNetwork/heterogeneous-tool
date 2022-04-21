package network.nerve.heterogeneous.core;

import com.google.protobuf.ByteString;
import network.nerve.heterogeneous.constant.TrxConstant;
import network.nerve.heterogeneous.model.TrxEstimateSun;
import network.nerve.heterogeneous.model.TrxSendTransactionPo;
import network.nerve.heterogeneous.utils.ListUtil;
import network.nerve.heterogeneous.utils.StringUtils;
import network.nerve.heterogeneous.utils.TrxUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tron.trident.abi.FunctionEncoder;
import org.tron.trident.abi.FunctionReturnDecoder;
import org.tron.trident.abi.datatypes.Function;
import org.tron.trident.abi.datatypes.Type;
import org.tron.trident.api.GrpcAPI;
import org.tron.trident.core.ApiWrapper;
import org.tron.trident.core.key.KeyPair;
import org.tron.trident.core.transaction.TransactionBuilder;
import org.tron.trident.proto.Chain;
import org.tron.trident.proto.Contract;
import org.tron.trident.proto.Response;
import org.tron.trident.utils.Numeric;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static network.nerve.heterogeneous.constant.TrxConstant.*;
import static org.tron.trident.core.ApiWrapper.parseAddress;
import static org.tron.trident.core.ApiWrapper.parseHex;

/**
 * @author: Mimi
 * @date: 2021/7/27
 */
public class TrxWalletApi {

    private static Logger Log = LoggerFactory.getLogger(TrxWalletApi.class.getName());

    private ApiWrapper wrapper;
    private String rpcAddress;
    private List<String> rpcAddressList;
    private int rpcIndex = 0;
    private String symbol;
    private String chainName;
    private String tempKey = "3333333333333333333333333333333333333333333333333333333333333333";

    public static TrxWalletApi getInstance(String rpcAddress) {
        return getInstance(ListUtil.of(rpcAddress));
    }

    public static TrxWalletApi getInstance(List<String> rpcAddressList) {
        if (rpcAddressList == null || rpcAddressList.isEmpty()) {
            throw new RuntimeException("rpcAddressList is null");
        }
        TrxWalletApi instance = new TrxWalletApi(rpcAddressList);
        return instance;
    }

    private TrxWalletApi(List<String> rpcAddressList) {
        this.symbol = "TRX";
        this.chainName = "TRON";
        this.rpcAddressList = rpcAddressList;
        this.rpcAddress = rpcAddressList.get(0);
        init(rpcAddress);
    }

    private void init(String rpcAddress) {
        // 初始化新的API服务
        if (StringUtils.isNotBlank(rpcAddress)) {
            resetApiWrapper();
        }
        if (wrapper == null) {
            wrapper = newInstanceApiWrapper(rpcAddress);
            this.rpcAddress = StringUtils.isBlank(rpcAddress) ? EMPTY_STRING : rpcAddress;
        }
    }

    private void changeApi() {
        resetApiWrapper();
        if (rpcAddressList != null && !rpcAddressList.isEmpty()) {
            rpcIndex++;
            if (rpcIndex >= rpcAddressList.size()) {
                rpcIndex = 0;
            }
            rpcAddress = rpcAddressList.get(rpcIndex);
        }
        init(rpcAddress);
    }

    private void resetApiWrapper() {
        if (wrapper != null) {
            wrapper.close();
            wrapper = null;
        }
    }

    private void checkIfResetApiWrapper(int times) {
        int mod = times % 4;
        if (mod == 3 && wrapper != null && rpcAddress != null) {
            resetApiWrapper();
            wrapper = newInstanceApiWrapper(rpcAddress);
        }
    }

    private ApiWrapper newInstanceApiWrapper(String rpcAddress) {
        if (StringUtils.isBlank(rpcAddress)) {
            return ApiWrapper.ofShasta(tempKey);
        } else if (rpcAddress.startsWith("endpoint")) {
            String[] split = rpcAddress.split(":");
            String rpc = split[1].trim();
            return new ApiWrapper(rpc + ":50051", rpc + ":50061", tempKey);
        } else {
            return ApiWrapper.ofMainnet(tempKey, rpcAddress);
        }
    }


    public Response.TransactionInfo getTransactionReceipt(String txHash) throws Exception {
        if (StringUtils.isBlank(txHash)) {
            return null;
        }
        txHash = Numeric.cleanHexPrefix(txHash);
        return this.timeOutWrapperFunction("getTxReceipt", txHash, args -> {
            ByteString bsTxid = parseAddress(args);
            GrpcAPI.BytesMessage request = GrpcAPI.BytesMessage.newBuilder()
                    .setValue(bsTxid)
                    .build();
            Response.TransactionInfo transactionInfo = wrapper.blockingStub.getTransactionInfoById(request);

            if (transactionInfo.getBlockTimeStamp() == 0) {
                return null;
            }
            return transactionInfo;
        });
    }

    private <T, R> R timeOutWrapperFunction(String functionName, T arg, ExceptionFunction<T, R> fucntion) throws Exception {
        return this.timeOutWrapperFunctionReal(functionName, fucntion, 0, arg);
    }

    private <T, R> R timeOutWrapperFunctionReal(String functionName, ExceptionFunction<T, R> fucntion, int times, T arg) throws Exception {
        try {
            this.checkIfResetApiWrapper(times);
            return fucntion.apply(arg);
        } catch (Exception e) {
            if (e instanceof BusinessRuntimeException || times > 1) {
                throw e;
            }
            changeApi();
            return timeOutWrapperFunctionReal(functionName, fucntion, times + 1, arg);
        }
    }


    /**
     * 获取交易详情
     */
    public Chain.Transaction getTransactionByHash(String txHash) throws Exception {
        if (StringUtils.isBlank(txHash)) {
            return null;
        }
        txHash = Numeric.cleanHexPrefix(txHash);
        return this.timeOutWrapperFunction("getTransactionById", txHash, args -> {
            ByteString bsTxid = parseAddress(args);
            GrpcAPI.BytesMessage request = GrpcAPI.BytesMessage.newBuilder().setValue(bsTxid).build();
            Chain.Transaction transaction = wrapper.blockingStub.getTransactionById(request);
            if (transaction.getRetCount() == 0) {
                return null;
            } else {
                return transaction;
            }
        });
    }

    /**
     * 调用合约的view/constant函数
     */
    public List<Type> callViewFunction(String contractAddress, Function function) throws Exception {
        List<Type> typeList = this.timeOutWrapperFunction("callViewFunction", ListUtil.of(contractAddress, function), args -> {
            String _contractAddress = (String) args.get(0);
            Function _function = (Function) args.get(1);
            Response.TransactionExtention call = wrapper.constantCall(TrxConstant.ZERO_ADDRESS_TRX, _contractAddress, _function);
            if (call.getConstantResultCount() == 0) {
                return null;
            }
            byte[] bytes = call.getConstantResult(0).toByteArray();
            return FunctionReturnDecoder.decode(Numeric.toHexString(bytes), _function.getOutputParameters());
        });
        return typeList;
    }


    public TrxEstimateSun estimateSunUsed(String from, String contractAddress, Function function) throws Exception {
        return this.estimateSunUsed(from, contractAddress, function, null);
    }

    public TrxEstimateSun estimateSunUsed(String from, String contractAddress, Function function, BigInteger value) throws Exception {
        value = value == null ? BigInteger.ZERO : value;
        TrxEstimateSun estimateEnergy = this.timeOutWrapperFunction("estimateEnergyUsed", ListUtil.of(from, contractAddress, function, value), args -> {
            BigInteger _value = (BigInteger) args.get(3);
            String encodedHex = FunctionEncoder.encode(function);
            Contract.TriggerSmartContract trigger = Contract.TriggerSmartContract.newBuilder()
                    .setOwnerAddress(parseAddress(from))
                    .setContractAddress(parseAddress(contractAddress))
                    .setData(parseHex(encodedHex))
                    .setCallValue(_value.longValue())
                    .build();
            Response.TransactionExtention call = wrapper.blockingStub.triggerConstantContract(trigger);

            if (call.getConstantResultCount() == 0) {
                return TrxEstimateSun.FAILED("Execute failed: empty result.");
            }
            String result = Numeric.toHexString(call.getConstantResult(0).toByteArray());
            if (TrxUtil.isErrorInResult(result)) {
                return TrxEstimateSun.FAILED(TrxUtil.getRevertReason(result));
            }
            long sunUsed = 0;
            long energyUsed = call.getEnergyUsed();
            if (energyUsed != 0) {
                sunUsed = BigInteger.valueOf(energyUsed).multiply(TrxConstant.SUN_PER_ENERGY).longValue();
            }
            return TrxEstimateSun.SUCCESS(sunUsed);
        });
        return estimateEnergy;
    }

    public long getBlockHeight() throws Exception {
        long blockHeight = this.timeOutWrapperFunction("getBlockHeight", null, args -> {
            Chain.Block nowBlock = wrapper.getNowBlock();
            return nowBlock.getBlockHeader().getRawDataOrBuilder().getNumber();
        });
        return blockHeight;
    }


    /**
     * 根据高度获取区块
     */
    public Response.BlockExtention getBlockByHeight(Long height) throws Exception {
        Response.BlockExtention block = this.timeOutWrapperFunction("getBlockByHeight", height, args -> {
            long blockNum = args;
            GrpcAPI.NumberMessage.Builder builder = GrpcAPI.NumberMessage.newBuilder();
            builder.setNum(blockNum);
            return wrapper.blockingStub.getBlockByNum2(builder.build());
        });
        return block;
    }

    /**
     * 获取trx余额
     */
    public BigDecimal getBalance(String address) throws Exception {
        BigDecimal balance = this.timeOutWrapperFunction("getAccountBalance", address, args -> {
            long accountBalance = wrapper.getAccountBalance(args);
            return new BigDecimal(accountBalance);
        });
        return balance;
    }

    public TrxSendTransactionPo callContract(String from, String privateKey, String contractAddress, BigInteger feeLimit, Function function) throws Exception {
        return this.callContract(from, privateKey, contractAddress, feeLimit, function, null);
    }

    public TrxSendTransactionPo callContract(String from, String privateKey, String contractAddress, BigInteger feeLimit, Function function, BigInteger value) throws Exception {
        value = value == null ? BigInteger.ZERO : value;
        String encodedFunction = FunctionEncoder.encode(function);

        TrxSendTransactionPo txPo = this.timeOutWrapperFunction("callContract", ListUtil.of(from, privateKey, contractAddress, feeLimit, encodedFunction, value), args -> {
            int i = 0;
            String _from = args.get(i++).toString();
            String _privateKey = args.get(i++).toString();
            String _contractAddress = args.get(i++).toString();
            BigInteger _feeLimit = (BigInteger) args.get(i++);
            String _encodedFunction = args.get(i++).toString();
            BigInteger _value = (BigInteger) args.get(i++);

            Contract.TriggerSmartContract trigger =
                    Contract.TriggerSmartContract.newBuilder()
                            .setOwnerAddress(parseAddress(_from))
                            .setContractAddress(parseAddress(_contractAddress))
                            .setData(parseHex(_encodedFunction))
                            .setCallValue(_value.longValue())
                            .build();
            Response.TransactionExtention txnExt = wrapper.blockingStub.triggerContract(trigger);
            TransactionBuilder builder = new TransactionBuilder(txnExt.getTransaction());
            builder.setFeeLimit(_feeLimit.longValue());

            Chain.Transaction signedTxn = wrapper.signTransaction(builder.build(), new KeyPair(_privateKey));
            Response.TransactionReturn ret = wrapper.blockingStub.broadcastTransaction(signedTxn);
            if (!ret.getResult()) {
                Log.error("调用合约交易广播失败, 原因: {}", ret.getMessage().toStringUtf8());
                return null;
            }
            return new TrxSendTransactionPo(TrxUtil.calcTxHash(signedTxn), _from, _contractAddress, _value, _encodedFunction, _feeLimit);
        });
        return txPo;
    }


    public void setRpcAddress(String rpcAddress) {
        this.rpcAddress = rpcAddress;
    }

    public ApiWrapper getWrapper() {
        return wrapper;
    }

    public void setWrapper(ApiWrapper wrapper) {
        this.wrapper = wrapper;
    }

    /**
     * 获取ERC-20 token指定地址余额
     *
     * @param address         查询地址
     * @param contractAddress 合约地址
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public BigInteger getERC20Balance(String address, String contractAddress) throws Exception {
        Function function = TrxUtil.getBalanceOfERC20Function(address);
        List<Type> types = callViewFunction(contractAddress, function);
        if (types == null || types.isEmpty()) {
            return BigInteger.ZERO;
        }
        return (BigInteger) types.get(0).getValue();
    }

    public String getERC20Name(String contractAddress) throws Exception {
        Function function = TrxUtil.getNameERC20Function();
        List<Type> types = callViewFunction(contractAddress, function);
        if (types == null || types.isEmpty()) {
            return EMPTY_STRING;
        }
        return (String) types.get(0).getValue();
    }

    public String getERC20Symbol(String contractAddress) throws Exception {
        Function function = TrxUtil.getSymbolERC20Function();
        List<Type> types = callViewFunction(contractAddress, function);
        if (types == null || types.isEmpty()) {
            return EMPTY_STRING;
        }
        return (String) types.get(0).getValue();
    }

    public int getERC20Decimals(String contractAddress) throws Exception {
        Function function = TrxUtil.getDecimalsERC20Function();
        List<Type> types = callViewFunction(contractAddress, function);
        if (types == null || types.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(types.get(0).getValue().toString());
    }

    public TrxSendTransactionPo transferTRC20Token(String from, String to, BigInteger value, String privateKey, String contractAddress) throws Exception {
        return this.transferTRC20Token(from, to, value, privateKey, contractAddress, null);
    }

    public TrxSendTransactionPo transferTRC20Token(String from, String to, BigInteger value, String privateKey, String contractAddress, BigInteger feeLimit) throws Exception {
        feeLimit = feeLimit == null ? TRX_20 : feeLimit;
        //创建RawTransaction交易对象
        Function function = TrxUtil.getTransferERC20Function(to, value);
        TrxSendTransactionPo callContract = this.callContract(from, privateKey, contractAddress, feeLimit, function);
        return callContract;
    }

    public TrxSendTransactionPo transferTrx(String from, String to, BigInteger value, String privateKey) throws Exception {
        TrxSendTransactionPo transferTrx = this.timeOutWrapperFunction("transferTrx", ListUtil.of(from, to, value, privateKey), args -> {
            Response.TransactionExtention txnExt = wrapper.transfer(from, to, value.longValue());

            TransactionBuilder builder = new TransactionBuilder(txnExt.getTransaction());
            builder.setFeeLimit(TRX_2.longValue());

            Chain.Transaction signedTxn = wrapper.signTransaction(builder.build(), new KeyPair(privateKey));
            Response.TransactionReturn ret = wrapper.blockingStub.broadcastTransaction(signedTxn);
            if (!ret.getResult()) {
                Log.error("[{}]转账交易广播失败, 原因: {}", ret.getMessage().toStringUtf8());
                return null;
            }
            return new TrxSendTransactionPo(TrxUtil.calcTxHash(signedTxn), from, to, value, null, TRX_2);
        });
        return transferTrx;
    }
}
