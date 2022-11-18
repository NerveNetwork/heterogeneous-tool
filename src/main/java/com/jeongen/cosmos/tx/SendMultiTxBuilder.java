package com.jeongen.cosmos.tx;

import com.google.protobuf.Any;
import com.jeongen.cosmos.CosmosRestApiClient;
import com.jeongen.cosmos.crypro.CosmosCredentials;
import com.jeongen.cosmos.util.ATOMUnitUtil;
import com.jeongen.cosmos.util.SignUtil;
import com.jeongen.cosmos.vo.SendInfo;
import cosmos.auth.v1beta1.Auth;
import cosmos.bank.v1beta1.Tx;
import cosmos.base.v1beta1.CoinOuterClass;
import cosmos.tx.v1beta1.TxOuterClass;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendMultiTxBuilder {

    /**
     * 组装批量转账交易
     *
     * @param payerCredentials 支付手续费的账户
     * @param sendList         转账信息
     * @param feeInAtom        手续费总额
     * @param gasLimit         gas最大可用量（gas用完时，矿工会退出执行，且扣除手续费）
     * @return 交易哈希
     * @throws Exception API 错误
     */
    public static TxOuterClass.Tx createSendMultiTxRequest(CosmosRestApiClient apiClient, CosmosCredentials payerCredentials, List<SendInfo> sendList, BigDecimal feeInAtom, long gasLimit) throws Exception {
        Map<String, Auth.BaseAccount> baseAccountCache = new HashMap<>();
        TxOuterClass.TxBody.Builder txBodyBuilder = TxOuterClass.TxBody.newBuilder();
        TxOuterClass.AuthInfo.Builder authInfoBuilder = TxOuterClass.AuthInfo.newBuilder();
        TxOuterClass.Tx.Builder txBuilder = TxOuterClass.Tx.newBuilder();

        Map<String, Boolean> signerInfoExistMap = new HashMap<>();
        Map<String, Boolean> signaturesExistMap = new HashMap<>();

        for (SendInfo sendInfo : sendList) {
            BigInteger sendAmountInMicroAtom = ATOMUnitUtil.atomToMicroAtomBigInteger(sendInfo.getAmount());
            CoinOuterClass.Coin sendCoin = CoinOuterClass.Coin.newBuilder()
                    .setAmount(sendAmountInMicroAtom.toString())
                    .setDenom(sendInfo.getDemon())
                    .build();

            Tx.MsgSend message = Tx.MsgSend.newBuilder()
                    .setFromAddress(sendInfo.getCredentials().getAddress())
                    .setToAddress(sendInfo.getToAddress())
                    .addAmount(sendCoin)
                    .build();

            txBodyBuilder.addMessages(Any.pack(message, "/"));

            if (!signerInfoExistMap.containsKey(sendInfo.getCredentials().getAddress())) {
                authInfoBuilder.addSignerInfos(SignUtil.getSignInfo(apiClient, sendInfo.getCredentials(), baseAccountCache));
                signerInfoExistMap.put(sendInfo.getCredentials().getAddress(), true);
            }
        }
        if (!signerInfoExistMap.containsKey(payerCredentials.getAddress())) {
            authInfoBuilder.addSignerInfos(SignUtil.getSignInfo(apiClient, payerCredentials, baseAccountCache));
            signerInfoExistMap.put(payerCredentials.getAddress(), true);
        }

        CoinOuterClass.Coin feeCoin = CoinOuterClass.Coin.newBuilder()
                .setAmount(ATOMUnitUtil.atomToMicroAtom(feeInAtom).toPlainString())
                .setDenom(apiClient.getTokenDemon())
                .build();

        String payerAddress = payerCredentials.getAddress();
        if (sendList.get(0).getCredentials().getAddress().equals(payerCredentials.getAddress())) {
            payerAddress = "";
        }
        TxOuterClass.Fee fee = TxOuterClass.Fee.newBuilder()
                .setGasLimit(gasLimit)
                .setPayer(payerAddress)
                .addAmount(feeCoin)
                .build();

        authInfoBuilder.setFee(fee);

        TxOuterClass.TxBody txBody = txBodyBuilder.build();

        TxOuterClass.AuthInfo authInfo = authInfoBuilder.build();

        for (SendInfo sendInfo : sendList) {
            if (!signaturesExistMap.containsKey(sendInfo.getCredentials().getAddress())) {
                txBuilder.addSignatures(SignUtil.getSignBytes(apiClient, sendInfo.getCredentials(), txBody, authInfo, baseAccountCache));
                signaturesExistMap.put(sendInfo.getCredentials().getAddress(), true);
            }
        }
        if (!signaturesExistMap.containsKey(payerCredentials.getAddress())) {
            txBuilder.addSignatures(SignUtil.getSignBytes(apiClient, payerCredentials, txBody, authInfo, baseAccountCache));
            signaturesExistMap.put(payerCredentials.getAddress(), true);
        }

        txBuilder.setBody(txBody);
        txBuilder.setAuthInfo(authInfo);
        TxOuterClass.Tx tx = txBuilder.build();
        return tx;
    }
}
