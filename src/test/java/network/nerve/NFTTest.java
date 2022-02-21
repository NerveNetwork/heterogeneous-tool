package network.nerve;

import network.nerve.heterogeneous.core.HtgWalletApi;
import network.nerve.heterogeneous.model.EthSendTransactionPo;
import network.nerve.heterogeneous.utils.EthFunctionUtil;
import org.junit.Test;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthSendTransaction;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class NFTTest {


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
        List<Uint256> tokenIdList = Arrays.asList(new Uint256(0L), new Uint256(1L));

        List<BigInteger> balances = walletApi.getERC1155BatchBalance(addressList, tokenAddress, tokenIdList);
        for (BigInteger bigInteger : balances) {
            System.out.println(bigInteger);
        }
    }

    @Test
    public void testTotalSupply() throws Exception {
        HtgWalletApi walletApi = HtgWalletApi.getInstance("BNB", "BSC", "https://data-seed-prebsc-1-s2.binance.org:8545/", 97);
        String tokenAddress = "0x219AB82cFF6313fb913adf9D9AEB982E2BfD877b";

        System.out.println(walletApi.getTotalSupply(tokenAddress));
    }
}
