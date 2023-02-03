package network.nerve.heterogeneous.utils;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.SignatureDecodeException;
import org.web3j.utils.Numeric;

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
}
