package com.jeongen.cosmos;

import com.jeongen.cosmos.config.CosmosChainConfig;
import com.jeongen.cosmos.crypro.CosmosCredentials;
import com.jeongen.cosmos.tx.*;
import com.jeongen.cosmos.util.ATOMUnitUtil;
import com.jeongen.cosmos.util.CosmosAddressUtil;
import com.jeongen.cosmos.vo.SendInfo;
import cosmos.auth.v1beta1.Auth;
import cosmos.base.abci.v1beta1.Abci;
import cosmos.base.tendermint.v1beta1.Query;
import cosmos.staking.v1beta1.QueryOuterClass;
import cosmos.staking.v1beta1.Staking;
import cosmos.tx.v1beta1.ServiceOuterClass;
import cosmos.tx.v1beta1.TxOuterClass;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.bouncycastle.util.encoders.Hex;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class CosmosWalletApi {

    /**
     * 远程restApi工具
     */
    private CosmosRestApiClient apiClient;

    /**
     * cosmos地址工具
     */
    private CosmosAddressUtil addressUtil;

    public CosmosWalletApi(String baseUrl, String chainId, String token, String prefix) {
        this.apiClient = new CosmosRestApiClient(baseUrl, chainId, token);
        this.addressUtil = new CosmosAddressUtil(prefix);
    }

    public CosmosWalletApi(String baseUrl, String token, String prefix) throws Exception {
        this.apiClient = new CosmosRestApiClient(baseUrl, token);
        this.addressUtil = new CosmosAddressUtil(prefix);
    }

    public CosmosWalletApi(CosmosChainConfig chainConfig) {
        this(chainConfig.getUrl(), chainConfig.getChainId(), chainConfig.getTokenDemon(), chainConfig.getPrefix());
    }

    /**---------------------   基础信息-----------------------**/

    /**
     * 获取最新区块高度
     *
     * @return
     * @throws Exception
     */
    public long getLatestHeight() throws Exception {
        return apiClient.getLatestHeight();
    }

    /**
     * 获取链基础信息
     *
     * @throws Exception
     */
    public Query.GetNodeInfoResponse getInfo() throws Exception {
        return apiClient.getInfo();
    }

    /**----------------------     账户相关-----------------------**/

    /**
     * 通过私钥得到地址
     *
     * @param priKey
     * @return
     */
    public String getAddress(String priKey) {
        if (priKey.startsWith("0x")) {
            priKey = priKey.substring(2);
        }
        byte[] privateKey = Hex.decode(priKey);
        CosmosCredentials credential = CosmosCredentials.create(privateKey, addressUtil);
        return credential.getAddress();
    }

    /**
     * 通过公钥得到地址
     *
     * @param pubKey
     * @return
     */
    public String getAddressByPubKey(String pubKey) {
        byte[] bytes = Hex.decode(pubKey);
        return addressUtil.publicKeyToAddress(bytes);
    }

    /**
     * 验证地址
     *
     * @param address
     * @return
     */
    public boolean validateAddress(String address) {
        return addressUtil.verifyCosmosAddress(address);
    }

    /**
     * 查询普通账户信息
     *
     * @param address
     * @param cacheMap
     * @return
     * @throws Exception
     */
    public Auth.BaseAccount queryBaseAccount(String address, Map<String, Auth.BaseAccount> cacheMap) throws Exception {
        if (cacheMap.containsKey(address)) {
            return cacheMap.get(address);
        }
        Auth.BaseAccount baseAccount = queryBaseAccount(address);
        cacheMap.put(address, baseAccount);
        return baseAccount;
    }

    /**
     * 查询普通账户信息
     *
     * @param address
     * @return
     * @throws Exception
     */
    public Auth.BaseAccount queryBaseAccount(String address) throws Exception {
        cosmos.auth.v1beta1.QueryOuterClass.QueryAccountResponse res = apiClient.queryAccount(address);
        if (res.hasAccount() && res.getAccount().is(Auth.BaseAccount.class)) {
            return res.getAccount().unpack(Auth.BaseAccount.class);
        }
        throw new RuntimeException("account not found:" + address);
    }

    /**
     * 获取地址代币余额
     *
     * @param address
     * @return
     * @throws Exception
     */
    public BigDecimal getAtomBalance(String address) throws Exception {
        cosmos.bank.v1beta1.QueryOuterClass.QueryBalanceResponse balanceResponse = apiClient.getAtomBalance(address);
        if (balanceResponse.hasBalance()) {
            String amount = balanceResponse.getBalance().getAmount();
            return ATOMUnitUtil.microAtomToAtom(amount);
        } else {
            return BigDecimal.ZERO;
        }
    }

    /**
     * 查询其他链代币余额
     *
     * @param address
     * @param demonAddress
     * @return
     * @throws Exception
     */
    public BigDecimal getTokenBalance(String address, String demonAddress) throws Exception {
        cosmos.bank.v1beta1.QueryOuterClass.QueryBalanceResponse balanceResponse = apiClient.getTokenBalance(address, demonAddress);
        if (balanceResponse.hasBalance()) {
            String amount = balanceResponse.getBalance().getAmount();
            return ATOMUnitUtil.microAtomToAtom(amount);
        } else {
            return BigDecimal.ZERO;
        }
    }

    /**------------------------------------   staking 质押相关接口  --------------------------------------**/

    /**
     * 查询所有质押验证节点
     *
     * @throws Exception
     */
    public QueryOuterClass.QueryValidatorsResponse queryAllStakingValidators() throws Exception {
        MultiValuedMap<String, String> queryMap = new ArrayListValuedHashMap<>();
        queryMap.put("status", "BOND_STATUS_BONDED");
        queryMap.put("pagination.offset", "0");
        queryMap.put("pagination.limit", "500");
        queryMap.put("pagination.count_total", "true");
        return apiClient.queryAllStakingValidators(queryMap);
    }

    /**
     * 根据状态查询节点
     *
     * @param status
     * @return
     * @throws Exception
     */
    public QueryOuterClass.QueryValidatorsResponse queryStakingValidatorsByStatus(Staking.BondStatus status) throws Exception {
        MultiValuedMap<String, String> queryMap = new ArrayListValuedHashMap<>();
        queryMap.put("status", status.name());
        queryMap.put("pagination.offset", "0");
        queryMap.put("pagination.limit", "500");
        queryMap.put("pagination.count_total", "true");
        return apiClient.queryStakingValidatorsByStatus(queryMap);
    }

    /**
     * 查询用户的质押信息
     *
     * @param address
     * @return
     * @throws Exception
     */
    public QueryOuterClass.QueryDelegatorDelegationsResponse queryStakingValidatorsByUser(String address) throws Exception {
        return apiClient.queryStakingValidatorsByUser(address);
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
        return apiClient.queryDelegationReward(address, validator);
    }

    /**------------------------------------   tx 交易相关接口  --------------------------------------**/
    /**
     * 查询交易
     *
     * @param hash
     */
    public ServiceOuterClass.GetTxResponse getTx(String hash) throws Exception {
        return apiClient.getTx(hash);
    }

    /**
     * 获取估算交易执行费用详情
     *
     * @param tx
     * @return
     * @throws Exception
     */
    public ServiceOuterClass.SimulateResponse simulate(TxOuterClass.Tx tx) throws Exception {
        return apiClient.simulate(tx);
    }

    /**
     * 估算交易执行gasLimit
     *
     * @param tx
     * @return
     * @throws Exception
     */
    public long gasLimit(TxOuterClass.Tx tx) throws Exception {
        ServiceOuterClass.SimulateResponse response = simulate(tx);
        BigDecimal gasLimit = new BigDecimal(response.getGasInfo().getGasUsed());
        gasLimit = gasLimit.multiply(new BigDecimal(1.1));
        return gasLimit.longValue();
    }

    /**
     * 发送转账交易
     *
     * @param payerCredentials
     * @param sendInfo
     * @return
     * @throws Exception
     */
    public Abci.TxResponse sendTransferTx(CosmosCredentials payerCredentials, SendInfo sendInfo) throws Exception {
        TxOuterClass.Tx tx = SendTxBuilder.createSendTxRequest(apiClient, payerCredentials, sendInfo, new BigDecimal("0.001"), 0);
        long gasLimit = gasLimit(tx);
        BigDecimal fee = ATOMUnitUtil.nanoAtomToAtom(BigInteger.valueOf(gasLimit));
        fee = fee.multiply(BigDecimal.TEN);         //gasPrice = BigDecimal.TEN;
        return sendTransferTx(payerCredentials, sendInfo, fee, gasLimit);
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
    public Abci.TxResponse sendTransferTx(CosmosCredentials payerCredentials, SendInfo sendInfo, BigDecimal feeInAtom, long gasLimit) throws Exception {
        return apiClient.sendTransferTx(payerCredentials, sendInfo, feeInAtom, gasLimit);
    }

    /**
     * 发送批量转账交易
     *
     * @param payerCredentials
     * @param sendList
     * @return
     * @throws Exception
     */
    public Abci.TxResponse sendMultiTransferTx(CosmosCredentials payerCredentials, List<SendInfo> sendList) throws Exception {
        if (sendList == null || sendList.size() == 0) {
            throw new Exception("sendList is empty");
        }
        TxOuterClass.Tx tx = SendMultiTxBuilder.createSendMultiTxRequest(apiClient, payerCredentials, sendList, new BigDecimal("0.001"), 0);
        long gasLimit = gasLimit(tx);
        BigDecimal fee = ATOMUnitUtil.nanoAtomToAtom(BigInteger.valueOf(gasLimit));
        fee = fee.multiply(BigDecimal.TEN);         //gasPrice = BigDecimal.TEN;
        return sendMultiTransferTx(payerCredentials, sendList, fee, gasLimit);
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
        return apiClient.sendMultiTransferTx(payerCredentials, sendList, feeInAtom, gasLimit);
    }

    /**
     * 发送质押交易
     *
     * @param payerCredentials
     * @param sendInfo
     * @return
     * @throws Exception
     */
    public Abci.TxResponse sendDelegateTx(CosmosCredentials payerCredentials, SendInfo sendInfo) throws Exception {
        TxOuterClass.Tx tx = DelegateTxBuilder.createDelegateTxRequest(apiClient, payerCredentials, sendInfo, new BigDecimal("0.001"), 0);
        long gasLimit = gasLimit(tx);
        BigDecimal fee = ATOMUnitUtil.nanoAtomToAtom(BigInteger.valueOf(gasLimit));
        fee = fee.multiply(BigDecimal.TEN);         //gasPrice = BigDecimal.TEN;
        return sendDelegateTx(payerCredentials, sendInfo, fee, gasLimit);
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
        return apiClient.sendDelegateTx(payerCredentials, sendInfo, feeInAtom, gasLimit);
    }

    /**
     * 发送取消质押交易
     *
     * @param payerCredentials
     * @param sendInfo
     * @return
     * @throws Exception
     */
    public Abci.TxResponse sendUnDelegateTx(CosmosCredentials payerCredentials, SendInfo sendInfo) throws Exception {
        TxOuterClass.Tx tx = UnDelegateTxBuilder.createUnDelegateTxRequest(apiClient, payerCredentials, sendInfo, new BigDecimal("0.001"), 0);
        long gasLimit = gasLimit(tx);
        BigDecimal fee = ATOMUnitUtil.nanoAtomToAtom(BigInteger.valueOf(gasLimit));
        fee = fee.multiply(BigDecimal.TEN);         //gasPrice = BigDecimal.TEN;
        return sendUnDelegateTx(payerCredentials, sendInfo, fee, gasLimit);
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
        return apiClient.sendUnDelegateTx(payerCredentials, sendInfo, feeInAtom, gasLimit);
    }

    /**
     * 发送获取质押奖励交易
     *
     * @param payerCredentials
     * @param validator
     * @return
     * @throws Exception
     */
    public Abci.TxResponse sendWithdrawRewardTx(CosmosCredentials payerCredentials, String validator) throws Exception {
        TxOuterClass.Tx tx = WithdrawRewardTxBuilder.createWithdrawRewardTxRequest(apiClient, payerCredentials, validator, new BigDecimal("0.001"), 0);
        long gasLimit = gasLimit(tx);
        BigDecimal fee = ATOMUnitUtil.nanoAtomToAtom(BigInteger.valueOf(gasLimit));
        fee = fee.multiply(BigDecimal.TEN);         //gasPrice = BigDecimal.TEN;

        return sendWithdrawRewardTx(payerCredentials, validator, fee, gasLimit);
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
        return apiClient.sendWithdrawRewardTx(payerCredentials, validator, feeInAtom, gasLimit);
    }

    public CosmosRestApiClient getApiClient() {
        return apiClient;
    }

    public CosmosAddressUtil getAddressUtil() {
        return addressUtil;
    }

}