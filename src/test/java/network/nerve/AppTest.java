package network.nerve;

import network.nerve.heterogeneous.utils.JsonRpcUtil;
import network.nerve.heterogeneous.utils.RpcResult;
import org.junit.Test;

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
}
