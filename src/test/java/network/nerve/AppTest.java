package network.nerve;

import network.nerve.heterogeneous.core.HtgWalletApi;
import network.nerve.heterogeneous.crypto.Sign;
import network.nerve.heterogeneous.model.EthSendTransactionPo;
import network.nerve.heterogeneous.utils.HexUtil;
import network.nerve.heterogeneous.utils.HtgCommonTools;
import network.nerve.heterogeneous.utils.JsonRpcUtil;
import network.nerve.heterogeneous.utils.RpcResult;
import org.bouncycastle.math.ec.ECPoint;
import org.junit.Test;
import org.web3j.abi.datatypes.Array;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Keys;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

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
                from, pri, value, to, "0xb339211438Dcbf3D00d7999ad009637472FC72b3", erc20Contract, BigInteger.valueOf(56062), BigInteger.valueOf(1000000000L)
        );
        System.out.println(po.getTxHex());
    }

    @Test
    public void testTransferERC721() throws Exception {
        HtgWalletApi walletApi = HtgWalletApi.getInstance("BNB", "BSC", "https://data-seed-prebsc-1-s1.binance.org:8545/", 97);
        String pri = "5bd38f6ce7768cc851e44133bec1811a5308ed5a09bb234134ffa15736a4ce2a";
        String from = "0x45cCF4B9F8447191C38F5134d8C58F874335028d";
        String to = "0x3083f7ed267dca41338de3401c4e054db2a1cd2f";
        String tokenAddress = "0xE1C8E1f7e2D8afFfA5271456af454243C39B6542";
        String data = "";
        BigInteger tokenId = BigInteger.ZERO;
        BigInteger gasPrice = BigInteger.valueOf(10000000000L);
        BigInteger gasLimit = walletApi.estimateGasForTransferERC721(tokenAddress, from, to, tokenId, data);

        EthSendTransactionPo po = walletApi.createTransferERC721Token(tokenAddress, from, to, tokenId, data, pri, gasLimit, gasPrice);

        EthSendTransaction tx = walletApi.send(po.getTxHex());
        System.out.println(tx.getTransactionHash());

    }

    @Test
    public void testTransferERC1155() throws Exception {
        HtgWalletApi walletApi = HtgWalletApi.getInstance("BNB", "BSC", "https://data-seed-prebsc-1-s1.binance.org:8545/", 97);
        String pri = "5bd38f6ce7768cc851e44133bec1811a5308ed5a09bb234134ffa15736a4ce2a";
        String from = "0x45cCF4B9F8447191C38F5134d8C58F874335028d";
        String to = "0x3083f7ed267dca41338de3401c4e054db2a1cd2f";
        String tokenAddress = "0x1f4D47cC8750243CfffFf5F5Fdb26C9f0CF2B774";
        String data = "";
        List<Uint256> tokenIdList = Arrays.asList(new Uint256(0L), new Uint256(1L));
        List<Uint256> values = Arrays.asList(new Uint256(1L), new Uint256(1L));
        BigInteger gasPrice = BigInteger.valueOf(10000000000L);
        BigInteger gasLimit = walletApi.estimateGasForTransferERC1155(tokenAddress, from, to, tokenIdList, values, data);

        EthSendTransactionPo po = walletApi.createTransferERC1155Token(tokenAddress, from, to, tokenIdList, values, data, pri, gasLimit, gasPrice);

        EthSendTransaction tx = walletApi.send(po.getTxHex());
        System.out.println(tx.getTransactionHash());
    }

    @Test
    public void testERC1155BalanceOf() throws Exception {
        HtgWalletApi walletApi = HtgWalletApi.getInstance("BNB", "BSC", "https://data-seed-prebsc-1-s1.binance.org:8545/", 97);
        String tokenAddress = "0x1f4D47cC8750243CfffFf5F5Fdb26C9f0CF2B774";
        String address = "0x3083f7ed267dca41338de3401c4e054db2a1cd2f";
        BigInteger tokenId = BigInteger.ZERO;

        BigInteger balance = walletApi.getERC1155Balance(address, tokenAddress, tokenId);
        System.out.println(balance);
    }

    @Test
    public void testERC1155BatchBalanceOf() throws Exception {
        HtgWalletApi walletApi = HtgWalletApi.getInstance("BNB", "BSC", "https://data-seed-prebsc-1-s1.binance.org:8545/", 97);
        String tokenAddress = "0x1f4D47cC8750243CfffFf5F5Fdb26C9f0CF2B774";
        List<String> addressList = Arrays.asList("0x3083f7ed267dca41338de3401c4e054db2a1cd2f", "0x3083f7ed267dca41338de3401c4e054db2a1cd2f");
        List<Uint256> tokenIdList = Arrays.asList(new Uint256(0L),new Uint256(1L));

        List<BigInteger> balances = walletApi.getERC1155BatchBalance(addressList, tokenAddress, tokenIdList);
        for(BigInteger bigInteger : balances) {
            System.out.println(bigInteger);
        }
    }

    @Test
    public void personalSignForTRONTest() {
        String priKey = "4594348e3482b751aa235b8e580efef69db465b3a291c5662ceda6459ed12e39";
        //String data = "hello world";// 0xef7af408a9b43f21b595245cfcddeb04e1c6cff41399a5e957c7105199cf430c460b5686f9e6243b43a41fa8bbb74c4f86aa950e7dfa127dee37c41202d7f6871b
        String data = "d8d66d0d6831cc428e19b48b80ef7b540f58e16974d9322e754fec7d51904369";//0x812a342321cb186eaa935bcab346f16782f741ddfacc629fc76ab3c341e427ec29a0740a2c75b5a1244bf03f128be9ac8cfb75cb19f21c83a68d9617eb2092311c
        String result = HtgCommonTools.personalSignForTRON(priKey, data);
        System.out.println(result);
    }

}
