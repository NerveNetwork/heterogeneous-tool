package util;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.LegacyAddress;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.script.Script;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.math.ec.ECPoint;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

import static org.web3j.crypto.Sign.CURVE_PARAMS;

public class AddressUtil {

    public static String getBtcAddress(String priKey) {
        NetworkParameters params = TestNet3Params.get();
        ECKey key = ECKey.fromPrivate(new BigInteger(1, HexUtil.decode(priKey)));
        Address address = Address.fromKey(params, key, Script.ScriptType.P2PKH);
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
