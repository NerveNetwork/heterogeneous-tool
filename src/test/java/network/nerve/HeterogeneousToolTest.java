package network.nerve;

import network.nerve.heterogeneous.ETHTool;
import network.nerve.heterogeneous.HeterogeneousTool;
import network.nerve.heterogeneous.constant.Constant;
import network.nerve.heterogeneous.context.AStarContext;
import network.nerve.heterogeneous.context.EthContext;
import org.junit.Before;
import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionDecoder;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;

import java.math.BigDecimal;
import java.math.BigInteger;

import static network.nerve.heterogeneous.constant.Constant.GAS_LIMIT_OF_MAIN;

/**
 * @author: Charlie
 * @date: 2021/5/18
 */
public class HeterogeneousToolTest {

    HeterogeneousTool heterogeneousTool;
    Web3j web3j;

    @Before
    public void before() {
        heterogeneousTool = new HeterogeneousTool(AStarContext.symbol, AStarContext.chainName, AStarContext.mainRpcAddress);
        web3j = Web3j.build(new HttpService(AStarContext.mainRpcAddress));
    }


    @Test
    public void assembleTransferMainAssetTest() throws Exception {
        String fromAddress = "0x6565f70d9a1eEEbCE196E29B7885DCd9cd877F6B";
        String prikey = "1";
        String toAddress = "0x6565f70d9a1eEEbCE196E29B7885DCd9cd877F6B";
        BigDecimal amount = new BigDecimal("0.0001");
        BigInteger gasLimit = Constant.GAS_LIMIT_OF_RECHARGE_MAIN;

        BigInteger gasPrice = web3j.ethGasPrice().send().getGasPrice();
        gasPrice = gasPrice.multiply(new BigInteger("5"));
        long s = System.currentTimeMillis();
        System.out.println("start:" + s);
        // 提供接口 组装交易 返回未签名的hex
        String txHex = heterogeneousTool.assembleTransferMainAsset(fromAddress, toAddress, amount, gasLimit, gasPrice);

        // 第三方 签名
        RawTransaction rawTransaction = TransactionDecoder.decode(txHex);
        Credentials credentials = Credentials.create(prikey);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, AStarContext.mainChainId, credentials);
        String hexValue = Numeric.toHexString(signedMessage);
        System.out.println("hexValue:" + hexValue);
        // 提供接口 发送交易
        EthSendTransaction send = web3j.ethSendRawTransaction(hexValue).sendAsync().get();

        System.out.println("hash:" + send.getTransactionHash());
        System.out.println("耗时:" + (System.currentTimeMillis() - s));
    }

    @Test
    public void test() throws Exception {
        /*
         "contractAddress": "0xe845959b0bc4426116501ae953e769fe127b7ea5",
        "fromAddress": "0x45cCF4B9F8447191C38F5134d8C58F874335028d",
        "amount": "1000000000000",
         */
        String fromAddress = "0x45cCF4B9F8447191C38F5134d8C58F874335028d";
        String value = "1000000000000";
        String toAddress = "TNVTdTSPFPov2xBAMRSnnfEwXjEDTVAASFEh6";
        String multyAddress = "0x7D759A3330ceC9B766Aa4c889715535eeD3c0484";
        String erc20Address = "0xe845959b0bc4426116501ae953e769fe127b7ea5";
        String s = heterogeneousTool.assembleRechargeERC20Token(fromAddress, new BigInteger(value), toAddress, multyAddress, erc20Address);
        System.out.println(s);
    }
}
