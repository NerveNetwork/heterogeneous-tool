package network.nerve.heterogeneous.core;

import network.nerve.base.basic.AddressTool;
import network.nerve.base.basic.NulsByteBuffer;
import network.nerve.base.basic.TransactionFeeCalculator;
import network.nerve.base.data.*;
import network.nerve.base.data.Block;
import network.nerve.base.data.Transaction;
import network.nerve.base.signture.P2PHKSignature;
import network.nerve.base.signture.TransactionSignature;
import network.nerve.core.basic.Result;
import network.nerve.core.constant.CommonCodeConstanst;
import network.nerve.core.constant.ErrorCode;
import network.nerve.core.constant.TxType;
import network.nerve.core.crypto.ECKey;
import network.nerve.core.exception.NulsException;
import network.nerve.core.exception.NulsRuntimeException;
import network.nerve.core.model.BigIntegerUtils;
import network.nerve.core.rpc.util.NulsDateUtils;
import network.nerve.heterogeneous.model.*;
import network.nerve.heterogeneous.utils.*;
import network.nerve.kit.constant.AccountConstant;
import network.nerve.kit.error.AccountErrorCode;
import network.nerve.kit.model.dto.CoinFromDto;
import network.nerve.kit.model.dto.CoinToDto;
import network.nerve.kit.model.dto.TransferDto;
import network.nerve.kit.model.dto.TransferTxFeeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static network.nerve.base.signture.SignatureUtil.createSignaturesByEckey;
import static network.nerve.core.basic.Result.getFailed;
import static network.nerve.core.basic.Result.getSuccess;
import static network.nerve.core.constant.CommonCodeConstanst.NULL_PARAMETER;
import static network.nerve.core.constant.CommonCodeConstanst.PARAMETER_ERROR;
import static network.nerve.heterogeneous.utils.NulsContractUtil.getNormalTxFee;
import static network.nerve.heterogeneous.utils.NulsContractUtil.getNormalUnsignedTxFee;
import static network.nerve.kit.error.AccountErrorCode.ADDRESS_ERROR;

/**
 * 区块接口服务
 * Created by wangkun23 on 2018/9/5.
 */
public class NulsWalletApi {

    private static Logger Log = LoggerFactory.getLogger(NulsWalletApi.class.getName());

    protected String rpcAddress;
    protected String jsonrpcAddress;
    private String symbol;
    private String chainName;
    private String addressPrefix;
    private int chainId = -1;
    private int decimals;

    private long gasPrice;
    private BigInteger nulsNormalTxFeePrice;

    public int chainId() {
        return chainId;
    }

    public int decimals() {
        return decimals;
    }

    public String symbol() {
        return symbol;
    }

    private Logger log() {
        return Log;
    }

    public NulsWalletApi(String symbol, String chainName, String rpcAddress, int chainId, int decimals, long gasPrice, String addressPrefix, long nulsNormalTxFeePrice) {
        this.symbol = symbol;
        this.chainName = chainName;
        this.addressPrefix = addressPrefix;
        this.chainId = chainId;
        this.decimals = decimals;
        this.gasPrice = gasPrice;
        this.nulsNormalTxFeePrice = BigInteger.valueOf(nulsNormalTxFeePrice);
        init(rpcAddress);
    }

    public String addressPrefix() {
        return addressPrefix;
    }

    public void init(String rpcAddress) {
        log().info("initialization {} API URL: {}", symbol(), rpcAddress);
        if (rpcAddress != null && !rpcAddress.endsWith("/")) {
            rpcAddress += "/";
        }
        this.rpcAddress = rpcAddress;
        this.jsonrpcAddress = rpcAddress + "jsonrpc";
    }

    /**
     * 获取最新区块头
     *
     * @return
     */
    public SimpleBlockHeader getNewestBlockHeader() throws Exception {
        RpcResult rpcResult = NulsApiUtil.jsonRpcRequest(jsonrpcAddress, "getBestBlockHeader", ListUtil.of(chainId()));
        if (rpcResult == null) {
            log().error("empty block about getting newest block!!!");
            throw new Exception("empty Block");
        }
        if (rpcResult.getError() != null) {
            log().error("error block about getting newest block !!! - {[]}", rpcResult.getError().toString());
            throw new Exception(rpcResult.getError().toString());
        }
        Map result = (Map) rpcResult.getResult();
        SimpleBlockHeader header = new SimpleBlockHeader();
        header.setHash((String) result.get("hash"));
        header.setPreHash((String) result.get("preHash"));
        header.setHeight(Long.parseLong(result.get("height").toString()));
        return header;
    }

