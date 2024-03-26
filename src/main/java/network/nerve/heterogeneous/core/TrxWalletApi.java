package network.nerve.heterogeneous.core;

import com.google.protobuf.ByteString;
import network.nerve.heterogeneous.HtgTool;
import network.nerve.heterogeneous.constant.TrxConstant;
import network.nerve.heterogeneous.model.*;
import network.nerve.heterogeneous.utils.ListUtil;
import network.nerve.heterogeneous.utils.RpcResult;
import network.nerve.heterogeneous.utils.StringUtils;
import network.nerve.heterogeneous.utils.TrxUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tron.trident.abi.FunctionEncoder;
import org.tron.trident.abi.FunctionReturnDecoder;
import org.tron.trident.abi.TypeReference;
import org.tron.trident.abi.datatypes.Address;
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
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.EthSendTransaction;

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
public class TrxWalletApi implements Api {

    private static Logger Log = LoggerFactory.getLogger(TrxWalletApi.class.getName());

    private ApiWrapper wrapper;
    private String rpcAddress;
    private String symbol;
    private String chainName;
    private String tempKey = "3333333333333333333333333333333333333333333333333333333333333333";
    private BigInteger energyPrice = SUN_PER_ENERGY;

    public static TrxWalletApi getInstance(String rpcAddress) {
        return new TrxWalletApi(rpcAddress);
    }

    public void setEnergyPrice(BigInteger energyPrice) {
        this.energyPrice = energyPrice;
    }

