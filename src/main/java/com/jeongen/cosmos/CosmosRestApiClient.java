package com.jeongen.cosmos;

import com.google.protobuf.util.JsonFormat;
import com.jeongen.cosmos.crypro.CosmosCredentials;
import com.jeongen.cosmos.exception.CosmosException;
import com.jeongen.cosmos.tx.*;
import com.jeongen.cosmos.util.ATOMUnitUtil;
import com.jeongen.cosmos.util.JsonToProtoObjectUtil;
import com.jeongen.cosmos.vo.CosmosFunction;
import com.jeongen.cosmos.vo.SendInfo;
import cosmos.auth.v1beta1.QueryOuterClass.QueryAccountResponse;
import cosmos.bank.v1beta1.QueryOuterClass;
import cosmos.base.abci.v1beta1.Abci;
import cosmos.base.tendermint.v1beta1.Query;
import cosmos.tx.v1beta1.ServiceOuterClass;
import cosmos.tx.v1beta1.TxOuterClass;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
public class CosmosRestApiClient {

    private static final JsonFormat.Printer printer = JsonToProtoObjectUtil.getPrinter();

    private GaiaHttpClient client;

    private String apiUrl;

    private List<String> apiUrlList;

    private int index;

    /**
     * API /node_info 的 network 字段
     * 测试网：cosmoshub-testnet
     * 主网：cosmoshub-4
     */
    private final String chainId;

    /**
     * 代币名称
     * 主网：uatom
     * 测试网：stake
     */
    private final String tokenDemon;

    /**
     * @param baseUrl
     * @param chainId
     * @param tokenDemon
     */
    public CosmosRestApiClient(String baseUrl, String chainId, String tokenDemon) {
        this.client = new GaiaHttpClient(baseUrl);
        this.apiUrl = baseUrl;
        this.tokenDemon = tokenDemon;
        this.chainId = chainId;
    }


    public CosmosRestApiClient(List<String> apiUrlList, String chainId, String tokenDemon) {
        this.apiUrlList = apiUrlList;
        this.apiUrl = apiUrlList.get(0);
        this.client = new GaiaHttpClient(this.apiUrl);
        this.tokenDemon = tokenDemon;
        this.chainId = chainId;
    }

    public long getLatestHeight() throws Exception {
        long lastHeight = this.execFunction(null, function -> {
            Query.GetLatestBlockResponse latestBlock = getLatestBlock();
            return latestBlock.getBlock().getHeader().getHeight();
        });
        return lastHeight;
    }

    public Query.GetLatestBlockResponse getLatestBlock() throws Exception {
        Query.GetLatestBlockResponse response = this.execFunction(null, function -> {
            String path = "/cosmos/base/tendermint/v1beta1/blocks/latest";
            return client.get(path, Query.GetLatestBlockResponse.class);
        });
        return response;
    }

    public Query.GetBlockByHeightResponse getBlockByHeight(Long height) throws Exception {
        Query.GetBlockByHeightResponse response = this.execFunction(null, function -> {
            String path = String.format("/cosmos/base/tendermint/v1beta1/blocks/%d", height);
            return client.get(path, Query.GetBlockByHeightResponse.class);
        });
        return response;
    }

    public ServiceOuterClass.GetTxsEventResponse getTxsEventByHeight(Long height, String nextKey) throws Exception {
        ServiceOuterClass.GetTxsEventResponse response = this.execFunction(null, function -> {
            MultiValuedMap<String, String> queryMap = new ArrayListValuedHashMap<>();
            queryMap.put("events", "tx.height=" + height);
            queryMap.put("events", "message.module='bank'");
            queryMap.put("pagination.key", nextKey);
            ServiceOuterClass.GetTxsEventResponse eventResponse = client.get("/cosmos/tx/v1beta1/txs", queryMap, ServiceOuterClass.GetTxsEventResponse.class);
            return eventResponse;
        });
        return response;
    }

    public QueryAccountResponse queryAccount(String address) throws Exception {
        QueryAccountResponse response = this.execFunction(null, function -> {
            String path = String.format("/cosmos/auth/v1beta1/accounts/%s", address);
            return client.get(path, QueryAccountResponse.class);
        });
        return response;
    }

