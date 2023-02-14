package network.nerve;

import network.nerve.heterogeneous.utils.JsonRpcUtil;
import network.nerve.heterogeneous.utils.RpcResult;
import org.junit.Test;
import org.web3j.ens.EnsResolver;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

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
    public void testEns() {
        String apiUrl = "https://eth-mainnet.public.blastapi.io";
        Web3j web3j = Web3j.build(new HttpService(apiUrl));
        EnsResolver ens = new EnsResolver(web3j, 300);
        System.out.println(ens.resolve("000.eth"));

        //byte[] nameHash = NameHash.nameHashAsBytes("000.eth");
    }
}