    private TrxWalletApi(String rpcAddress) {
        this.symbol = "TRX";
        this.chainName = "TRON";
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


    private void changeApi(String rpc) {
        resetApiWrapper();
        init(rpc);
    }

    private void resetApiWrapper() {
        if (wrapper != null) {
            wrapper.close();
            wrapper = null;
        }
    }

    private void checkIfResetApiWrapper(int times) throws Exception {
        int mod = times % 6;
        if (mod == 5 && wrapper != null && rpcAddress != null) {
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
            changeApi(this.rpcAddress);
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
        String encodedFunction = FunctionEncoder.encode(function);
        String result = this.callViewFunction(contractAddress, encodedFunction);
        if (StringUtils.isBlank(result)) {
            return null;
        }
        return FunctionReturnDecoder.decode(result, function.getOutputParameters());
    }

    public String callViewFunction(String contractAddress, String functionStr) throws Exception {
        String result = this.timeOutWrapperFunction("callViewFunction", ListUtil.of(contractAddress, functionStr), args -> {
            String _contractAddress = (String) args.get(0);
            String _function = (String) args.get(1);
            org.tron.trident.core.contract.Contract cntr = wrapper.getContract(_contractAddress);
            cntr.setOwnerAddr(parseAddress(TrxConstant.ZERO_ADDRESS_TRX));
            String encodedHex = _function;
            // Make a TriggerSmartContract contract
            Contract.TriggerSmartContract trigger =
                    Contract.TriggerSmartContract.newBuilder()
                            .setOwnerAddress(cntr.getOwnerAddr())
                            .setContractAddress(cntr.getCntrAddr())
                            .setData(parseHex(encodedHex))
                            .build();
            Response.TransactionExtention call = wrapper.blockingStub.triggerConstantContract(trigger);
            //Response.TransactionExtention call = wrapper.constantCall(TrxConstant.ZERO_ADDRESS_TRX, _contractAddress, _function);
            if (call.getConstantResultCount() == 0) {
                return null;
            }
            byte[] bytes = call.getConstantResult(0).toByteArray();
            return Numeric.toHexString(bytes);
        });
        return result;
    }


    public TrxEstimateSun estimateSunUsed(String from, String contractAddress, Function function) throws Exception {
        return this.estimateSunUsed(from, contractAddress, function, null);
    }

    public TrxEstimateSun estimateSunUsed(String from, String contractAddress, Function function, BigInteger value) throws Exception {
        String functionStr = FunctionEncoder.encode(function);
        return this.estimateSunUsed(from, contractAddress, functionStr, value);
    }

    public TrxEstimateSun estimateSunUsed(String from, String contractAddress, String functionStr, BigInteger value) throws Exception {
        value = value == null ? BigInteger.ZERO : value;
        TrxEstimateSun estimateEnergy = this.timeOutWrapperFunction("estimateEnergyUsed", ListUtil.of(from, contractAddress, functionStr, value), args -> {
            BigInteger _value = (BigInteger) args.get(3);
            String encodedHex = functionStr;
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
                sunUsed = BigInteger.valueOf(energyUsed).multiply(energyPrice).longValue();
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
    @Override
    public BigInteger getBalance(String address) throws Exception {
        BigInteger balance = this.timeOutWrapperFunction("getAccountBalance", address, args -> {
            long accountBalance = wrapper.getAccountBalance(args);
            return BigInteger.valueOf(accountBalance);
        });
        return balance;
    }

    /**
     * 查询只能合约信息
     *
     * @param address
     * @return
     * @throws Exception
     */
    public org.tron.trident.core.contract.Contract getContract(String address) {
        try {
            org.tron.trident.core.contract.Contract contract = this.timeOutWrapperFunction("getContract", address, args -> {
                return wrapper.getContract(args);
            });
            return contract;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public TrxSendTransactionPo callContract(String from, String privateKey, String contractAddress, BigInteger feeLimit, Function function) throws Exception {
        return this.callContract(from, privateKey, contractAddress, feeLimit, function, null);
    }

    public TrxSendTransactionPo callContract(String from, String privateKey, String contractAddress, BigInteger feeLimit, Function function, BigInteger value) throws Exception {
        String encodedFunction = FunctionEncoder.encode(function);
        return this.callContract(from, privateKey, contractAddress, feeLimit, encodedFunction, value);
    }

    public TrxSendTransactionPo callContract(String from, String privateKey, String contractAddress, BigInteger feeLimit, String functionStr, BigInteger value) throws Exception {
        value = value == null ? BigInteger.ZERO : value;
        TrxSendTransactionPo txPo = this.timeOutWrapperFunction("callContract", ListUtil.of(from, privateKey, contractAddress, feeLimit, functionStr, value), args -> {
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
                throw new BusinessRuntimeException(ret.getMessage().toStringUtf8());
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
    @Override
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
        feeLimit = feeLimit == null ? TRX_30 : feeLimit;
        //创建RawTransaction交易对象
        Function function = TrxUtil.getTransferERC20Function(to, value);
        TrxSendTransactionPo callContract = this.callContract(from, privateKey, contractAddress, feeLimit, function);
        return callContract;
    }

    public TrxSendTransactionPo transferTrx(String from, String to, BigInteger value, String privateKey) throws Exception {
        return this.transferTrx(from, to, value, privateKey, null);
    }

    public TrxSendTransactionPo transferTrx(String from, String to, BigInteger value, String privateKey, BigInteger feeLimit) throws Exception {
        if (from.equalsIgnoreCase(to)) {
            throw new BusinessRuntimeException("Cannot transfer TRX to the same account");
        }
        if (feeLimit == null) {
            feeLimit = TRX_3;
        }
        TrxSendTransactionPo transferTrx = this.timeOutWrapperFunction("transferTrx", ListUtil.of(from, to, value, privateKey, feeLimit), args -> {
            Response.TransactionExtention txnExt = wrapper.transfer(from, to, value.longValue());

            TransactionBuilder builder = new TransactionBuilder(txnExt.getTransaction());
            BigInteger _feeLimit = (BigInteger) args.get(4);
            builder.setFeeLimit(_feeLimit.longValue());

            Chain.Transaction signedTxn = wrapper.signTransaction(builder.build(), new KeyPair(privateKey));
            Response.TransactionReturn ret = wrapper.blockingStub.broadcastTransaction(signedTxn);
            if (!ret.getResult()) {
                throw new BusinessRuntimeException(ret.getMessage().toStringUtf8());
            }
            return new TrxSendTransactionPo(TrxUtil.calcTxHash(signedTxn), from, to, value, null, TRX_3);
        });
        return transferTrx;
    }

    @Override
    public EthSendTransactionPo createSendMainAsset(String fromAddress, String privateKey, String toAddress, BigDecimal value, BigInteger gasLimit, BigInteger gasPrice) throws Exception {
        TrxSendTransactionPo transferTrx = this.transferTrx(fromAddress, toAddress, TrxUtil.convertTrxToSun(value), privateKey, gasLimit);
        if (transferTrx == null) {
            return null;
        }
        EthSendTransactionPo po = new EthSendTransactionPo(
                transferTrx.getTxHash(),
                transferTrx.getFrom(),
                BigInteger.ZERO,
                BigInteger.ONE,
                transferTrx.getFeeLimit(),
                transferTrx.getTo(),
                transferTrx.getValue(),
                transferTrx.getData(),
                transferTrx.getTxHash());
        return po;
    }

    @Override
    public EthSendTransactionPo createTransferERC20Token(String from, String to, BigInteger value, String privateKey, String contractAddress, BigInteger gasLimit, BigInteger gasPrice) throws Exception {
        TrxSendTransactionPo transferTRC20Token = this.transferTRC20Token(from, to, value, privateKey, contractAddress, gasLimit);
        if (transferTRC20Token == null) {
            return null;
        }
        EthSendTransactionPo po = new EthSendTransactionPo(
                transferTRC20Token.getTxHash(),
                transferTRC20Token.getFrom(),
                BigInteger.ZERO,
                BigInteger.ONE,
                transferTRC20Token.getFeeLimit(),
                transferTRC20Token.getTo(),
                transferTRC20Token.getValue(),
                transferTRC20Token.getData(),
                transferTRC20Token.getTxHash());
        return po;
    }

    @Override
    public EthSendTransactionPo createTransferERC721Token(String contractAddress, String from, String to, BigInteger tokenId, String data, String privateKey, BigInteger gasLimit, BigInteger gasPrice) throws Exception {
        throw new RuntimeException("Do not support it on the TRON.");
    }

    @Override
    public EthSendTransactionPo createTransferERC1155Token(String contractAddress, String from, String to, List<Uint256> tokenIdList, List<Uint256> values, String data, String privateKey, BigInteger gasLimit, BigInteger gasPrice) throws Exception {
        throw new RuntimeException("Do not support it on the TRON.");
    }

    @Override
    public EthSendTransactionPo createRechargeMainAssetWithGas(String fromAddress, String prikey, BigInteger value, String toAddress, String multySignContractAddress, BigInteger gasLimit, BigInteger gasPrice) throws Exception {
        throw new RuntimeException("Do not support it on the TRON.");
    }

    @Override
    public EthSendTransactionPo createRechargeErc20WithGas(String fromAddress, String prikey, BigInteger value, String toAddress, String multySignContractAddress, String erc20ContractAddress, BigInteger gasLimit, BigInteger gasPrice) throws Exception {
        throw new RuntimeException("Do not support it on the TRON.");
    }

    @Override
    public BigInteger estimateGasForTransferMainAsset() throws Exception {
        return TRX_3;
    }

    @Override
    public BigInteger estimateGasWithNetworkForTransferMainAsset(String from, String to, BigInteger value) throws Exception {
        return TRX_3;
    }

    @Override
    public BigInteger estimateGasForTransferERC20(String from, String to, BigInteger value, String contractAddress) throws Exception {
        Function function = TrxUtil.getTransferERC20Function(to, value);
        TrxEstimateSun trxEstimateSun = this.estimateSunUsed(from, contractAddress, function);
        if (trxEstimateSun.isReverted()) {
            throw new BusinessRuntimeException(trxEstimateSun.getRevertReason());
        }
        return BigInteger.valueOf(trxEstimateSun.getSunUsed());
    }

    @Override
    public BigInteger estimateGasForRechargeMainAsset() throws Exception {
        throw new RuntimeException("Do not support it on the TRON.");
    }

    @Override
    public BigInteger estimateGasForTransferERC721(String contractAddress, String from, String to, BigInteger tokenId, String data) throws Exception {
        throw new RuntimeException("Do not support it on the TRON.");
    }

    @Override
    public BigInteger estimateGasForTransferERC1155(String contractAddress, String from, String to, List<Uint256> tokenIdList, List<Uint256> values, String data) throws Exception {
        throw new RuntimeException("Do not support it on the TRON.");
    }

    @Override
    public BigInteger estimateGasForRechargeERC20(String fromAddress, String toAddress, BigInteger value, String multySignContractAddress, String erc20ContractAddress) throws Exception {
        throw new RuntimeException("Do not support it on the TRON.");
    }

    @Override
    public boolean isAuthorized(String fromAddress, String multySignContractAddress, String erc20Address) throws Exception {
        throw new RuntimeException("Do not support it on the TRON.");
    }

    @Override
    public BigInteger getERC1155Balance(String address, String contractAddress, BigInteger tokenId) throws Exception {
        throw new RuntimeException("Do not support it on the TRON.");
    }

    @Override
    public TokenInfo getTokenInfo(String contractAddress) throws Exception {
        String name = this.getERC20Name(contractAddress);
        String symbol = this.getERC20Symbol(contractAddress);
        int decimals = this.getERC20Decimals(contractAddress);
        return new TokenInfo(name, symbol, decimals);
    }

    @Override
    public EthSendTransaction sendRawTransaction(String txHexValue) throws Exception {
        throw new RuntimeException("Do not support it on the TRON.");
    }

    @Override
    public MultiCallResult multiCall(String multiCallAddress, List<MultiCallModel> multiCallModelList) throws Exception {
        org.web3j.abi.datatypes.Function aggregateFunction = createAggregateFunction(multiCallModelList);
        String encodeFunctionData = org.web3j.abi.FunctionEncoder.encode(aggregateFunction);
        String resultCall = this.callViewFunction(multiCallAddress, encodeFunctionData);
        MultiCallResult result = new MultiCallResult();
        if (StringUtils.isBlank(resultCall)) {
            CallError callError = new CallError(3, "Execute failed: empty result.");
            result.setCallError(callError);
            return result;
        }
        if (TrxUtil.isErrorInResult(resultCall)) {
            CallError callError = new CallError(3, TrxUtil.getRevertReason(resultCall));
            result.setCallError(callError);
            return result;
        }
        List<org.web3j.abi.datatypes.Type> multiType = org.web3j.abi.FunctionReturnDecoder.decode(resultCall, aggregateFunction.getOutputParameters());
        //获取当前区块高度
        Uint256 uint256 = (Uint256) multiType.get(0);
        long blockHeight = uint256.getValue().longValue();
        org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.DynamicBytes> dynamicArray = (DynamicArray<org.web3j.abi.datatypes.DynamicBytes>) multiType.get(1);
        List<DynamicBytes> dynamicBytesList = dynamicArray.getValue();
        List<List<org.web3j.abi.datatypes.Type>> multiResultList = HtgTool.processMultiCallResult(dynamicBytesList, multiCallModelList);
        result.setBlockHeight(blockHeight);
        result.setMultiResultList(multiResultList);
        return result;
    }

    @Override
    public MultiCallResult tryMultiCall(String multiCallAddress, List<MultiCallModel> multiCallModelList) throws Exception {
        throw new RuntimeException("Do not support it on the TRON.");
    }

    @Override
    public org.web3j.abi.datatypes.Function createAggregateFunction(List<MultiCallModel> multiCallModelList) {
        return HtgTool.createAggregateFunction(multiCallModelList);
    }

    @Override
    public EthSendTransactionPo sendRawTransaction(String privateKey, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to, BigInteger value, String data) throws Exception {
        TrxSendTransactionPo po;
        String from = new KeyPair(privateKey).toBase58CheckAddress();
        if (StringUtils.isNotBlank(data)) {
            po = this.callContract(from, privateKey, to, gasLimit, data, value);
        } else {
            po = this.transferTrx(from, to, value, privateKey, gasLimit);
        }
        EthSendTransactionPo result = new EthSendTransactionPo(
                po.getTxHash(),
                po.getFrom(),
                BigInteger.ZERO,
                BigInteger.ONE,
                po.getFeeLimit(),
                po.getTo(),
                po.getValue(),
                po.getData(),
                po.getTxHash());
        return result;
    }

    @Override
    public String sendRawTransactionWithoutBroadcast(String privateKey, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to, BigInteger value, String data) throws Exception {
        TrxSendTransactionPo po;
        String from = new KeyPair(privateKey).toBase58CheckAddress();
        if (StringUtils.isNotBlank(data)) {
            po = this.callContract(from, privateKey, to, gasLimit, data, value);
        } else {
            po = this.transferTrx(from, to, value, privateKey, gasLimit);
        }
        return po.getTxHash();
    }

    @Override
    public EthCall validateRawTransaction(String from, String to, String data, BigInteger value) throws Exception {
        throw new RuntimeException("Do not support it on the TRON.");
    }

    @Override
    public EthCall ethCall(String from, String to, BigInteger gasLimit, BigInteger gasPrice, BigInteger value, String data, boolean latest) throws Exception {
        throw new RuntimeException("Do not support it on the TRON.");
    }

    @Override
    public EthEstimateGas ethEstimateGas(String from, String to, BigInteger gasLimit, BigInteger gasPrice, BigInteger value, String data) throws Exception {
        TrxEstimateSun trxEstimateSun = this.estimateSunUsed(from, to, data, value);
        EthEstimateGas result = new EthEstimateGas();
        result.setJsonrpc("2.0");
        result.setId(1);
        if (!trxEstimateSun.isReverted()) {
            result.setResult(Numeric.prependHexPrefix(BigInteger.valueOf(trxEstimateSun.getSunUsed()).toString(16)));
        } else {
            org.web3j.protocol.core.Response.Error error = new org.web3j.protocol.core.Response.Error();
            error.setCode(3);
            error.setMessage(trxEstimateSun.getRevertReason());
            error.setData(EMPTY_STRING);
            result.setError(error);
        }
        return result;
    }

    @Override
    public RpcResult request(String requestURL, String method, List<Object> params) {
        throw new RuntimeException("Do not support it on the TRON.");
    }
}