    public SimpleBlockHeader getBlockHeaderByHeight(Long height) throws Exception {
        RpcResult rpcResult = NulsApiUtil.jsonRpcRequest(jsonrpcAddress, "getHeaderByHeight", ListUtil.of(chainId(), height));
        if (rpcResult == null) {
            log().error("empty block about getting newest block!!!");
            throw new Exception("empty Block");
        }
        if (rpcResult.getError() != null) {
            log().error("error block about getting newest block !!! - {[]}", rpcResult.getError().toString());
            throw new Exception(rpcResult.getError().toString());
        }
        Map result = (Map) rpcResult.getResult();
        SimpleBlockHeader header = new SimpleBlockHeader();
        header.setHash((String) result.get("hash"));
        header.setPreHash((String) result.get("preHash"));
        header.setHeight(Long.parseLong(result.get("height").toString()));
        header.setBlockTime(Long.parseLong(result.get("timestamp").toString()));
        return header;
    }

    /**
     * 获取最新高度
     *
     * @return
     */
    public Long getNewestBlockHeight() throws Exception {
        RpcResult rpcResult = NulsApiUtil.jsonRpcRequest(jsonrpcAddress, "getLatestHeight", ListUtil.of(chainId()));
        if (rpcResult == null) {
            log().error("empty block about getting newest block height[0]!!!");
            throw new Exception("empty Block height[0]");
        }
        if (rpcResult.getError() != null) {
            log().error("error block about getting newest block height!!! - {[]}", rpcResult.getError().toString());
            throw new Exception(rpcResult.getError().toString());
        }
        Object result = rpcResult.getResult();
        if (result == null) {
            log().error("empty block about getting newest block height[1]!!!");
            throw new Exception("empty Block height[1]");
        }
        Long height = Long.parseLong(result.toString());
        return height;
    }

    /**
     * 根据高度获取区块
     *
     * @return
     */
    public Block getBlockByHeight(Long height) throws Exception {
        RpcResult rpcResult = NulsApiUtil.jsonRpcRequest(jsonrpcAddress, "getBlockSerializationByHeight", ListUtil.of(chainId(), height));
        if (rpcResult == null) {
            log().error("empty block about getting block by height!!!");
            throw new Exception("empty Block");
        }
        if (rpcResult.getError() != null) {
            log().error("error block about getting block by height!!! - {[]}", rpcResult.getError().toString());
            throw new Exception(rpcResult.getError().toString());
        }
        String blockHex = (String) rpcResult.getResult();
        Block block = new Block();
        block.parse(new NulsByteBuffer(HexUtil.decode(blockHex)));
        return block;
    }

    /**
     * 根据hash获取区块
     *
     * @return
     */
    public Block getBlockByHash(String hash) throws Exception {
        RpcResult rpcResult = NulsApiUtil.jsonRpcRequest(jsonrpcAddress, "getBlockSerializationByHash", ListUtil.of(chainId(), hash));
        String blockHex = (String) rpcResult.getResult();
        Block block = new Block();
        block.parse(new NulsByteBuffer(HexUtil.decode(blockHex)));
        return block;
    }

    public RpcResult<Map<String, Map>> getContractTxResultList(List<String> hashList) throws InterruptedException {
        RpcResult<Map<String, Map>> rpcResult = NulsApiUtil.jsonRpcRequest(jsonrpcAddress, "getContractTxResultList", ListUtil.of(chainId(), hashList));
        return rpcResult;
    }

    public ContractResultSimpleDto getContractTxResult(String hash) throws InterruptedException {
        RpcResult<Map> rpcResult = NulsApiUtil.jsonRpcRequest(jsonrpcAddress, "getContractTxResult", ListUtil.of(chainId(), hash));
        if (rpcResult.getError() != null) {
            throw new RuntimeException(rpcResult.getError().toString());
        }
        return JSONUtils.map2pojo(rpcResult.getResult(), ContractResultSimpleDto.class);
    }

