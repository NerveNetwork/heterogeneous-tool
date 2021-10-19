package network.nerve;

import network.nerve.heterogeneous.crypto.Sign;
import network.nerve.heterogeneous.utils.JsonRpcUtil;
import network.nerve.heterogeneous.utils.RpcResult;
import org.bouncycastle.math.ec.ECPoint;
import org.junit.Test;
import org.web3j.crypto.Keys;
import org.web3j.utils.Numeric;

import java.util.Arrays;

import static network.nerve.heterogeneous.constant.Constant.FORWARD_PATH;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void jsonrpcForMetaMaskTest() {
        String requestURL = "http://192.168.1.132:8083/nabox-api/api";
        String url = requestURL;
        if (requestURL.endsWith("/")) {
            url = url + FORWARD_PATH;
        } else {
            url = url + "/" + FORWARD_PATH;
        }
        System.out.println(url);
        String method = "eth_getBlockByNumber";
        RpcResult result = JsonRpcUtil.requestForMetaMask(url, "BSC", method, Arrays.asList("0x1", false));
        System.out.println(result.toString());
    }

    @Test
    public void addressByPublicKey() {
        String pub = "03e5ce2adaac4168c7c1f6ddc7dbe5cd296fb1bbbb54a9f069fa7f407b32143fc0";
        ECPoint ecPoint = Sign.CURVE.getCurve().decodePoint(Numeric.hexStringToByteArray(pub));
        byte[] encoded = ecPoint.getEncoded(false);
        String orginPubkeyStr = "0x" + Numeric.toHexStringNoPrefix(encoded).substring(2);
        String address = "0x" + Keys.getAddress(orginPubkeyStr);
        System.out.println(address);
    }
}
