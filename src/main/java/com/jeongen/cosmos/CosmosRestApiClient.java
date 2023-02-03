package com.jeongen.cosmos;

import com.google.protobuf.util.JsonFormat;
import com.jeongen.cosmos.crypro.CosmosCredentials;
import com.jeongen.cosmos.tx.*;
import com.jeongen.cosmos.util.JsonToProtoObjectUtil;
import com.jeongen.cosmos.vo.SendInfo;
import cosmos.auth.v1beta1.QueryOuterClass.QueryAccountResponse;
import cosmos.bank.v1beta1.QueryOuterClass;
import cosmos.base.abci.v1beta1.Abci;
import cosmos.base.tendermint.v1beta1.Query;
import cosmos.tx.v1beta1.ServiceOuterClass;
import cosmos.tx.v1beta1.TxOuterClass;
import io.netty.util.internal.StringUtil;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

public class CosmosRestApiClient {

    private static final Logger logger = LoggerFactory.getLogger(CosmosRestApiClient.class);

    private static final JsonFormat.Printer printer = JsonToProtoObjectUtil.getPrinter();

    private GaiaHttpClient client;

    /**
     * API /node_info 的 network 字段
     * 测试网：cosmoshub-testnet
     * 主网：cosmoshub-4
     */
    private String chainId;

    /**
     * 代币名称
     * 主网：uatom
     * 测试网：stake
     */
    private String tokenDemon;

    /**
     * @param baseUrl
     * @param chainId
     * @param tokenDemon
     */
    public CosmosRestApiClient(String baseUrl, String chainId, String tokenDemon) {
        this.client = new GaiaHttpClient(baseUrl);
        this.tokenDemon = tokenDemon;
        this.chainId = chainId;
    }

    /**
     *
     * @param baseUrl
     * @param tokenDemon
     * @throws Exception
     */
    public CosmosRestApiClient(String baseUrl, String tokenDemon) throws Exception {
        this.client = new GaiaHttpClient(baseUrl);
        this.tokenDemon = tokenDemon;
        Query.GetNodeInfoResponse response = getInfo();
        this.chainId = response.getDefaultNodeInfo().getNetwork();
    }

    public Query.GetNodeInfoResponse getInfo() throws Exception {
        String path = "/cosmos/base/tendermint/v1beta1/node_info";
        return client.get(path, Query.GetNodeInfoResponse.class);
    }

    public long getLatestHeight() throws Exception {
        Query.GetLatestBlockResponse latestBlock = getLatestBlock();
        return latestBlock.getBlock().getHeader().getHeight();

    }

    public Query.GetLatestBlockResponse getLatestBlock() throws Exception {
        String path = "/cosmos/base/tendermint/v1beta1/blocks/latest";
        return client.get(path, Query.GetLatestBlockResponse.class);
    }

    public Query.GetBlockByHeightResponse getBlockByHeight(Long height) throws Exception {
        String path = String.format("/cosmos/base/tendermint/v1beta1/blocks/%d", height);
        return client.get(path, Query.GetBlockByHeightResponse.class);
    }

    public ServiceOuterClass.GetTxsEventResponse getTxsEventByHeight(Long height, String nextKey) throws Exception {
        MultiValuedMap<String, String> queryMap = new ArrayListValuedHashMap<>();
        queryMap.put("events", "tx.height=" + height);
        queryMap.put("events", "message.module='bank'");
        queryMap.put("pagination.key", nextKey);
        ServiceOuterClass.GetTxsEventResponse eventResponse = client.get("/cosmos/tx/v1beta1/txs", queryMap, ServiceOuterClass.GetTxsEventResponse.class);
        return eventResponse;
    }

    public QueryAccountResponse queryAccount(String address) throws Exception {
        String path = String.format("/cosmos/auth/v1beta1/accounts/%s", address);
        return client.get(path, QueryAccountResponse.class);
    }

    /**
     * 获取地址本链代币余额
     *
     * @param address
     * @return
     * @throws Exception
     */
    public QueryOuterClass.QueryBalanceResponse getAtomBalance(String address) throws Exception {
        String path = String.format("/cosmos/bank/v1beta1/balances/%s/by_denom", address);
        MultiValuedMap<String, String> queryMap = new ArrayListValuedHashMap<>();
        queryMap.put("denom", tokenDemon);
        return client.get(path, queryMap, QueryOuterClass.QueryBalanceResponse.class);
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
        String path = String.format("/cosmos/bank/v1beta1/balances/%s/by_denom", address);
        MultiValuedMap<String, String> queryMap = new ArrayListValuedHashMap<>();
        queryMap.put("denom", "ibc/" + demonAddress);
        return this.client.get(path, queryMap, QueryOuterClass.QueryBalanceResponse.class);
    }


    /**------------------------------------   staking 质押相关接口  --------------------------------------**/

    /**
     * 查询所有质押验证节点
     *
     * @throws Exception
     */
    public cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorsResponse queryAllStakingValidators(MultiValuedMap<String, String> queryMap) throws Exception {
        cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorsResponse response = client.get("/cosmos/staking/v1beta1/validators", queryMap, cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorsResponse.class);
        return response;
    }