    public NulsTransaction getTransactionByHash(String txHash) throws Exception {
        RpcResult rpcResult = NulsApiUtil.jsonRpcRequest(jsonrpcAddress, "getTxSerialization", ListUtil.of(chainId(), txHash));
        if (rpcResult.getError() != null) {
            throw new RuntimeException(rpcResult.getError().toString());
        }
        Map res = (Map) rpcResult.getResult();
        String txStr = (String) res.get("tx");
        Long height = Long.parseLong(res.get("height").toString());
        Transaction tx = new Transaction();
        tx.parse(HexUtil.decode(txStr), 0);
        return new NulsTransaction(tx, height);
    }

    public BigDecimal getBalance(String address) throws Exception {
        BigDecimal balance = BigDecimal.ZERO;
        Map info = getAccountBalanceInfo(address);
        if (info != null) {
            balance = new BigDecimal(info.get("totalBalance").toString());
        }
        return balance;
    }

    public RpcResult estimateGas(String fromAddress, BigInteger value, String contract, String method, String methodDesc, Object[] args) throws Exception {
        int chainId = chainId();
        RpcResult<Map> rpcResult = NulsApiUtil.jsonRpcRequest(jsonrpcAddress, "imputedContractCallGas", ListUtil.of(chainId, fromAddress, value, contract, method, methodDesc, args));
        return rpcResult;
    }

    private Map getAccountBalanceInfo(String address) throws Exception {
        int chainId = chainId();
        int assetId = 1;
        RpcResult<Map> result = NulsApiUtil.jsonRpcRequest(jsonrpcAddress, "getAccountBalance", ListUtil.of(chainId, chainId, assetId, address), 5);
        if (result == null) {
            log().error("Post time out 6 times!!! address is {}", address);
            return null;
        }
        if (result.getError() != null) {
            log().error("getAccountBalance error, address is {}, error is {}", address, result.getError().toString());
            return null;
        }
        return result.getResult();
    }

    private BigInteger calcTransferTxFee(TransferTxFeeDto dto) {
        if (dto.getPrice() == null) {
            dto.setPrice(nulsNormalTxFeePrice);
        }
        return NulsContractUtil.calcTransferTxFee(dto.getAddressCount(), dto.getFromLength(), dto.getToLength(), dto.getRemark(), dto.getPrice());
    }

    public Result<Map> tokenTransferTxOffline(String fromAddress, BigInteger senderBalance, String nonce, String toAddress, String contractAddress, long gasLimit, BigInteger amount, long time, String remark) {
        if (amount == null || amount.compareTo(BigInteger.ZERO) <= 0) {
            return Result.getFailed(PARAMETER_ERROR).setMsg(String.format("amount [%s] is invalid", amount));
        }

        if (!AddressTool.validAddress(chainId, fromAddress)) {
            return Result.getFailed(ADDRESS_ERROR).setMsg(String.format("fromAddress [%s] is invalid", fromAddress));
        }

        if (!AddressTool.validAddress(chainId, toAddress)) {
            return Result.getFailed(ADDRESS_ERROR).setMsg(String.format("toAddress [%s] is invalid", toAddress));
        }

        if (!AddressTool.validAddress(chainId, contractAddress)) {
            return Result.getFailed(ADDRESS_ERROR).setMsg(String.format("contractAddress [%s] is invalid", contractAddress));
        }
        return this.callContractTxOffline(fromAddress, senderBalance, nonce, null, contractAddress, gasLimit, NulsContractUtil.NRC20_METHOD_TRANSFER, null,
                new Object[]{toAddress, amount.toString()}, new String[]{"String", "BigInteger"}, time, remark, null, null);
    }

    public Result<Map> token721TransferTxOffline(String fromAddress, BigInteger senderBalance, String nonce,
                                                  String contractAddress, String toAddress, BigInteger tokenId,
                                                  long gasLimit, long time, String remark) {

        if (!AddressTool.validAddress(chainId, fromAddress)) {
            return Result.getFailed(ADDRESS_ERROR).setMsg(String.format("fromAddress [%s] is invalid", fromAddress));
        }

        if (!AddressTool.validAddress(chainId, toAddress)) {
            return Result.getFailed(ADDRESS_ERROR).setMsg(String.format("toAddress [%s] is invalid", toAddress));
        }

        if (!AddressTool.validAddress(chainId, contractAddress)) {
            return Result.getFailed(ADDRESS_ERROR).setMsg(String.format("contractAddress [%s] is invalid", contractAddress));
        }
        return this.callContractTxOffline(fromAddress, senderBalance, nonce, null, contractAddress, gasLimit, NulsContractUtil.NRC721_METHOD_TRANSFER, null,
                new Object[]{fromAddress, toAddress, tokenId.toString(), "blank data"}, new String[]{"Address", "Address", "BigInteger", "String"}, time, remark, null, null);
    }

