package com.jeongen.cosmos.tx;

import com.google.protobuf.Any;
import com.jeongen.cosmos.CosmosRestApiClient;
import com.jeongen.cosmos.crypro.CosmosCredentials;
import com.jeongen.cosmos.util.ATOMUnitUtil;
import com.jeongen.cosmos.util.SignUtil;
import cosmos.auth.v1beta1.Auth;
import cosmos.base.v1beta1.CoinOuterClass;
import cosmos.distribution.v1beta1.Tx;
import cosmos.tx.v1beta1.TxOuterClass;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class WithdrawRewardTxBuilder {


    public static TxOuterClass.Tx createWithdrawRewardTxRequest(CosmosRestApiClient apiClient, ATOMUnitUtil atomUnitUtil, CosmosCredentials payerCredentials,
                                                                String validator, BigDecimal feeInAtom, long gasLimit) throws Exception {
        Map<String, Auth.BaseAccount> baseAccountCache = new HashMap<>();
        TxOuterClass.TxBody.Builder txBodyBuilder = TxOuterClass.TxBody.newBuilder();
        TxOuterClass.AuthInfo.Builder authInfoBuilder = TxOuterClass.AuthInfo.newBuilder();
        TxOuterClass.Tx.Builder txBuilder = TxOuterClass.Tx.newBuilder();

        Tx.MsgWithdrawDelegatorReward.Builder msgBuilder = Tx.MsgWithdrawDelegatorReward.newBuilder();
        msgBuilder.setDelegatorAddress(payerCredentials.getAddress());
        msgBuilder.setValidatorAddress(validator);
        txBodyBuilder.addMessages(Any.pack(msgBuilder.build(), "/"));

        //组装手续费
        CoinOuterClass.Coin feeCoin = CoinOuterClass.Coin.newBuilder()
                .setAmount(atomUnitUtil.atomToMicroAtom(feeInAtom).toPlainString())
                .setDenom(apiClient.getTokenDemon())
                .build();

        String payerAddress = payerCredentials.getAddress();
        if (payerCredentials.getAddress().equals(payerCredentials.getAddress())) {
            payerAddress = "";
        }
        TxOuterClass.Fee fee = TxOuterClass.Fee.newBuilder()
                .setGasLimit(gasLimit)
                .setPayer(payerAddress)
                .addAmount(feeCoin)
                .build();
        authInfoBuilder.setFee(fee);

        //设置签名信息
        authInfoBuilder.addSignerInfos(SignUtil.getSignInfo(apiClient, payerCredentials, baseAccountCache));

        TxOuterClass.TxBody txBody = txBodyBuilder.build();
        txBuilder.setBody(txBody);
        TxOuterClass.AuthInfo authInfo = authInfoBuilder.build();
        txBuilder.addSignatures(SignUtil.getSignBytes(apiClient, payerCredentials, txBody, authInfo, baseAccountCache));

        txBuilder.setAuthInfo(authInfo);
        TxOuterClass.Tx tx = txBuilder.build();
        return tx;
    }
}