    /**
     * 获取地址本链代币余额
     *
     * @param address
     * @return
     * @throws Exception
     */
    public QueryOuterClass.QueryBalanceResponse getAtomBalance(String address) throws Exception {
        QueryOuterClass.QueryBalanceResponse response = this.execFunction(null, function -> {
            String path = String.format("/cosmos/bank/v1beta1/balances/%s/by_denom", address);
            MultiValuedMap<String, String> queryMap = new ArrayListValuedHashMap<>();
            queryMap.put("denom", tokenDemon);
            return client.get(path, queryMap, QueryOuterClass.QueryBalanceResponse.class);
        });
        return response;
    }

    /**
     * 查询地址其他代币余额
     *
     * @param address
     * @param demonAddress
     * @return
     * @throws Exception
     */
    public QueryOuterClass.QueryBalanceResponse getTokenBalance(String address, String demonAddress) throws Exception {
        QueryOuterClass.QueryBalanceResponse response = this.execFunction(null, function -> {
            String path = String.format("/cosmos/bank/v1beta1/balances/%s/by_denom", address);
            MultiValuedMap<String, String> queryMap = new ArrayListValuedHashMap<>();
            queryMap.put("denom", "ibc/" + demonAddress);
            return this.client.get(path, queryMap, QueryOuterClass.QueryBalanceResponse.class);
        });
        return response;
    }


    /**
     * ------------------------------------   staking 质押相关接口  --------------------------------------
     **/
    public cosmos.staking.v1beta1.QueryOuterClass.QueryParamsResponse queryStakingParams() throws Exception {
        cosmos.staking.v1beta1.QueryOuterClass.QueryParamsResponse response = this.execFunction(null, function -> {
            return client.get("/cosmos/staking/v1beta1/params", cosmos.staking.v1beta1.QueryOuterClass.QueryParamsResponse.class);
        });
        return response;
    }

    /**
     * 查询质押验证节点
     *
     * @throws Exception
     */
    public cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorsResponse queryStakingValidators(MultiValuedMap<String, String> queryMap) throws Exception {
        cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorsResponse response = this.execFunction(null, function -> {
            return client.get("/cosmos/staking/v1beta1/validators", queryMap, cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorsResponse.class);
        });
        return response;
    }

    /**
     * 通过验证人查询节点
     *
     * @param validatorAddress
     * @return
     * @throws Exception
     */
    public cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorResponse queryStakingByValidatorAddress(String validatorAddress) throws Exception {
        String path = "/cosmos/staking/v1beta1/validators/" + validatorAddress;
        cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorResponse response = this.execFunction(null, function -> {
            return client.get(path, cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorResponse.class);
        });
        return response;
    }

    /**
     * 查询用户的所有质押节点信息
     *
     * @param address
     * @return
     * @throws Exception
     */
    public cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorDelegationsResponse queryStakingValidatorsByUser(String address) throws Exception {
        cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorDelegationsResponse response = this.execFunction(null, function -> {
            String path = String.format("/cosmos/staking/v1beta1/delegations/%s", address);
            return client.get(path, cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorDelegationsResponse.class);
        });
        return response;
    }


    public cosmos.staking.v1beta1.QueryOuterClass.QueryDelegationResponse queryDelegation(String address, String validatorAddress) throws Exception {
        cosmos.staking.v1beta1.QueryOuterClass.QueryDelegationResponse response = this.execFunction(null, function -> {
            String path = String.format("/cosmos/staking/v1beta1/validators/%s/delegations/%s", validatorAddress, address);
            return client.get(path, cosmos.staking.v1beta1.QueryOuterClass.QueryDelegationResponse.class);
        });
        return response;
    }
    /**
     * 查询用户已申请解除质押还未解锁记录
     *
     * @param address
     * @param validatorAddress
     * @return
     * @throws Exception
     */
    public cosmos.staking.v1beta1.QueryOuterClass.QueryUnbondingDelegationResponse queryUnbondingDelegation(String address, String validatorAddress) throws Exception {
        cosmos.staking.v1beta1.QueryOuterClass.QueryUnbondingDelegationResponse response = this.execFunction(null, function -> {
            String path = String.format("/cosmos/staking/v1beta1/validators/%s/delegations/%s/unbonding_delegation", validatorAddress, address);
            return client.get(path, cosmos.staking.v1beta1.QueryOuterClass.QueryUnbondingDelegationResponse.class);
        });
        return response;
    }