    public Result<Map> token1155TransferTxOffline(String fromAddress, BigInteger senderBalance, String nonce,
                                                  String contractAddress, String toAddress, BigInteger tokenId, BigInteger amount,
                                                  long gasLimit, long time, String remark) {
        if (amount == null || amount.compareTo(BigInteger.ZERO) <= 0) {
            return Result.getFailed(PARAMETER_ERROR).setMsg(String.format("amount [%s] is invalid", amount));
        }

        if (!AddressTool.validAddress(chainId, fromAddress)) {
            return Result.getFailed(ADDRESS_ERROR).setMsg(String.format("fromAddress [%s] is invalid", fromAddress));
        }

        if (!AddressTool.validAddress(chainId, toAddress)) {
            return Result.getFailed(ADDRESS_ERROR).setMsg(String.format("toAddress [%s] is invalid", toAddress));
        }

        if (!AddressTool.validAddress(chainId, contractAddress)) {
            return Result.getFailed(ADDRESS_ERROR).setMsg(String.format("contractAddress [%s] is invalid", contractAddress));
        }
        return this.callContractTxOffline(fromAddress, senderBalance, nonce, null, contractAddress, gasLimit, NulsContractUtil.NRC1155_METHOD_TRANSFER, null,
                new Object[]{fromAddress, toAddress, tokenId.toString(), amount.toString(), "blank data"}, new String[]{"Address", "Address", "BigInteger", "BigInteger", "String"}, time, remark, null, null);
    }

    public Result createTxSimpleTransferOfNuls(String fromAddress, String toAddress, BigInteger amount, long time, String remark) throws Exception {
        Map balance = getAccountBalanceInfo(fromAddress);
        BigInteger senderBalance = new BigInteger(balance.get("available").toString());

        TransferTxFeeDto feeDto = new TransferTxFeeDto();
        feeDto.setAddressCount(1);
        feeDto.setFromLength(2);
        feeDto.setToLength(1);
        feeDto.setRemark(remark);
        BigInteger feeNeed = calcTransferTxFee(feeDto);
        BigInteger amountTotal = amount.add(feeNeed);
        if (senderBalance.compareTo(amountTotal) < 0) {
            return Result.getFailed(AccountErrorCode.INSUFFICIENT_BALANCE);
        }
        String nonce = balance.get("nonce").toString();

        TransferDto transferDto = new TransferDto();
        List<CoinFromDto> inputs = new ArrayList<>();

        //转账资产
        CoinFromDto from = new CoinFromDto();
        from.setAddress(fromAddress);
        from.setAmount(amountTotal);
        from.setAssetChainId(chainId);
        from.setAssetId(1);
        from.setNonce(nonce);
        inputs.add(from);

        List<CoinToDto> outputs = new ArrayList<>();
        CoinToDto to = new CoinToDto();
        to.setAddress(toAddress);
        to.setAmount(amount);
        to.setAssetChainId(chainId);
        to.setAssetId(1);
        outputs.add(to);

        transferDto.setInputs(inputs);
        transferDto.setOutputs(outputs);
        transferDto.setTime(time);
        transferDto.setRemark(remark);
        return createTransferTx(transferDto);
    }

