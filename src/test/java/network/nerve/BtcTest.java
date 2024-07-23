/**
 * MIT License
 * <p>
 * Copyright (c) 2017-2018 nuls.io
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package network.nerve;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.neemre.btcdcli4j.core.BitcoindException;
import com.neemre.btcdcli4j.core.CommunicationException;
import com.neemre.btcdcli4j.core.client.BtcdClient;
import network.nerve.base.basic.AddressTool;
import network.nerve.heterogeneous.core.BtcWalletApi;
import network.nerve.heterogeneous.model.BitCoinFeeInfo;
import network.nerve.heterogeneous.model.RechargeData;
import network.nerve.heterogeneous.model.UTXOData;
import network.nerve.heterogeneous.utils.*;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionInput;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.http.HttpService;

import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: PierreLuo
 * @date: 2024/5/27
 */
public class BtcTest {

    private String protocol = "http";
    private String host = "192.168.5.140";
    private String port = "18332";
    private String user = "cobble";
    private String password = "asdf1234";
    private String auth_scheme = "Basic";

    private BtcdClient client;
    private BtcWalletApi btcWalletApi;
    boolean mainnet = false;
    String multisigAddress;
    String nerveApi;

    @BeforeClass
    public static void initClass() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger logger = context.getLogger("com.neemre");
        logger.setAdditive(false);
        logger.setLevel(Level.INFO);
    }

    @Before
    public void before() {
        setTestnet();
    }

    void setTestnet() {
        mainnet = false;
        //protocol = "https";
        //host = "btctest.nerve.network";
        //port = "443";
        //user = "Nerve";
        //password = "9o7fSmXPBfPQoQSbnBB";
        //auth_scheme = "Basic";
        btcWalletApi = new BtcWalletApi();
        btcWalletApi.init("https://btctest.nerve.network,Nerve,9o7fSmXPBfPQoQSbnBB", mainnet);
        multisigAddress = "tb1qtskq8773jlhjqm7ad6a8kxhxleznp0nech0wpk0nxt45khuy0vmqwzeumf";
        nerveApi = "http://beta.api.nerve.network";
    }

    void setMain() {
        mainnet = true;
        //protocol = "https";
        //host = "btc.nerve.network";
        //port = "443";
        //user = "Nerve";
        //password = "9o7fSmXPBCM4F6cAJsfPQoQSbnBB";
        //auth_scheme = "Basic";
        btcWalletApi = new BtcWalletApi();
        btcWalletApi.init("https://btc.nerve.network,Nerve,9o7fSmXPBCM4F6cAJsfPQoQSbnBB", mainnet);
        multisigAddress = "bc1q7l4q8kqekyur4ak3tf4s2rr9rp4nhz6axejxjwrc3f28ywm4tl8smz5dpd";
        nerveApi = "https://api.nerve.network";
    }


    @Test
    public void feeRateTest() throws Exception {
        System.out.println(btcWalletApi.getFeeRate());
    }

    @Test
    public void depositTxTest() throws Exception {
        setTestnet();
        String from = "mmLahgkWGHQSKszCDcZXPooWoRuYhQPpCF";
        String fromPri = "6de8506ce2f2291ea4bd5a8a4718d3179f5591e60eac6e8f6333a84004307b32";
        String to = multisigAddress;
        String nerveTo = "TNVTdTSPJJMGh7ijUGDqVZyucbeN1z4jqb1ad";
        //String nerveTo = "TNVTdTSPSShZokMfXRo82TP2Kq6Fc2nhMNmF7";
        Long amount = 50000l;
        long feeRate = btcWalletApi.getFeeRate();

        List<UTXOData> utxos = btcWalletApi.getAccountUTXOs(from);
        List<byte[]> opReturns = new ArrayList<>();
        if (true) {
            RechargeData rechargeData = new RechargeData();
            rechargeData.setTo(AddressTool.getAddress(nerveTo));
            rechargeData.setValue(amount);
            byte[] opReturnBytes = rechargeData.serialize();
            int length = opReturnBytes.length;
            System.out.println(String.format("opReturnBytes size: %s", length));
            for (int i = 1; ; i++) {
                if (length > i * 80) {
                    byte[] result = new byte[80];
                    System.arraycopy(opReturnBytes, (i - 1) * 80, result, 0, 80);
                    opReturns.add(result);
                } else {
                    int len = length - (i - 1) * 80;
                    byte[] result = new byte[len];
                    System.arraycopy(opReturnBytes, (i - 1) * 80, result, 0, len);
                    opReturns.add(result);
                    break;
                }
            }
        }

        Transaction tx = BtcUtil.sendLegacyTransactionOffline(fromPri, to, from, amount, utxos, opReturns, feeRate, false);
        System.out.println("txId: " + tx.getTxId());
        btcWalletApi.broadcast(tx);
    }

    @Test
    public void testMakeLegacyTransactionOffline() throws Exception {
        setTestnet();
        //ECKey ecKey = ECKey.fromPublicOnly(HexUtil.decode("0218509f52e47491df3b8331cbb3d2c784512c5ffb58689413a748a0c9fbd77aa5"));
        //Script script = ScriptBuilder.createP2PKHOutputScript(ecKey);
        String from = "mqYkDJboJGMa7XJjrVm3pDxYwB6icxTQrW";
        String to = "mmLahgkWGHQSKszCDcZXPooWoRuYhQPpCF";
        Long amount = 5000l;
        //long feeRate = btcWalletApi.getFeeRate();
        long feeRate = 130;

        List<UTXOData> utxos = btcWalletApi.getAccountUTXOs(from);
        //utxos.get(0).setPreTxHex("0100000001bc3be3e8e4c4367b63c2d6a59b6d1e9052ba0f44ff9fe300a5439607ec7fda4c010000006a47304402204fdec92dde647b4a265da3227c6bcd3efb6b415eb3fe727b71c3fb85cec943c7022033cbf5b0d2824fe0347810b1e7877e561ae30695b72da7e15bab6f9e8a78b4328121039087cc88855da54458e231c50cb76300334abe46553d3c7a76bc8af0852b87f0fdffffff028e0c0300000000001976a9146e08011a2a94059cf1545cc1da29f51d73efee8688acdcf10100000000001976a9143fda920e686292be324b438d6509123ecd8e1e9f88ac00000000");
        List<byte[]> opReturns = new ArrayList<>();
        opReturns.add("test psbt hex".getBytes(StandardCharsets.UTF_8));

        Transaction tx = BtcUtil.makeLegacyTransactionOffline(from, to, amount, utxos, opReturns, feeRate, false, false);
        String txHex = HexUtil.encode(tx.serialize());
        System.out.println("txHex: " + txHex);
        String psbt = btcWalletApi.converterToPsbt(txHex);
        System.out.println(psbt);
    }

    @Test
    public void testMakeSegWitCompatibleTransactionOffline() throws Exception {
        setTestnet();
        //ECKey ecKey = ECKey.fromPublicOnly(HexUtil.decode("02c33b15d12f51122974d6c44aa429ea19efad06431803711b2658a967dfa574e4"));
        //Script script = ScriptBuilder.createP2PKHOutputScript(ecKey);

        String from = "2N9zTP5UCTiHr9wB5y3kMT94h7aK4x6DaTU";
        String to = "mmLahgkWGHQSKszCDcZXPooWoRuYhQPpCF";
        Long amount = 1000l;
        //long feeRate = btcWalletApi.getFeeRate();
        long feeRate = 1;

        List<UTXOData> utxos = btcWalletApi.getAccountUTXOs(from);
        //utxos.get(0).setPreTxHex("0100000001bc3be3e8e4c4367b63c2d6a59b6d1e9052ba0f44ff9fe300a5439607ec7fda4c010000006a47304402204fdec92dde647b4a265da3227c6bcd3efb6b415eb3fe727b71c3fb85cec943c7022033cbf5b0d2824fe0347810b1e7877e561ae30695b72da7e15bab6f9e8a78b4328121039087cc88855da54458e231c50cb76300334abe46553d3c7a76bc8af0852b87f0fdffffff028e0c0300000000001976a9146e08011a2a94059cf1545cc1da29f51d73efee8688acdcf10100000000001976a9143fda920e686292be324b438d6509123ecd8e1e9f88ac00000000");
        List<byte[]> opReturns = new ArrayList<>();
        opReturns.add("test psbt hex".getBytes(StandardCharsets.UTF_8));

        Transaction tx = BtcUtil.makeSegWitCompatibleTransactionOffline(from, to, amount, utxos, opReturns, feeRate, false, false);
        String txHex = HexUtil.encode(tx.serialize());
        System.out.println("txHex: " + txHex);
        String psbt = btcWalletApi.converterToPsbt(txHex);
        System.out.println(psbt);
    }

    @Test
    public void testConvertToPsbt() {
        String rawTx = "0100000001188feea4d9e4a46028f17b21ebc98426f9d2819e625142ee4f407621309a8e560000000000fdffffff0388130000000000001976a9143fda920e686292be324b438d6509123ecd8e1e9f88acfe740200000000001976a9146e08011a2a94059cf1545cc1da29f51d73efee8688ac00000000000000000f6a0d7465737420707362742068657800000000";
        String psbt = btcWalletApi.converterToPsbt(rawTx);
        System.out.println(psbt);
    }

    @Test
    public void testJS() {
        ScriptEngineManager factory = new ScriptEngineManager();
        for (ScriptEngineFactory available : factory.getEngineFactories()) {
            System.out.println(available.getEngineName());
            // 打印脚本具体名称信息
            System.out.println(available.getNames());
        }
    }

    @Test
    public void testTxSizeOfWithdrawalBTC() {
        int utxoSize = 2;
        long txSize = BtcUtil.calcTxSizeWithdrawal(utxoSize, mainnet);
        System.out.println("txSize: " + txSize);
    }

    @Test
    public void testGetMinimumFeeOfWithdrawal() throws Exception {
        String nerveHash = "ffcc90da59b4590e031c32890212c26bffd0aafea35d5c9ed3e2d3ff2573efdd";
        BitCoinFeeInfo feeInfo = BtcUtil.getMinimumFeeOfWithdrawal(nerveHash, mainnet);
        System.out.println(String.format("minimumFee: %s, utxoSize: %s, feeRate: %s", feeInfo.getMinimumFee(), feeInfo.getUtxoSize(), feeInfo.getFeeRate()));
    }

    /*
        满足Nerve底层的手续费要求
     */
    @Test
    public void testAddFeeOfWithdrawalI() {
        String nerveHash = "ffcc90da59b4590e031c32890212c26bffd0aafea35d5c9ed3e2d3ff2573efdd";
        BitCoinFeeInfo feeInfo = BtcUtil.getMinimumFeeOfWithdrawal(nerveHash, mainnet);
        int minimumFee = feeInfo.getMinimumFee();
        //todo 1. minimumFee是BTC资产，需转换成用户支付的手续费资产，再和用户已支付的手续费数量比较
        //  2. 不够则追加，发普通追加手续费交易
    }

    /*
        满足BTC网络的矿工打包的手续费要求
     */
    @Test
    public void testAddFeeOfWithdrawalII() {
        String nerveHash = "ffcc90da59b4590e031c32890212c26bffd0aafea35d5c9ed3e2d3ff2573efdd";
        BitCoinFeeInfo feeInfo = BtcUtil.getMinimumFeeOfWithdrawal(nerveHash, mainnet);
        int utxoSize = feeInfo.getUtxoSize();
        long feeRateOnTx = feeInfo.getFeeRate();
        long feeRateOnNetwork = btcWalletApi.getFeeRate();
        if (feeRateOnNetwork > feeRateOnTx && (feeRateOnNetwork - feeRateOnTx > 2)) {
            long txSize = BtcUtil.calcTxSizeWithdrawal(utxoSize, mainnet);
            long needAddFee = txSize * (feeRateOnNetwork - feeRateOnTx);
            //todo 1. needAddFee是BTC资产，需转换成用户支付的手续费资产
            //  2. 按换算出来的数量追加，发`特殊`的追加手续费交易
            // 参考以下追加 withdrawalAddFeeTest()
        }
    }

    @Test
    public void testHeco() throws Exception {
        String mainEthRpcAddress = "http://heco.nerve.network/";
        Web3j web3j = Web3j.build(new HttpService(mainEthRpcAddress));
        EthBlock send = web3j.ethGetBlockByNumber(new DefaultBlockParameterNumber(6834640), false).send();
        Response.Error error = send.getError();
        if (error != null) {
            System.err.println(JSONUtils.obj2PrettyJson(error));
            return;
        }
        System.out.println(send.getBlock());
    }


    @Test
    public void depositLegacyPsbtTest() throws Exception {
        setTestnet();
        String from = "mmLahgkWGHQSKszCDcZXPooWoRuYhQPpCF";
        String to = multisigAddress;
        String nerveTo = "TNVTdTSPJJMGh7ijUGDqVZyucbeN1z4jqb1ad";
        long amount = 50000l;
        String remark = "order data....";
        long feeRate = btcWalletApi.getFeeRate();// 请求网络
        List<UTXOData> utxos = btcWalletApi.getAccountUTXOs(from);// 请求网络
        Transaction tx = BtcUtil.makeLegacyDepositTransaction(from, to, nerveTo, amount, utxos, feeRate, remark, mainnet);
        List<TransactionInput> inputs = tx.getInputs();
        List<Long> valueList = inputs.stream().map(i -> i.getValue().longValue()).collect(Collectors.toList());
        int i = 0;
        for (Long value : valueList) {
            System.out.println(String.format("Coin Value %s: %s", i++, value));
        }
        String txHex = HexUtil.encode(tx.serialize());
        System.out.println(txHex);
        String psbt = btcWalletApi.converterToPsbt(txHex);// 请求网络
        System.out.println(psbt);
    }

    @Test
    public void testRawTx() throws CommunicationException, BitcoindException {
        String hash = "3dd28753e13ba622cc078a69aa7f2281a3170c5dad5b8fab1d0d69b6a2f7d557";
        String rawTransaction = btcWalletApi.getClient().getRawTransaction(hash);
        System.out.println(rawTransaction);
    }

    @Test
    public void testLegacyTxUTXO() throws Exception {
        String from = "mmLahgkWGHQSKszCDcZXPooWoRuYhQPpCF";
        List<UTXOData> utxos = btcWalletApi.getAccountUTXOs(from);// 请求网络
        // 先调js CalcSpendingUtxosAndFee ，得到 spendingUtxos，再把 spendingUtxos 遍历查preTxHex
        for (UTXOData utxo : utxos) {
            utxo.setPreTxHex(btcWalletApi.getClient().getRawTransaction(utxo.getTxid()));
        }
    }

    /**
     * 组装充值的txData
     */
    @Test
    public void depositDataTest() throws Exception {
        String nerveTo = "TNVTdTSPJJMGh7ijUGDqVZyucbeN1z4jqb1ad";
        long amount = 50000l;
        String remark = "orderId data....";
        List<byte[]> opReturn = BtcUtil.makeDepositOpReturn(nerveTo, amount, remark);

        opReturn.forEach(bytes -> System.out.println(HexUtil.encode(bytes)));
    }

    /**
     * 计算跨出的手续费
     */
    @Test
    public void calcWithdrawFeeTest() {
        setMain();
        RpcResult request = JsonRpcUtil.request("<rpc url>", "getSplitGranularity", List.of(201));// get data from nerve api or nerve ps, call "getSplitGranularity", params: 201
        Long splitGranularity = Long.parseLong(((Map) request.getResult()).get("value").toString());
        List<UTXOData> utxos = btcWalletApi.getAccountUTXOs(multisigAddress);
        long amount = 600000;
        long feeRate = btcWalletApi.getFeeRate();
        long fee = BtcUtil.calcFeeWithdrawal(utxos, amount, feeRate, mainnet, splitGranularity);
        System.out.println("calc fee: " + fee);
    }

    /**
     * 判断UTXO是否被NERVE锁定
     */
    @Test
    public void getUtxoCheckedInfoTest() throws Exception {
        setTestnet();
        List<UTXOData> utxos = btcWalletApi.getAccountUTXOs(multisigAddress);
        RpcResult request = JsonRpcUtil.request(nerveApi + "/jsonrpc", "getUtxoCheckedInfo", List.of(
                201,
                utxos
        ));// get data from nerve api

        System.out.println(JSONUtils.obj2PrettyJson(request));
        Map result = (Map) request.getResult();
        List<Map> list = (List<Map>) result.get("value");
        List<UTXOData> utxoDataList = list.stream().map(map -> JSONUtils.map2pojo(map, UTXOData.class)).collect(Collectors.toList());
        System.out.println(JSONUtils.obj2PrettyJson(utxoDataList));
    }
}
