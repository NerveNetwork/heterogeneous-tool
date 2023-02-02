package network.nerve.heterogeneous.utils;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.SignatureDecodeException;
import org.web3j.crypto.ECDSASignature;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class SignValidateUtil {

    public static boolean verifyForBTC(String pubKey, String dataHex, String signatureHex) throws SignatureDecodeException {
        dataHex = Numeric.cleanHexPrefix(dataHex);
        signatureHex = Numeric.cleanHexPrefix(signatureHex);
        pubKey = Numeric.cleanHexPrefix(pubKey);
        byte[] pubKeyBytes = HexUtil.decode(pubKey);
        byte[] dataBytes = HexUtil.decode(dataHex);
        byte[] signBytes = HexUtil.decode(signatureHex);
        return ECKey.verify(dataBytes, signBytes, pubKeyBytes);
    }

    public static boolean verifyForETH(String pubKey, String data, String signatureHex) {
        signatureHex = Numeric.cleanHexPrefix(signatureHex);
        if (signatureHex.length() != 130) {
            return false;
        }
        String r = "0x" + signatureHex.substring(0, 64);
        String s = "0x" + signatureHex.substring(64, 128);
        ECDSASignature signature = new ECDSASignature(Numeric.decodeQuantity(r), Numeric.decodeQuantity(s));

        byte[] dataBytes;
        if (HexUtil.isHexStr(data)) {
            dataBytes = Numeric.hexStringToByteArray(data);
        } else {
            dataBytes = dataToBytes(data);
        }

        String address = getEthAddressFromPubKey(pubKey);
        String signAddress;
        for (int i = 0; i < 4; i++) {
            BigInteger recover = Sign.recoverFromSignature(i, signature, dataBytes);
            if (recover != null) {
                signAddress = "0x" + Keys.getAddress(recover).toLowerCase();
                if (signAddress.equalsIgnoreCase(address)) {
                    return true;
                }
            }
        }
        return false;

    }

    /**
     * 根据公钥获取以太坊地址
     *
     * @param publicKey
     * @return
     */
    public static String getEthAddressFromPubKey(String publicKey) {
        org.ethereum.crypto.ECKey ecKey = org.ethereum.crypto.ECKey.fromPublicOnly(Numeric.hexStringToByteArray(publicKey));
        String orginPubKeyStr = "0x" + Numeric.toHexStringNoPrefix(ecKey.getPubKey()).substring(2);
        return "0x" + Keys.getAddress(orginPubKeyStr);
    }


    public static byte[] dataToBytes(String data) {
        //这里一个字符都不能少，eth签名规则必须拼上 "\u0019Ethereum Signed Message:\n"
        String prefix = "\u0019Ethereum Signed Message:\n" + data.length();
        return Hash.sha3((prefix + data).getBytes());
    }
}