    private Result createTransferTx(TransferDto transferDto) {
        try {

            for (CoinFromDto fromDto : transferDto.getInputs()) {
                if (fromDto.getAssetChainId() == 0) {
                    fromDto.setAssetChainId(chainId);
                }
                if (fromDto.getAssetId() == 0) {
                    fromDto.setAssetId(1);
                }
            }
            for (CoinToDto toDto : transferDto.getOutputs()) {
                if (toDto.getAssetChainId() == 0) {
                    toDto.setAssetChainId(chainId);
                }
                if (toDto.getAssetId() == 0) {
                    toDto.setAssetId(1);
                }
            }

            Transaction tx = new Transaction(TxType.TRANSFER);
            if (transferDto.getTime() != 0) {
                tx.setTime(transferDto.getTime());
            } else {
                tx.setTime(NulsDateUtils.getCurrentTimeSeconds());
            }
            tx.setRemark(StringUtils.bytes(transferDto.getRemark()));

            CoinData coinData = assemblyCoinData(transferDto.getInputs(), transferDto.getOutputs(), tx.getSize());
            tx.setCoinData(coinData.serialize());
            tx.setHash(NulsHash.calcHash(tx.serializeForHash()));

            Map<String, Object> map = new HashMap<>();
            map.put("hash", tx.getHash().toHex());
            map.put("txHex", HexUtil.encode(tx.serialize()));
            return Result.getSuccess(map);
        } catch (NulsException e) {
            return Result.getFailed(e.getErrorCode()).setMsg(e.format());
        } catch (IOException e) {
            return Result.getFailed(AccountErrorCode.DATA_PARSE_ERROR).setMsg(AccountErrorCode.DATA_PARSE_ERROR.getMsg());
        }
    }

    private CoinData assemblyCoinData(List<CoinFromDto> inputs, List<CoinToDto> outputs, int txSize) throws NulsException {
        List<CoinFrom> coinFroms = new ArrayList<>();
        for (CoinFromDto from : inputs) {
            byte[] address = AddressTool.getAddress(from.getAddress());
            byte[] nonce = HexUtil.decode(from.getNonce());
            CoinFrom coinFrom = new CoinFrom(address, from.getAssetChainId(), from.getAssetId(), from.getAmount(), nonce, AccountConstant.NORMAL_TX_LOCKED);
            coinFroms.add(coinFrom);
        }

        List<CoinTo> coinTos = new ArrayList<>();
        for (CoinToDto to : outputs) {
            byte[] addressByte = AddressTool.getAddress(to.getAddress());
            CoinTo coinTo = new CoinTo(addressByte, to.getAssetChainId(), to.getAssetId(), to.getAmount(), to.getLockTime());
            coinTos.add(coinTo);
        }

        txSize = txSize + getSignatureSize(coinFroms);
        calcTxFee(coinFroms, coinTos, txSize);
        CoinData coinData = new CoinData();
        coinData.setFrom(coinFroms);
        coinData.setTo(coinTos);
        return coinData;
    }

    private int getSignatureSize(List<CoinFrom> coinFroms) {
        int size = 0;
        Set<String> commonAddress = new HashSet<>();
        for (CoinFrom coinFrom : coinFroms) {
            String address = AddressTool.getStringAddressByBytes(coinFrom.getAddress());
            commonAddress.add(address);
        }
        size += commonAddress.size() * P2PHKSignature.SERIALIZE_LENGTH;
        return size;
    }

    public boolean isMainAsset(int chainId, int assetId) {
        return chainId == this.chainId && assetId == 1;
    }

    public void calcTxFee(List<CoinFrom> coinFroms, List<CoinTo> coinTos, int txSize) throws NulsException {
        BigInteger totalFrom = BigInteger.ZERO;
        for (CoinFrom coinFrom : coinFroms) {
            txSize += coinFrom.size();
            if (isMainAsset(coinFrom.getAssetsChainId(), coinFrom.getAssetsId())) {
                totalFrom = totalFrom.add(coinFrom.getAmount());
            }
        }
        BigInteger totalTo = BigInteger.ZERO;
        for (CoinTo coinTo : coinTos) {
            txSize += coinTo.size();
            if (isMainAsset(coinTo.getAssetsChainId(), coinTo.getAssetsId())) {
                totalTo = totalTo.add(coinTo.getAmount());
            }
        }
        //本交易预计收取的手续费
        BigInteger targetFee = getNormalTxFee(decimals, txSize);
        //实际收取的手续费, 可能自己已经组装完成
        BigInteger actualFee = totalFrom.subtract(totalTo);
        if (BigIntegerUtils.isLessThan(actualFee, BigInteger.ZERO)) {
            throw new NulsException(AccountErrorCode.INSUFFICIENT_FEE);
        } else if (BigIntegerUtils.isLessThan(actualFee, targetFee)) {
            throw new NulsException(AccountErrorCode.INSUFFICIENT_FEE);
        }
    }

