package network.nerve;

import network.nerve.heterogeneous.ETHTool;
import network.nerve.heterogeneous.HeterogeneousTool;
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
    public void before(){
        String rpcAddr = "https://ropsten.infura.io/v3/7e086d9f3bdc48e4996a3997b33b032f";
        heterogeneousTool = new HeterogeneousTool(EthContext.symbol, EthContext.chainName, rpcAddr);
        web3j = Web3j.build(new HttpService(rpcAddr));
    }


    @Test
    public void assembleTransferMainAssetTest() throws Exception{
        String fromAddress = "0xfa27c84eC062b2fF89EB297C24aaEd366079c684";
        String prikey = "B36097415F57FE0AC1665858E3D007BA066A7C022EC712928D2372B27E8513FF";
        String toAddress = "0xE133cF1CFc4e19c2962137287EB825B441385F04";
        BigDecimal amount = new BigDecimal("0.02");
        BigInteger gasLimit = GAS_LIMIT_OF_MAIN;
        BigInteger gasPrice = ETHTool.getCurrentGasPrice();
        long s = System.currentTimeMillis();
        System.out.println("start:" + s);
        // 提供接口 组装交易 返回未签名的hex
        String txHex = heterogeneousTool.assembleTransferMainAsset(fromAddress, toAddress, amount, gasLimit, gasPrice);
        System.out.println("txHex:" + txHex);

        // 第三方 签名
        RawTransaction rawTransaction = TransactionDecoder.decode(txHex);
        Credentials credentials = Credentials.create(prikey);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signedMessage);

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