    /**
     * 查询用户在质押节点的奖励
     *
     * @param address
     * @param validator
     * @return
     * @throws Exception
     */
    public cosmos.distribution.v1beta1.QueryOuterClass.QueryDelegationRewardsResponse queryDelegationReward(String address, String validator) throws Exception {
        cosmos.distribution.v1beta1.QueryOuterClass.QueryDelegationRewardsResponse response = this.execFunction(null, function -> {
            String path = String.format("/cosmos/distribution/v1beta1/delegators/%s/rewards/%s", address, validator);
            return client.get(path, cosmos.distribution.v1beta1.QueryOuterClass.QueryDelegationRewardsResponse.class);
        });
        return response;
    }

    /**
     * ------------------------------------  tx 交易相关接口  --------------------------------------
     **/
    public ServiceOuterClass.GetTxResponse getTx(String hash) throws Exception {
        ServiceOuterClass.GetTxResponse response = this.execFunction(null, function -> {
            String path = String.format("/cosmos/tx/v1beta1/txs/%s", hash);
            return this.client.get(path, ServiceOuterClass.GetTxResponse.class);
        });
        return response;
    }

    /**
     * 查询交易估算执行信息
     *
     * @param tx
     * @return
     * @throws Exception
     */
    public ServiceOuterClass.SimulateResponse simulate(TxOuterClass.Tx tx) throws Exception {
        ServiceOuterClass.SimulateResponse response = this.execFunction(null, function -> {
            ServiceOuterClass.SimulateRequest req = ServiceOuterClass.SimulateRequest.newBuilder().setTx(tx).build();
            String reqBody = printer.print(req);
            ServiceOuterClass.SimulateResponse simulateResponse = client.post("/cosmos/tx/v1beta1/simulate", reqBody, ServiceOuterClass.SimulateResponse.class);
            return simulateResponse;
        });
        return response;
    }

    /**
     * 发送转账交易
     *
     * @param payerCredentials
     * @param sendInfo
     * @param feeInAtom
     * @param gasLimit
     * @return
     * @throws Exception
     */
    public Abci.TxResponse sendTransferTx(ATOMUnitUtil atomUnitUtil, CosmosCredentials payerCredentials,
                                          SendInfo sendInfo, BigDecimal feeInAtom, long gasLimit, String memo) throws Exception {
        Abci.TxResponse response = this.execFunction(null, function -> {
            TxOuterClass.Tx tx = SendTxBuilder.createSendTxRequest(this, atomUnitUtil, payerCredentials, sendInfo, feeInAtom, gasLimit, memo);
            ServiceOuterClass.BroadcastTxRequest broadcastTxRequest = ServiceOuterClass.BroadcastTxRequest.newBuilder()
                    .setTxBytes(tx.toByteString())
                    .setMode(ServiceOuterClass.BroadcastMode.BROADCAST_MODE_SYNC)
                    .build();
            return broadcastTx(broadcastTxRequest, tx);
        });
        return response;
    }

    /**
     * 发送批量转账交易
     *
     * @param payerCredentials 支付手续费的账户
     * @param sendList         转账列表
     * @param feeInAtom        手续费总额
     * @param gasLimit         gas最大可用量（gas用完时，矿工会退出执行，且扣除手续费）
     * @return 交易哈希
     * @throws Exception API 错误
     */
    public Abci.TxResponse sendMultiTransferTx(ATOMUnitUtil atomUnitUtil, CosmosCredentials payerCredentials,
                                               List<SendInfo> sendList, BigDecimal feeInAtom, long gasLimit) throws Exception {
        if (sendList == null || sendList.size() == 0) {
            throw new Exception("sendList is empty");
        }

        Abci.TxResponse response = this.execFunction(null, function -> {
            TxOuterClass.Tx tx = SendMultiTxBuilder.createSendMultiTxRequest(this, atomUnitUtil, payerCredentials, sendList, feeInAtom, gasLimit);
            ServiceOuterClass.BroadcastTxRequest broadcastTxRequest = ServiceOuterClass.BroadcastTxRequest.newBuilder()
                    .setTxBytes(tx.toByteString())
                    .setMode(ServiceOuterClass.BroadcastMode.BROADCAST_MODE_SYNC)
                    .build();
            return broadcastTx(broadcastTxRequest, tx);
        });
        return response;
    }