    public RpcResult callViewFunction(String contract, String method, Object[] args) throws Exception {
        ContractViewCallForm form = new ContractViewCallForm();
        form.setContractAddress(contract);
        form.setMethodName(method);
        form.setArgs(args);

        RpcResult rpcResult = NulsApiUtil.invokeViewJsonrpc(chainId(), jsonrpcAddress, form);
        return rpcResult;
    }

    public String[] getLatestNonce(String address) throws Exception {
        Map info = getAccountBalanceInfo(address);
        String available = info.get("balance").toString();
        String nonce = info.get("nonce").toString();
        return new String[]{available, nonce};
    }

    public HtgSendTransactionPo callContract(String fromAddress, String contract, BigInteger gasLimit, String method,
                                             String methodDesc, Object[] args, String[] argsType, BigInteger value, String remark) throws Exception {
        return callContract(fromAddress, contract, gasLimit, method, methodDesc, args, argsType, value, remark, null, null);
    }

    public HtgSendTransactionPo callContract(String fromAddress, String contract, BigInteger gasLimit, String method, String methodDesc,
                                             Object[] args, String[] argsType, BigInteger value, String remark, BigInteger senderBalance, String nonce) throws Exception {
        if (senderBalance == null || StringUtils.isBlank(nonce)) {
            String[] latestNonce = this.getLatestNonce(fromAddress);
            senderBalance = new BigInteger(latestNonce[0]);
            nonce = latestNonce[1];
        }
        Result<Map> nerveMultiSign = callContractTxOffline(fromAddress, senderBalance, nonce, value, contract, gasLimit.longValue(), method, methodDesc, args, argsType, remark);
        if (nerveMultiSign.isFailed()) {
            log().error("make contract tx error - [{}]", nerveMultiSign.toString());
            return null;
        }

        Map txMap = nerveMultiSign.getData();
        String txHex = (String) txMap.get("txHex");
        String hash = (String) txMap.get("txHash");
        HtgSendTransactionPo po = new HtgSendTransactionPo(
                hash,
                fromAddress,
                NulsContractUtil.hexToBigInteger(nonce),
                BigInteger.valueOf(0),
                BigInteger.valueOf(0),
                contract,
                value,
                txHex
        );
        return po;
    }


    public HtgSendTransactionPo callContract(String fromAddress, String priKey, String contract, BigInteger gasLimit, String method, String methodDesc,
                                             Object[] args, String[] argsType, BigInteger value, String remark, BigInteger senderBalance, String nonce) throws Exception {
        if (senderBalance == null || StringUtils.isBlank(nonce)) {
            String[] latestNonce = this.getLatestNonce(fromAddress);
            senderBalance = new BigInteger(latestNonce[0]);
            nonce = latestNonce[1];
        }
        Result<Map> nerveMultiSign = callContractTxOffline(fromAddress, senderBalance, nonce, value, contract, gasLimit.longValue(), method, methodDesc, args, argsType, remark);
        if (nerveMultiSign.isFailed()) {
            log().error("make contract tx error - [{}]", nerveMultiSign.toString());
            return null;
        }
        Map txMap = nerveMultiSign.getData();
        String txHex = (String) txMap.get("txHex");

        String signedTxHex;
        // 签名交易
        signedTxHex = HexUtil.encode(this.nulsTxSign(priKey, HexUtil.decode(txHex)));

        // 在线接口 - 广播交易
        Result<Map> broadcaseTxR = broadcastTx(signedTxHex);
        if (broadcaseTxR.isFailed()) {
            log().error("broadcast contract tx error, signedTxHex: {} \n[{}]", signedTxHex, JSONUtils.obj2PrettyJson(broadcaseTxR));
            return null;
        }
        Map data = broadcaseTxR.getData();
        String hash = (String) data.get("hash");
        Transaction tx = new Transaction();
        tx.parse(HexUtil.decode(signedTxHex), 0);
        CallContractData txData = new CallContractData();
        txData.parse(tx.getTxData(), 0);

        HtgSendTransactionPo po = new HtgSendTransactionPo(
                hash,
                fromAddress,
                NulsContractUtil.hexToBigInteger(nonce),
                BigInteger.valueOf(txData.getPrice()),
                BigInteger.valueOf(txData.getGasLimit()),
                contract,
                value,
                HexUtil.encode(tx.getTxData())
        );
        return po;
    }

