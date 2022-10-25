package network.nerve.heterogeneous.core;

import com.jeongen.cosmos.CosmosRestApiClient;
import com.jeongen.cosmos.crypro.CosmosCredentials;
import network.nerve.heterogeneous.utils.StringUtils;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class CosmosWalletApi {

    private static Logger Log = LoggerFactory.getLogger(CosmosWalletApi.class.getName());

    private CosmosRestApiClient client;


    public static CosmosWalletApi createInstance(String baseUrl, String chainId, String token) {
        CosmosWalletApi walletApi = new CosmosWalletApi();
        walletApi.client = new CosmosRestApiClient(baseUrl, chainId, token);
        return walletApi;
    }

    /**
     * 通过私钥得到地址
     *
     * @param priKey
     * @return
     */
    public String getAddress(String priKey) {
        if (priKey.startsWith("0x")) {
            priKey = priKey.substring(2);
        }
        byte[] privateKey = Hex.decode(priKey);
        CosmosCredentials credentials = CosmosCredentials.create(privateKey);
        return credentials.getAddress();
    }

    /**
     * 获取地址代币余额
     * @param address
     * @return
     * @throws Exception
     */
    public BigDecimal getAtomBalance(String address) throws Exception {
        return client.getBalanceInAtom(address);
    }
}