    /**
     * 发送质押交易
     *
     * @param payerCredentials 支付手续费的账户
     * @param sendInfo         质押信息
     * @param feeInAtom        手续费总额
     * @param gasLimit         gas最大可用量（gas用完时，矿工会退出执行，且扣除手续费）
     * @return 交易哈希
     * @throws Exception API 错误
     */
    public Abci.TxResponse sendDelegateTx(ATOMUnitUtil atomUnitUtil, CosmosCredentials payerCredentials,
                                          SendInfo sendInfo, BigDecimal feeInAtom, long gasLimit) throws Exception {
        Abci.TxResponse response = this.execFunction(null, function -> {
            TxOuterClass.Tx tx = DelegateTxBuilder.createDelegateTxRequest(this, atomUnitUtil, payerCredentials, sendInfo, feeInAtom, gasLimit);
            ServiceOuterClass.BroadcastTxRequest broadcastTxRequest = ServiceOuterClass.BroadcastTxRequest.newBuilder()
                    .setTxBytes(tx.toByteString())
                    .setMode(ServiceOuterClass.BroadcastMode.BROADCAST_MODE_SYNC)
                    .build();
            return broadcastTx(broadcastTxRequest, tx);
        });
        return response;
    }

    /**
     * 发送取消质押交易
     *
     * @param payerCredentials 支付手续费的账户
     * @param sendInfo         质押信息
     * @param feeInAtom        手续费总额
     * @param gasLimit         gas最大可用量（gas用完时，矿工会退出执行，且扣除手续费）
     * @return 交易哈希
     * @throws Exception API 错误
     */
    public Abci.TxResponse sendUnDelegateTx(ATOMUnitUtil atomUnitUtil, CosmosCredentials payerCredentials,
                                            SendInfo sendInfo, BigDecimal feeInAtom, long gasLimit) throws Exception {
        Abci.TxResponse response = this.execFunction(null, function -> {
            TxOuterClass.Tx tx = UnDelegateTxBuilder.createUnDelegateTxRequest(this, atomUnitUtil, payerCredentials, sendInfo, feeInAtom, gasLimit);
            ServiceOuterClass.BroadcastTxRequest broadcastTxRequest = ServiceOuterClass.BroadcastTxRequest.newBuilder()
                    .setTxBytes(tx.toByteString())
                    .setMode(ServiceOuterClass.BroadcastMode.BROADCAST_MODE_SYNC)
                    .build();
            return broadcastTx(broadcastTxRequest, tx);
        });
        return response;
    }

    /**
     * 发送获取质押奖励交易
     *
     * @param payerCredentials
     * @param validator
     * @param feeInAtom
     * @param gasLimit
     * @return
     * @throws Exception
     */
    public Abci.TxResponse sendWithdrawRewardTx(ATOMUnitUtil atomUnitUtil, CosmosCredentials payerCredentials,
                                                String validator, BigDecimal feeInAtom, long gasLimit) throws Exception {
        Abci.TxResponse response = this.execFunction(null, function -> {
            TxOuterClass.Tx tx = WithdrawRewardTxBuilder.createWithdrawRewardTxRequest(this, atomUnitUtil, payerCredentials, validator, feeInAtom, gasLimit);
            ServiceOuterClass.BroadcastTxRequest broadcastTxRequest = ServiceOuterClass.BroadcastTxRequest.newBuilder()
                    .setTxBytes(tx.toByteString())
                    .setMode(ServiceOuterClass.BroadcastMode.BROADCAST_MODE_SYNC)
                    .build();
            return broadcastTx(broadcastTxRequest, tx);
        });
        return response;
    }