    public byte[] nulsTxSign(String priKey, byte[] bytes) throws Exception {
        try {
            if (bytes == null) {
                throw new NulsRuntimeException(PARAMETER_ERROR, "txHex is invalid");
            }

            List<ECKey> signEcKeys = new ArrayList<>();
            signEcKeys.add(ECKey.fromPrivate(HexUtil.decode(priKey)));

            Transaction tx = new Transaction();
            tx.parse(new NulsByteBuffer(bytes));

            TransactionSignature transactionSignature = new TransactionSignature();
            List<P2PHKSignature> p2PHKSignatures = createSignaturesByEckey(tx, signEcKeys);
            transactionSignature.setP2PHKSignatures(p2PHKSignatures);
            tx.setTransactionSignature(transactionSignature.serialize());

            return tx.serialize();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Result broadcastTx(String txHex) throws InterruptedException {
        RpcResult<Map> balanceResult = NulsApiUtil.jsonRpcRequest(jsonrpcAddress, "broadcastTx", ListUtil.of(chainId(), txHex));
        RpcResultError rpcResultError = balanceResult.getError();
        if (rpcResultError != null) {
            return getFailed(ErrorCode.init(rpcResultError.getCode())).setMsg(rpcResultError.getMessage());
        }
        Map result = balanceResult.getResult();
        return getSuccess(result);
    }

    public Result<Map> callContractTxOffline(String sender, BigInteger senderBalance, String nonce, BigInteger value, String contractAddress, long gasLimit,
                                             String methodName, String methodDesc, Object[] args, String[] argsType, String remark) {
        return callContractTxOffline(sender, senderBalance, nonce, value, contractAddress, gasLimit, methodName, methodDesc,
                args, argsType, System.currentTimeMillis() / 1000, remark, null, null);
    }

    public Result<Map> callContractTxOffline(String sender, BigInteger senderBalance, String nonce, BigInteger value, String contractAddress,
                                              long gasLimit, String methodName, String methodDesc, Object[] args, String[] argsType,
                                              long time, String remark, List<ProgramMultyAssetValue> multyAssetValues, List<AccountAmountDto> nulsValueToOthers) {
        int chainId = chainId();
        if (!AddressTool.validAddress(chainId, sender)) {
            return getFailed(ADDRESS_ERROR).setMsg(String.format("sender [%s] is invalid", sender));
        }

        if (!AddressTool.validAddress(chainId, contractAddress)) {
            return getFailed(ADDRESS_ERROR).setMsg(String.format("contractAddress [%s] is invalid", contractAddress));
        }

        if (StringUtils.isBlank(methodName)) {
            return getFailed(NULL_PARAMETER).setMsg("methodName is empty");
        }
        if (value == null) {
            value = BigInteger.ZERO;
        }

        int assetId = 1;
        // 生成参数的二维数组
        String[][] finalArgs = null;
        if (args != null && args.length > 0) {
            if (argsType == null || argsType.length != args.length) {
                return getFailed(PARAMETER_ERROR).setMsg("size of 'argsType' array not match 'args' array");
            }
            finalArgs = NulsContractUtil.twoDimensionalArray(args, argsType);
        }

        // 组装交易的txData
        byte[] contractAddressBytes = AddressTool.getAddress(contractAddress);
        byte[] senderBytes = AddressTool.getAddress(sender);
        CallContractData callContractData = new CallContractData();
        callContractData.setContractAddress(contractAddressBytes);
        callContractData.setSender(senderBytes);
        callContractData.setValue(value);
        callContractData.setPrice(gasPrice);
        callContractData.setGasLimit(gasLimit);
        callContractData.setMethodName(methodName);
        callContractData.setMethodDesc(methodDesc);
        if (finalArgs != null) {
            callContractData.setArgsCount((short) finalArgs.length);
            callContractData.setArgs(finalArgs);
        }

        // 生成交易
        Transaction tx = NulsContractUtil.newCallTx(decimals, chainId, assetId, senderBalance, nonce, callContractData, time, remark, multyAssetValues, nulsValueToOthers);
        try {
            Map<String, Object> resultMap = new HashMap<>(4);
            resultMap.put("hash", tx.getHash().toHex());
            resultMap.put("txHex", HexUtil.encode(tx.serialize()));
            return getSuccess(resultMap);
        } catch (IOException e) {
            return getFailed(CommonCodeConstanst.FAILED).setMsg(e.getMessage());
        }
    }
}
