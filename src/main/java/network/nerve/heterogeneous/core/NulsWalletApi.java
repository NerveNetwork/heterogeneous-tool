package network.nerve.heterogeneous.core;

import network.nerve.base.basic.AddressTool;
import network.nerve.base.basic.NulsByteBuffer;
import network.nerve.base.data.Block;
import network.nerve.base.data.Transaction;
import network.nerve.base.signture.P2PHKSignature;
import network.nerve.base.signture.TransactionSignature;
import network.nerve.core.basic.Result;
import network.nerve.core.constant.CommonCodeConstanst;
import network.nerve.core.constant.ErrorCode;
import network.nerve.core.crypto.ECKey;
import network.nerve.core.crypto.HexUtil;
import network.nerve.core.exception.NulsRuntimeException;
import network.nerve.core.model.StringUtils;
import network.nerve.heterogeneous.model.*;
import network.nerve.heterogeneous.utils.*;
import network.nerve.kit.error.AccountErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static network.nerve.base.signture.SignatureUtil.createSignaturesByEckey;
import static network.nerve.core.basic.Result.getFailed;
import static network.nerve.core.basic.Result.getSuccess;
import static network.nerve.core.constant.CommonCodeConstanst.NULL_PARAMETER;
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

    public NulsWalletApi(String symbol, String chainName, String rpcAddress, int chainId, int decimals, long gasPrice, String addressPrefix) {
        this.symbol = symbol;
        this.chainName = chainName;
        this.addressPrefix = addressPrefix;
        this.chainId = chainId;
        this.decimals = decimals;
        this.gasPrice = gasPrice;
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
                                             String methodDesc, Object[] args, String[] argsType, BigInteger value) throws Exception {
        return callContract(fromAddress, contract, gasLimit, method, methodDesc, args, argsType, value, null, null);
    }

    public HtgSendTransactionPo callContract(String fromAddress, String contract, BigInteger gasLimit, String method, String methodDesc,
                                             Object[] args, String[] argsType, BigInteger value, BigInteger senderBalance, String nonce) throws Exception {
        if (senderBalance == null || StringUtils.isBlank(nonce)) {
            String[] latestNonce = this.getLatestNonce(fromAddress);
            senderBalance = new BigInteger(latestNonce[0]);
            nonce = latestNonce[1];
        }
        Result<Map> nerveMultiSign = callContractTxOffline(fromAddress, senderBalance, nonce, value, contract, gasLimit.longValue(), method, methodDesc, args, argsType, "Nerve Multi Sign");
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


    public HtgSendTransactionPo callContract(String fromAddress, String priKey, String contract, BigInteger gasLimit,
                                             String method, String methodDesc, Object[] args, String[] argsType, BigInteger value, BigInteger senderBalance, String nonce) throws Exception {
        if (senderBalance == null || StringUtils.isBlank(nonce)) {
            String[] latestNonce = this.getLatestNonce(fromAddress);
            senderBalance = new BigInteger(latestNonce[0]);
            nonce = latestNonce[1];
        }
        Result<Map> nerveMultiSign = callContractTxOffline(fromAddress, senderBalance, nonce, value, contract, gasLimit.longValue(), method, methodDesc, args, argsType, "Nerve Multi Sign");
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
                throw new NulsRuntimeException(AccountErrorCode.PARAMETER_ERROR, "txHex is invalid");
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
        return _callContractTxOffline(sender, senderBalance, nonce, value, contractAddress, gasLimit, methodName, methodDesc,
                args, argsType, System.currentTimeMillis() / 1000, remark, null, null);
    }

    public Result<Map> _callContractTxOffline(String sender, BigInteger senderBalance, String nonce, BigInteger value, String contractAddress,
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
                return getFailed(CommonCodeConstanst.PARAMETER_ERROR).setMsg("size of 'argsType' array not match 'args' array");
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
        Transaction tx = NulsContractUtil.newCallTx(chainId, assetId, senderBalance, nonce, callContractData, time, remark, multyAssetValues, nulsValueToOthers);
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
