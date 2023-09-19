package network.nerve;

import network.nerve.heterogeneous.core.HtgWalletApi;
import network.nerve.heterogeneous.crypto.Sign;
import network.nerve.heterogeneous.model.EthSendTransactionPo;
import network.nerve.heterogeneous.utils.*;
import org.bitcoinj.core.SignatureDecodeException;
import org.bouncycastle.math.ec.ECPoint;
import org.junit.Test;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Keys;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static network.nerve.heterogeneous.constant.Constant.FORWARD_PATH;

/**
 * Unit test for simple App.
 */
public class AppTest {


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


    public void addressByPublicKey() {
        String pub = "03e5ce2adaac4168c7c1f6ddc7dbe5cd296fb1bbbb54a9f069fa7f407b32143fc0";
        ECPoint ecPoint = Sign.CURVE.getCurve().decodePoint(Numeric.hexStringToByteArray(pub));
        byte[] encoded = ecPoint.getEncoded(false);
        String orginPubkeyStr = "0x" + Numeric.toHexStringNoPrefix(encoded).substring(2);
        String address = "0x" + Keys.getAddress(orginPubkeyStr);
        System.out.println(address);

    }


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


    public void testAddress() {
        HtgWalletApi walletApi = HtgWalletApi.getInstance("ETH", "Ethereum", "https://web3.mytokenpocket.vip", 1);
        String address = "0x0c3685559af6f3d20c501b1076a8056a0a14426a";
        int type = walletApi.getAddressType(address);
        System.out.println(type);
    }


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


    public void testERC1155BalanceOf() throws Exception {
        HtgWalletApi walletApi = HtgWalletApi.getInstance("BNB", "BSC", "https://data-seed-prebsc-1-s1.binance.org:8545/", 97);
        String tokenAddress = "0x1f4D47cC8750243CfffFf5F5Fdb26C9f0CF2B774";
        String address = "0x3083f7ed267dca41338de3401c4e054db2a1cd2f";
        BigInteger tokenId = BigInteger.ZERO;

        BigInteger balance = walletApi.getERC1155Balance(address, tokenAddress, tokenId);
        System.out.println(balance);
    }


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


    public void personalSignForTRONTest() {
        String priKey = "4594348e3482b751aa235b8e580efef69db465b3a291c5662ceda6459ed12e39";
        //String data = "hello world";// 0xef7af408a9b43f21b595245cfcddeb04e1c6cff41399a5e957c7105199cf430c460b5686f9e6243b43a41fa8bbb74c4f86aa950e7dfa127dee37c41202d7f6871b
        String data = "d8d66d0d6831cc428e19b48b80ef7b540f58e16974d9322e754fec7d51904369";//0x812a342321cb186eaa935bcab346f16782f741ddfacc629fc76ab3c341e427ec29a0740a2c75b5a1244bf03f128be9ac8cfb75cb19f21c83a68d9617eb2092311c
        String result = HtgCommonTools.personalSignForTRON(priKey, data);
        System.out.println(result);
    }


    public void signTxForTRONTest() {
        String priKey = "4594348e3482b751aa235b8e580efef69db465b3a291c5662ceda6459ed12e39";
        String data = "72d97526527460de672b1da503fedc2d553aee1f64833c4b27670fc0403b6065";//
        String result = HtgCommonTools.signTxForTRON(priKey, data);
        System.out.println(result);
        // adfb83176ffca64cc568dac97e6d49be576cc86eedc9da56214cd7c66ac0810c104343bbc12cf43315081a48aea2a19452035b78a5c98fbb5fb04194ccb4479c00
        // adfb83176ffca64cc568dac97e6d49be576cc86eedc9da56214cd7c66ac0810c104343bbc12cf43315081a48aea2a19452035b78a5c98fbb5fb04194ccb4479c00
    }


    public void ethSignTest() {
        String priKey = "a572b95153b10141ff06c64818c93bd0e7b4025125b83f15a89a7189248191ca";
        String data = "0x405004f905654214d16f097affb67a659be323dd7ba0ee26b9bbaffb35b0b947";//
        String result = HtgCommonTools.ethSign(priKey, data);
        System.out.println(result);
    }



