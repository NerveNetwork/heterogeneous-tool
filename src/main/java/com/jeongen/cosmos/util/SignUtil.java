package com.jeongen.cosmos.util;

import com.gg.protobuf.Any;
import com.google.protobuf.ByteString;
import com.jeongen.cosmos.CosmosRestApiClient;
import com.jeongen.cosmos.crypro.CosmosCredentials;
import cosmos.auth.v1beta1.Auth;
import cosmos.auth.v1beta1.QueryOuterClass;
import cosmos.crypto.secp256k1.Keys;
import cosmos.tx.signing.v1beta1.Signing;
import cosmos.tx.v1beta1.TxOuterClass;
import org.bitcoinj.core.Sha256Hash;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Sign;

import java.util.Map;

public class SignUtil {

    public static TxOuterClass.SignerInfo getSignInfo(CosmosRestApiClient apiClient, CosmosCredentials credentials, Map<String, Auth.BaseAccount> baseAccountCache) throws Exception {
        byte[] encodedPubKey = credentials.getEcKey().getPubKeyPoint().getEncoded(true);
        Keys.PubKey pubKey = Keys.PubKey.newBuilder()
                .setKey(ByteString.copyFrom(encodedPubKey))
                .build();
        TxOuterClass.ModeInfo.Single single = TxOuterClass.ModeInfo.Single.newBuilder()
                .setMode(Signing.SignMode.SIGN_MODE_DIRECT)
                .build();

        Auth.BaseAccount baseAccount = queryBaseAccount(apiClient, credentials.getAddress(), baseAccountCache);
        TxOuterClass.SignerInfo signerInfo = TxOuterClass.SignerInfo.newBuilder()
                .setPublicKey(Any.pack(pubKey, "/"))
                .setModeInfo(TxOuterClass.ModeInfo.newBuilder().setSingle(single))
                .setSequence(baseAccount.getSequence())
                .build();
        return signerInfo;
    }

    public static Auth.BaseAccount queryBaseAccount(CosmosRestApiClient apiClient, String address, Map<String, Auth.BaseAccount> cacheMap) throws Exception {
        if (cacheMap.containsKey(address)) {
            return cacheMap.get(address);
        }
        Auth.BaseAccount baseAccount = queryBaseAccount(apiClient, address);
        cacheMap.put(address, baseAccount);
        return baseAccount;
    }

    public static Auth.BaseAccount queryBaseAccount(CosmosRestApiClient apiClient, String address) throws Exception {
        QueryOuterClass.QueryAccountResponse res = apiClient.queryAccount(address);
        if (res.hasAccount() && res.getAccount().is(Auth.BaseAccount.class)) {
            return res.getAccount().unpack(Auth.BaseAccount.class);
        }
        throw new RuntimeException("account not found:" + address);
    }


    public static ByteString getSignBytes(CosmosRestApiClient apiClient, CosmosCredentials credentials, TxOuterClass.TxBody txBody, TxOuterClass.AuthInfo authInfo, Map<String, Auth.BaseAccount> baseAccountCache) throws Exception {
        Auth.BaseAccount baseAccount = queryBaseAccount(apiClient, credentials.getAddress(), baseAccountCache);
        byte[] sigBytes = signDoc(credentials.getEcKey().getPrivKeyBytes(), baseAccount, txBody, authInfo, apiClient.getChainId());
        return ByteString.copyFrom(sigBytes);
    }


    public static byte[] signDoc(byte[] privateKey, Auth.BaseAccount baseAccount, TxOuterClass.TxBody txBody, TxOuterClass.AuthInfo authInfo, String chainId) {
        ECKeyPair keyPair = ECKeyPair.create(privateKey);
        TxOuterClass.SignDoc signDoc = TxOuterClass.SignDoc.newBuilder()
                .setBodyBytes(txBody.toByteString())
                .setAuthInfoBytes(authInfo.toByteString())
                .setAccountNumber(baseAccount.getAccountNumber())
                .setChainId(chainId)
                .build();
        byte[] hash = Sha256Hash.hash(signDoc.toByteArray());
        Sign.SignatureData signature = Sign.signMessage(hash, keyPair, false);
        return mergeBytes(signature.getR(), signature.getS());
    }

    private static byte[] mergeBytes(byte[] array1, byte[] array2) {
        byte[] joinedArray = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }
}
