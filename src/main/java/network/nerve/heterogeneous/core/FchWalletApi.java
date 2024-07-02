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
package network.nerve.heterogeneous.core;

import apipClass.BlockInfo;
import apipClass.ResponseBody;
import apipClass.TxInfo;
import apipClient.ApipClient;
import apipClient.ApipDataGetter;
import apipClient.BlockchainAPIs;
import apipClient.FreeGetAPIs;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fchClass.Cash;
import fchClass.OpReturn;
import fchClass.P2SH;
import network.nerve.core.exception.NulsException;
import network.nerve.heterogeneous.utils.HexUtil;
import network.nerve.heterogeneous.utils.HtgCommonTools;
import txTools.FchTool;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: PierreLuo
 * @date: 2023/12/26
 */
public class FchWalletApi {
    private String rpc = "https://cid.cash/APIP";
    private String via = "FBejsS6cJaBrAwPcMjFJYH7iy6Krh2fkRD";
    private byte[] sessionKey = HexUtil.decode("47a75483f8800d0c36f6e11c7502b7b6f7522713d800790d665b89736f776cbc");
    private ReentrantLock checkLock = new ReentrantLock();
    private int rpcVersion = -1;
    private boolean reSyncBlock = false;
    private volatile boolean urlFromThirdPartyForce = false;


    public void init(String rpcAddress) {
        this.rpc = rpcAddress;
    }

    public void changeApi(String rpc) throws NulsException {
        // switchapi
        String[] info = rpc.split(",");
        if (info.length == 1) {
            this.rpc = rpc;
        } else if (info.length == 2) {
            this.rpc = info[0];
            this.sessionKey = HexUtil.decode(info[1]);
        } else {
            this.rpc = info[0];
            this.via = info[1];
            this.sessionKey = HexUtil.decode(info[2]);
        }
    }

    public BigDecimal getBalance(String address) {
        ApipClient apipClient = FreeGetAPIs.getFidCid(rpc, address);
        Map data = (Map) checkBestHeight(apipClient.getResponseBody());
        if (data == null) {
            return BigDecimal.ZERO;
        }
        Object balance = data.get("balance");
        if (balance == null) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(balance.toString());
    }

    public BigDecimal getPrice() {
        ApipClient apipClient = FreeGetAPIs.getPrices(rpc);
        Map data = (Map) checkBestHeight(apipClient.getResponseBody());
        if (data == null) {
            return BigDecimal.ZERO;
        }
        Object balance = data.get("fch/doge");
        if (balance == null) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(balance.toString());
    }

    ThreadLocal<Long> bestHeightLocal = new ThreadLocal<>();

    public Long getBestHeightForCurrentThread() {
        return bestHeightLocal.get();
    }

    private Object checkBestHeight(ResponseBody responseBody) {
        if (responseBody.getCode() != 0) {
            throw new RuntimeException(String.format("%s, detail: %s", responseBody.getMessage(), responseBody.getData()));
        }
        bestHeightLocal.set(responseBody.getBestHeight());
        return responseBody.getData();
    }

    private Object checkApiBalance(ResponseBody responseBody) {
        checkBestHeight(responseBody);
        return responseBody.getData();
    }

    public TxInfo getTransactionByHash(String txHash) {
        ApipClient client = BlockchainAPIs.txByIdsPost(rpc, new String[]{txHash}, via, sessionKey);
        Object data = checkApiBalance(client.getResponseBody());
        if (data == null) {
            return null;
        }
        List<TxInfo> txInfoList = ApipDataGetter.getTxInfoList(data);
        if (txInfoList == null || txInfoList.isEmpty()) {
            return null;
        }
        return txInfoList.get(0);
    }

    public String getOpReturnInfo(String txHash) {
        ApipClient client = BlockchainAPIs.opReturnByIdsPost(rpc, new String[]{txHash}, via, sessionKey);
        Object data = checkApiBalance(client.getResponseBody());
        if (data == null) {
            return null;
        }
        Map<String, OpReturn> opReturnMap = ApipDataGetter.getOpReturnMap(data);
        if (opReturnMap == null || opReturnMap.isEmpty()) {
            return null;
        }
        OpReturn opReturn = opReturnMap.get(txHash);
        return opReturn == null ? null : opReturn.getOpReturn();
    }

    public BlockInfo getBestBlock() {
        ApipClient apipClient = FreeGetAPIs.getBestBlock(rpc);
        Object data = apipClient.getResponseBody().getData();
        if (data == null) {
            return null;
        }
        Type t = (new TypeToken<BlockInfo>() {
        }).getType();
        Gson gson = new Gson();
        return (BlockInfo) gson.fromJson(gson.toJson(data), t);
    }

    public BlockInfo getBlockByHeight(long height) {
        String heightKey = height + "";
        ApipClient client = BlockchainAPIs.blockByHeightsPost(rpc, new String[]{heightKey}, via, sessionKey);
        Object data = checkApiBalance(client.getResponseBody());
        if (data == null) {
            return null;
        }
        Map<String, BlockInfo> blockInfoMap = ApipDataGetter.getBlockInfoMap(data);
        if (blockInfoMap == null) {
            return null;
        }
        return blockInfoMap.get(heightKey);
    }

    public List<TxInfo> getTxInfoList(List<String> txHashes) {
        String[] txHashArr = new String[txHashes.size()];
        txHashes.toArray(txHashArr);
        ApipClient client = BlockchainAPIs.txByIdsPost(rpc, txHashArr, via, sessionKey);
        Object data = checkApiBalance(client.getResponseBody());
        if (data == null) {
            return null;
        }
        return ApipDataGetter.getTxInfoList(data);
    }

    public List<Cash> getAccountUTXOs(String address) {
        ApipClient apipClient = FreeGetAPIs.getCashes(rpc, address, 0);
        Object data = checkBestHeight(apipClient.getResponseBody());
        if (data == null) {
            return null;
        }
        return ApipDataGetter.getCashList(data);
    }

    public Map<String, Cash> getUTXOsByIds(String[] cashIds) {
        ApipClient client = BlockchainAPIs.cashByIdsPost(rpc, cashIds, via, sessionKey);
        Object data = checkApiBalance(client.getResponseBody());
        if (data == null) {
            return null;
        }
        return ApipDataGetter.getCashMap(data);
    }

    public String createMultisigAddress(List<byte[]> pubs, int minSign) {
        if (HtgCommonTools.isEmptyList(pubs)) {
            return null;
        }
        if (pubs.size() < minSign) {
            return null;
        }
        P2SH p2sh = FchTool.genMultiP2sh(pubs, minSign);
        return p2sh.getFid();
    }

    public String broadcast(String txHex) {
        ApipClient apipClient = FreeGetAPIs.broadcast(rpc, txHex);
        Object data = checkBestHeight(apipClient.getResponseBody());
        if (data == null) {
            return null;
        }
        return data.toString();
    }

    public long getFeeRate() {
        return 1;
    }
}
