package network.nerve.heterogeneous.utils;

import org.bitcoinj.base.Address;
import org.bitcoinj.base.LegacyAddress;
import org.bitcoinj.base.ScriptType;
import org.bitcoinj.core.*;
import org.bitcoinj.crypto.ECKey;
import org.bitcoinj.crypto.SignatureDecodeException;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.script.Script;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.math.ec.ECPoint;
import org.web3j.crypto.ECDSASignature;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

import static org.web3j.crypto.Sign.CURVE_PARAMS;

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

    public static String getBtcAddress(String priKey) {
        NetworkParameters params = TestNet3Params.get();
        ECKey key = ECKey.fromPrivate(new BigInteger(1, HexUtil.decode(priKey)));
        Address address = Address.fromKey(params, key, ScriptType.P2PKH);
        System.out.println(HexUtil.encode(key.getPubKeyHash()));
        System.out.println(HexUtil.encode(key.getPubKey()));
        LegacyAddress legacyAddress = LegacyAddress.fromPubKeyHash(TestNet3Params.get(), key.getPubKeyHash());
        System.out.println(legacyAddress.toString().equals(address.toString()));
        return address.toString();
    }

    public static String getEthAddressFromPubKey(String compressedPublicKey) {
        String orginPubkeyStr = "0x" + Numeric.toHexStringNoPrefix(originPublicKey(compressedPublicKey)).substring(2);
        return "0x" + Keys.getAddress(orginPubkeyStr);
    }

    public static final ECDomainParameters CURVE = new ECDomainParameters(CURVE_PARAMS.getCurve(), CURVE_PARAMS.getG(), CURVE_PARAMS.getN(), CURVE_PARAMS.getH());

    public static byte[] originPublicKey(String compressedPublicKey) {
        ECPoint ecPoint = CURVE.getCurve().decodePoint(Numeric.hexStringToByteArray(compressedPublicKey));
        return ecPoint.getEncoded(false);
    }

    public static byte[] dataToBytes(String data) {
        //这里一个字符都不能少，eth签名规则必须拼上 "\u0019Ethereum Signed Message:\n"
        String prefix = "\u0019Ethereum Signed Message:\n" + data.length();
        return Hash.sha3((prefix + data).getBytes());
    }

}
