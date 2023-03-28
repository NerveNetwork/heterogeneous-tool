package com.jeongen.cosmos.tx;

import com.google.protobuf.Any;
import com.jeongen.cosmos.CosmosRestApiClient;
import com.jeongen.cosmos.crypro.CosmosCredentials;
import com.jeongen.cosmos.util.ATOMUnitUtil;
import com.jeongen.cosmos.util.SignUtil;
import com.jeongen.cosmos.vo.SendInfo;
import cosmos.auth.v1beta1.Auth;
import cosmos.base.v1beta1.CoinOuterClass;
import cosmos.staking.v1beta1.Tx;
import cosmos.tx.v1beta1.TxOuterClass;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class UnDelegateTxBuilder {

    /**
     * 组装退出质押交易
     *
     * @param payerCredentials 支付手续费的账户
     * @param sendInfo         质押信息
     * @param feeInAtom        手续费总额
     * @param gasLimit         gas最大可用量（gas用完时，矿工会退出执行，且扣除手续费）
     * @return 交易哈希
     * @throws Exception API 错误
     */
    public static TxOuterClass.Tx createUnDelegateTxRequest(CosmosRestApiClient apiClient, ATOMUnitUtil atomUnitUtil, CosmosCredentials payerCredentials,
                                                            SendInfo sendInfo, BigDecimal feeInAtom, long gasLimit) throws Exception {
        Map<String, Auth.BaseAccount> baseAccountCache = new HashMap<>();
        TxOuterClass.TxBody.Builder txBodyBuilder = TxOuterClass.TxBody.newBuilder();
        TxOuterClass.AuthInfo.Builder authInfoBuilder = TxOuterClass.AuthInfo.newBuilder();
        TxOuterClass.Tx.Builder txBuilder = TxOuterClass.Tx.newBuilder();

        //创建质押信息
        Tx.MsgUndelegate.Builder msgBuilder = Tx.MsgUndelegate.newBuilder();
        msgBuilder.setValidatorAddress(sendInfo.getToAddress());
        msgBuilder.setDelegatorAddress(sendInfo.getCredentials().getAddress());
        BigInteger sendAmountInMicroAtom = atomUnitUtil.atomToMicroAtomBigInteger(sendInfo.getAmount());
        CoinOuterClass.Coin amountCoin = CoinOuterClass.Coin.newBuilder()
                .setAmount(sendAmountInMicroAtom.toString())
                .setDenom(sendInfo.getDemon())
                .build();
        msgBuilder.setAmount(amountCoin);
        txBodyBuilder.addMessages(Any.pack(msgBuilder.build(), "/"));

        //组装手续费
        CoinOuterClass.Coin feeCoin = CoinOuterClass.Coin.newBuilder()
                .setAmount(atomUnitUtil.atomToMicroAtom(feeInAtom).toPlainString())
                .setDenom(apiClient.getTokenDemon())
                .build();

        String payerAddress = payerCredentials.getAddress();
        if (payerCredentials.getAddress().equals(sendInfo.getCredentials().getAddress())) {
            payerAddress = "";
        }
        TxOuterClass.Fee fee = TxOuterClass.Fee.newBuilder()
                .setGasLimit(gasLimit)
                .setPayer(payerAddress)
                .addAmount(feeCoin)
                .build();
        authInfoBuilder.setFee(fee);

        //设置签名信息
        authInfoBuilder.addSignerInfos(SignUtil.getSignInfo(apiClient, sendInfo.getCredentials(), baseAccountCache));
        if (!payerCredentials.getAddress().equals(sendInfo.getCredentials().getAddress())) {
            authInfoBuilder.addSignerInfos(SignUtil.getSignInfo(apiClient, payerCredentials, baseAccountCache));
        }

        TxOuterClass.TxBody txBody = txBodyBuilder.build();
        txBuilder.setBody(txBody);
        TxOuterClass.AuthInfo authInfo = authInfoBuilder.build();
        txBuilder.addSignatures(SignUtil.getSignBytes(apiClient, sendInfo.getCredentials(), txBody, authInfo, baseAccountCache));
        if (!payerCredentials.getAddress().equals(sendInfo.getCredentials().getAddress())) {
            txBuilder.addSignatures(SignUtil.getSignBytes(apiClient, payerCredentials, txBody, authInfo, baseAccountCache));
        }

        txBuilder.setAuthInfo(authInfo);
        TxOuterClass.Tx tx = txBuilder.build();
        return tx;
    }
}