    /**
     * 根据状态查询节点
     *
     * @return
     * @throws Exception
     */
    public cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorsResponse queryStakingValidatorsByStatus(MultiValuedMap<String, String> queryMap) throws Exception {
        cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorsResponse response = client.get("/cosmos/staking/v1beta1/validators", queryMap, cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorsResponse.class);
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
        String path = String.format("/cosmos/staking/v1beta1/delegations/%s", address);
        cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorDelegationsResponse response = client.get(path, cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorDelegationsResponse.class);
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
        String path = String.format("/cosmos/distribution/v1beta1/delegators/%s/rewards/%s", address, validator);
        cosmos.distribution.v1beta1.QueryOuterClass.QueryDelegationRewardsResponse response = client.get(path, cosmos.distribution.v1beta1.QueryOuterClass.QueryDelegationRewardsResponse.class);
        return response;
    }


    /**
     * ------------------------------------  tx 交易相关接口  --------------------------------------
     **/

    public ServiceOuterClass.GetTxResponse getTx(String hash) throws Exception {
        String path = String.format("/cosmos/tx/v1beta1/txs/%s", hash);
        return this.client.get(path, ServiceOuterClass.GetTxResponse.class);
    }

    /**
     * 查询交易估算执行信息
     *
     * @param tx
     * @return
     * @throws Exception
     */
    public ServiceOuterClass.SimulateResponse simulate(TxOuterClass.Tx tx) throws Exception {
        ServiceOuterClass.SimulateRequest req = ServiceOuterClass.SimulateRequest.newBuilder().setTx(tx).build();
        String reqBody = printer.print(req);
        ServiceOuterClass.SimulateResponse simulateResponse = client.post("/cosmos/tx/v1beta1/simulate", reqBody, ServiceOuterClass.SimulateResponse.class);
        return simulateResponse;
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
    public Abci.TxResponse sendTransferTx(CosmosCredentials payerCredentials, SendInfo sendInfo, BigDecimal feeInAtom, long gasLimit, String memo) throws Exception {
        TxOuterClass.Tx tx = SendTxBuilder.createSendTxRequest(this, payerCredentials, sendInfo, feeInAtom, gasLimit, memo);
        ServiceOuterClass.BroadcastTxRequest broadcastTxRequest = ServiceOuterClass.BroadcastTxRequest.newBuilder()
                .setTxBytes(tx.toByteString())
                .setMode(ServiceOuterClass.BroadcastMode.BROADCAST_MODE_SYNC)
                .build();

        return broadcastTx(broadcastTxRequest, tx);
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
    public Abci.TxResponse sendMultiTransferTx(CosmosCredentials payerCredentials, List<SendInfo> sendList, BigDecimal feeInAtom, long gasLimit) throws Exception {
        if (sendList == null || sendList.size() == 0) {
            throw new Exception("sendList is empty");
        }

        TxOuterClass.Tx tx = SendMultiTxBuilder.createSendMultiTxRequest(this, payerCredentials, sendList, feeInAtom, gasLimit);

        ServiceOuterClass.BroadcastTxRequest broadcastTxRequest = ServiceOuterClass.BroadcastTxRequest.newBuilder()
                .setTxBytes(tx.toByteString())
                .setMode(ServiceOuterClass.BroadcastMode.BROADCAST_MODE_SYNC)
                .build();

        return broadcastTx(broadcastTxRequest, tx);
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
    public Abci.TxResponse sendDelegateTx(CosmosCredentials payerCredentials, SendInfo sendInfo, BigDecimal feeInAtom, long gasLimit) throws Exception {
        TxOuterClass.Tx tx = DelegateTxBuilder.createDelegateTxRequest(this, payerCredentials, sendInfo, feeInAtom, gasLimit);

        ServiceOuterClass.BroadcastTxRequest broadcastTxRequest = ServiceOuterClass.BroadcastTxRequest.newBuilder()
                .setTxBytes(tx.toByteString())
                .setMode(ServiceOuterClass.BroadcastMode.BROADCAST_MODE_SYNC)
                .build();
        return broadcastTx(broadcastTxRequest, tx);
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
    public Abci.TxResponse sendUnDelegateTx(CosmosCredentials payerCredentials, SendInfo sendInfo, BigDecimal feeInAtom, long gasLimit) throws Exception {
        TxOuterClass.Tx tx = UnDelegateTxBuilder.createUnDelegateTxRequest(this, payerCredentials, sendInfo, feeInAtom, gasLimit);

        ServiceOuterClass.BroadcastTxRequest broadcastTxRequest = ServiceOuterClass.BroadcastTxRequest.newBuilder()
                .setTxBytes(tx.toByteString())
                .setMode(ServiceOuterClass.BroadcastMode.BROADCAST_MODE_SYNC)
                .build();

        return broadcastTx(broadcastTxRequest, tx);
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
    public Abci.TxResponse sendWithdrawRewardTx(CosmosCredentials payerCredentials, String validator, BigDecimal feeInAtom, long gasLimit) throws Exception {
        TxOuterClass.Tx tx = WithdrawRewardTxBuilder.createWithdrawRewardTxRequest(this, payerCredentials, validator, feeInAtom, gasLimit);
        ServiceOuterClass.BroadcastTxRequest broadcastTxRequest = ServiceOuterClass.BroadcastTxRequest.newBuilder()
                .setTxBytes(tx.toByteString())
                .setMode(ServiceOuterClass.BroadcastMode.BROADCAST_MODE_SYNC)
                .build();

        return broadcastTx(broadcastTxRequest, tx);
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


    public String getChainId() {
        return chainId;
    }

    public String getTokenDemon() {
        return tokenDemon;
    }
}
