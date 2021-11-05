package network.nerve;

import network.nerve.heterogeneous.core.HtgWalletApi;
import network.nerve.heterogeneous.crypto.Sign;
import network.nerve.heterogeneous.model.EthSendTransactionPo;
import network.nerve.heterogeneous.utils.JsonRpcUtil;
import network.nerve.heterogeneous.utils.RpcResult;
import org.bouncycastle.math.ec.ECPoint;
import org.junit.Test;
import org.web3j.crypto.Keys;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
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

    @Test
    public void test() throws Exception {
        HtgWalletApi walletApi = HtgWalletApi.getInstance("HT", "Heco", "https://http-testnet.hecochain.com", 256);
        String pri = "0d4fd685cdaee9c9fb327f6de941e0bb59dad0171ab97281954519abafbc9fe3";
        String from = "0x45ccf4b9f8447191c38f5134d8c58f874335028d";
        String to = "TNVTdTSPFPov2xBAMRSnnfEwXjEDTVAASFEh6";
        BigInteger value = new BigInteger("100000000000000000000");
        String erc20Contract = "0xa8b8a0751b658dc8c69738283b9d4a79c87a3b3e";
        EthSendTransactionPo po = walletApi.createRechargeErc20WithGas(
                from, pri, value, to, "0xb339211438Dcbf3D00d7999ad009637472FC72b3" , erc20Contract, BigInteger.valueOf(56062), BigInteger.valueOf(1000000000L)
        );
        System.out.println(po.getTxHex());
    }
}