    public void personalSignTest () {
        String priKey = "a572b95153b10141ff06c64818c93bd0e7b4025125b83f15a89a7189248191ca";
        String data = "0x405004f905654214d16f097affb67a659be323dd7ba0ee26b9bbaffb35b0b947";
       // String data = "0x307834303530303466393035363534323134643136663039376166666236376136353962653332336464376261306565323662396262616666623335623062393437";
        String result = HtgCommonTools.personalSign(priKey, data);

        //0x5a64f40b4fb105eb074255875c4abdee523fb8f349d9d3b25dc4cf665e2ee597607d602d6d34c4da0cdbae5b8118b977ed52c929a55a7a51097a8c906736f4551c
        //0x526d6e5bfc0c75221a1123c0ab678d06dbd576014ce3c1fdb8333b316c6cd44c08e8c8368913d31331f9f0a8348a80e23d2aec8aa0b28e2ef95c2e44ca377a4f1c
        System.out.println(result);
    }


    public void jsonTest() throws IOException {
        String json = "{\"txID\":\"bb43580431f46da8b81ea2d20e9092a104308e0d28afc4c308217cc0dad4c86b\",\"raw_data\":{\"contract\":[{\"parameter\":{\"value\":{\"data\":\"38615bb000000000000000000000000000000000000000000000000000000000000000800000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000e00000000000000000000000000000000000000000000000000000000000000025544e56546454535046506f76327842414d52536e6e664577586a4544545641415346456836000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000002466306230623063622d323230362d343230642d623462362d38626136306135316139643900000000000000000000000000000000000000000000000000000000\",\"owner_address\":\"41c11d9943805e56b630a401d4bd9a29550353efa1\",\"contract_address\":\"41f723e62e48f4e0a5160ebaf69a60d7244e462a05\",\"call_value\":1000000},\"type_url\":\"type.googleapis.com/protocol.TriggerSmartContract\"},\"type\":\"TriggerSmartContract\"}],\"ref_block_bytes\":\"ef38\",\"ref_block_hash\":\"772c7da077194ab4\",\"expiration\":1653471637372,\"fee_limit\":150000000,\"timestamp\":1653471435993},\"raw_data_hex\":\"0a02ef382208772c7da077194ab440fcdedbd48f305ab403081f12af030a31747970652e676f6f676c65617069732e636f6d2f70726f746f636f6c2e54726967676572536d617274436f6e747261637412f9020a1541c11d9943805e56b630a401d4bd9a29550353efa1121541f723e62e48f4e0a5160ebaf69a60d7244e462a0518c0843d22c40238615bb000000000000000000000000000000000000000000000000000000000000000800000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000e00000000000000000000000000000000000000000000000000000000000000025544e56546454535046506f76327842414d52536e6e664577586a4544545641415346456836000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000002466306230623063622d323230362d343230642d623462362d3862613630613531613964390000000000000000000000000000000000000000000000000000000070d9b9cfd48f30900180a3c347\",\"signature\":[\"3277dc3b841661d873ca6922151451e708b31d032c244acc95745a216656fdb30272a7188d7479ce33aae82b8dc95d69d9d719c9688704e14a31d3a4c04ad92201\"]}";
        Map<String, Object> map = JSONUtils.json2map(json);
        System.out.println(JSONUtils.obj2PrettyJson(map));
        Map contract0Map = (Map) ((List) ((Map) map.get("raw_data")).get("contract")).get(0);
        Object permissionId = contract0Map.get("Permission_id");
        if (permissionId == null) {
            contract0Map.put("Permission_id", 0);
        } else {
            try {
                Integer.parseInt(permissionId.toString());
            } catch (Exception e) {
                contract0Map.put("Permission_id", 0);
            }
        }
        System.out.println(JSONUtils.obj2PrettyJson(map));
    }


    public void testSignValidate() {
        String pubKey = "1";
        //需要签名的数据
        String data = "a70f833719ea6d7abbf1fcd6cf6f905f";
        //签名结果
        String signed = "3045022100c69559730bef8c41d4699426669a671e517a514906c4d84037589badca315eed02205f8599fba95d852f7d8f437fc5bde3402d5284d40978b27878f1527cdd4ee7ac";

        try {
            System.out.println(SignValidateUtil.verifyForBTC(pubKey, data, signed));
        } catch (SignatureDecodeException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void signTest() {
        String prikey = "1";
        String data = "20853218";
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        String hex =HtgCommonTools.personalSign(prikey ,bytes);
        System.out.println(hex);

    }
}