    /**
     * 广播交易
     *
     * @param req
     * @return
     * @throws Exception
     */
    public Abci.TxResponse broadcastTx(ServiceOuterClass.BroadcastTxRequest req, TxOuterClass.Tx tx) throws Exception {
        String reqBody = printer.print(req);
        ServiceOuterClass.BroadcastTxResponse broadcastTxResponse = client.post("/cosmos/tx/v1beta1/txs", reqBody, ServiceOuterClass.BroadcastTxResponse.class);

        if (!broadcastTxResponse.hasTxResponse()) {
            throw new Exception("broadcastTxResponse no body\n" + printer.print(tx));
        }
        Abci.TxResponse txResponse = broadcastTxResponse.getTxResponse();
        if (txResponse.getCode() != 0 || !StringUtil.isNullOrEmpty(txResponse.getCodespace())) {
            throw new Exception("BroadcastTx error:" + txResponse.getCodespace() + "," + txResponse.getCode() + "," + txResponse.getRawLog() + "\n" + printer.print(tx));
        }
        if (txResponse.getTxhash().length() != 64) {
            throw new Exception("Txhash illegal\n" + printer.print(tx));
        }
        return txResponse;
    }

    public Abci.TxResponse broadcastTx(ServiceOuterClass.BroadcastTxRequest req) throws Exception {

        String reqBody = printer.print(req);
        ServiceOuterClass.BroadcastTxResponse broadcastTxResponse = client.post("/cosmos/tx/v1beta1/txs", reqBody, ServiceOuterClass.BroadcastTxResponse.class);
        if (!broadcastTxResponse.hasTxResponse()) {
            throw new Exception("broadcastTxResponse no body");
        }
        Abci.TxResponse txResponse = broadcastTxResponse.getTxResponse();
        if (txResponse.getCode() != 0 || !StringUtil.isNullOrEmpty(txResponse.getCodespace())) {
            throw new Exception("BroadcastTx error:" + txResponse.getCodespace() + "," + txResponse.getCode() + "," + txResponse.getRawLog());
        }
        if (txResponse.getTxhash().length() != 64) {
            throw new Exception("Txhash illegal");
        }
        return txResponse;
    }



    public <T, R> R execFunction(T arg, CosmosFunction<T, R> function) throws Exception {
        return execFunctionReal(arg, function, 0);
    }

    private <T, R> R execFunctionReal(T arg, CosmosFunction<T, R> function, int times) throws Exception {
        try {
            return function.apply(arg);
        } catch (Exception e) {
            if (e instanceof CosmosException) {
                throw e;
            }
            if (times < 3) {
                reset();
                times++;
                return execFunctionReal(arg, function, times);
            }
            log.error("-----eth PRC error---", e.getMessage());
            throw e;
        }
    }

    private <T, R> R execFunctionOnce(T arg, CosmosFunction<T, R> function) throws Exception {
        return function.apply(arg);
    }

    public void changeUrl(String apiUrl) {
        if (apiUrlList == null || apiUrlList.size() <= 1) {
            return;
        }
        for (int i = 0; i < apiUrlList.size(); i++) {
            if (apiUrlList.get(i).equals(apiUrl)) {
                this.index = i;
                this.apiUrl = apiUrl;
                this.client = new GaiaHttpClient(apiUrl);
                break;
            }
        }
    }

    public void reset() {
        if (apiUrlList == null || apiUrlList.size() <= 1) {
            return;
        }
        index++;
        if (index >= apiUrlList.size()) {
            index = 0;
        }
        apiUrl = apiUrlList.get(index);
        this.client = new GaiaHttpClient(apiUrl);
    }

    public void setApiUrlList(List<String> apiUrlList) {
        this.apiUrlList = apiUrlList;
    }

    public String getChainId() {
        return this.chainId;
    }

    public String getTokenDemon() {
        return this.tokenDemon;
    }

    public String getApiUrl() {
        return this.apiUrl;
    }

    public GaiaHttpClient getClient() {
        return this.client;
    }
}
