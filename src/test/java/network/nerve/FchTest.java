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
import com.fasterxml.jackson.core.JsonProcessingException;
import fchClass.Cash;
import network.nerve.heterogeneous.core.FchWalletApi;
import network.nerve.heterogeneous.model.BitCoinFeeInfo;
import network.nerve.heterogeneous.model.UTXOData;
import network.nerve.heterogeneous.utils.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: PierreLuo
 * @date: 2024/5/27
 */
public class FchTest {

    private FchWalletApi fchWalletApi;
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
        fchWalletApi = new FchWalletApi();
        fchWalletApi.init("https://cid.cash/APIP");
        multisigAddress = "3NLqrstiNFrLie6g9H3jMkAe2g9Sp8b8dR";
        nerveApi = "http://beta.api.nerve.network";
        fchWalletApi.changeApi("https://cid.cash/APIP,b3928a1dc649b38fb1f4b21b0afc3def668bad9f335c99db4fc0ec54cac1e655");
    }

    void setMain() {
        mainnet = true;
        fchWalletApi = new FchWalletApi();
        fchWalletApi.init("https://cid.cash/APIP");
        multisigAddress = "35nAXxa7CtTk1dRZGYga3cBfn7mHRB4qS8";
        nerveApi = "https://api.nerve.network";
    }


    @Test
    public void feeRateTest() throws Exception {
        System.out.println(fchWalletApi.getFeeRate());
    }


    @Test
    public void testGetMinimumFeeOfWithdrawal() throws Exception {
        String nerveHash = "325c5b9722c94e937abd556976309afa428459a6ccb8eb24f1fbe056988f5cc8";
        BitCoinFeeInfo feeInfo = FchUtil.getMinimumFeeOfWithdrawal(nerveHash, mainnet);
        System.out.println(String.format("minimumFee: %s, utxoSize: %s, feeRate: %s", feeInfo.getMinimumFee(), feeInfo.getUtxoSize(), feeInfo.getFeeRate()));
    }

    @Test
    public void getSplitGranularityTest() {
        setTestnet();
        RpcResult request = JsonRpcUtil.request(nerveApi + "/jsonrpc", "getSplitGranularity", List.of(202));// get data from nerve api or nerve ps, call "getSplitGranularity", params: 202
        Long splitGranularity = Long.parseLong(((Map) request.getResult()).get("value").toString());
        System.out.println(splitGranularity);
    }

    @Test
    public void testGetUTXO() throws Exception {
        String from = "FBejsS6cJaBrAwPcMjFJYH7iy6Krh2fkRD";
        List<Cash> utxos = fchWalletApi.getAccountUTXOs(from);// 请求网络
        System.out.println(JSONUtils.obj2PrettyJson(utxos));
    }

    /**
     * 组装充值的txData
     */
    @Test
    public void depositDataTest() throws Exception {
        String nerveTo = "TNVTdTSPJJMGh7ijUGDqVZyucbeN1z4jqb1ad";
        long amount = 50000l;
        String remark = "orderId data....";
        String opReturn = FchUtil.makeDepositOpReturn(nerveTo, amount, remark);
        System.out.println(opReturn);
    }

    /**
     * 计算跨出的手续费
     */
    @Test
    public void calcWithdrawFeeTest() {
        setTestnet();
        RpcResult request = JsonRpcUtil.request(nerveApi + "/jsonrpc", "getSplitGranularity", List.of(202));// get data from nerve api or nerve ps, call "getSplitGranularity", params: 202
        Long splitGranularity = Long.parseLong(((Map) request.getResult()).get("value").toString());
        List<Cash> utxos = fchWalletApi.getAccountUTXOs(multisigAddress);
        long amount = 600000;
        long feeRate = fchWalletApi.getFeeRate();
        long fee = FchUtil.calcFeeWithdrawal(utxos, amount, feeRate, mainnet, splitGranularity);
        System.out.println("calc fee: " + fee);
    }

    /**
     * 判断UTXO是否被NERVE锁定
     */
    @Test
    public void getUtxoCheckedInfoTest() throws Exception {
        setTestnet();
        List<Cash> utxos = fchWalletApi.getAccountUTXOs(multisigAddress);
        RpcResult request = JsonRpcUtil.request(nerveApi + "/jsonrpc", "getUtxoCheckedInfo", List.of(
                202,
                utxos.stream().map(cash -> FchUtil.converterCashToUTXOData(cash)).collect(Collectors.toList())
        ));// get data from nerve api

        System.out.println(JSONUtils.obj2PrettyJson(request));
        Map result = (Map) request.getResult();
        List<Map> list = (List<Map>) result.get("value");
        List<UTXOData> utxoDataList = list.stream().map(map -> JSONUtils.map2pojo(map, UTXOData.class)).collect(Collectors.toList());
        System.out.println(JSONUtils.obj2PrettyJson(utxoDataList));
    }
}
