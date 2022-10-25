package network.nerve.cosmos;

import com.jeongen.cosmos.crypro.CosmosCredentials;
import network.nerve.heterogeneous.core.CosmosWalletApi;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Before;
import org.junit.Test;

public class CosmosWalletApiTest {


    private CosmosWalletApi cosmosWalletApi;

    @Before
    public void before() {
        String baseUrl = "https://api.cosmos.network";
        String chainId = "cosmoshub-4";
        String token = "uatom";
        cosmosWalletApi = CosmosWalletApi.createInstance(baseUrl, chainId, token);
    }

    @Test
    public void testAddress() {
        String priKey = "0x7483feb34efd850875b89957e978325860eb5091e428e54143560b07eccd4f04";
        System.out.println(cosmosWalletApi.getAddress(priKey));
    }

    @Test
    public void getAtomBalance() {
        String address = "";
    }
}
