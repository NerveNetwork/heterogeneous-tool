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

import com.neemre.btcdcli4j.core.BitcoindException;
import com.neemre.btcdcli4j.core.CommunicationException;
import com.neemre.btcdcli4j.core.NodeProperties;
import com.neemre.btcdcli4j.core.client.BtcdClient;
import com.neemre.btcdcli4j.core.domain.*;
import com.neemre.btcdcli4j.core.domain.enums.ScriptTypes;
import network.nerve.heterogeneous.model.UTXOData;
import network.nerve.heterogeneous.utils.*;
import network.nerve.kit.model.dto.JsonRpcRequest;
import org.apache.http.message.BasicHeader;
import org.bitcoinj.core.VerificationException;
import org.bitcoinj.script.ScriptException;
import org.web3j.utils.Numeric;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static network.nerve.heterogeneous.constant.TrxConstant.EMPTY_STRING;


/**
 * @author: PierreLuo
 * @date: 2023/12/26
 */
public class BtcWalletApi {
    private String protocol = "http";
    private String host = "192.168.5.140";
    private String port = "18332";
    private String user = "user";
    private String password = "password";
    private String auth_scheme = "Basic";

    private BtcdClient client;
    private byte[] sessionKey = null;
    private ReentrantLock checkLock = new ReentrantLock();
    private int rpcVersion = -1;
    private boolean reSyncBlock = false;
    private boolean mainnet = false;
    private volatile boolean urlFromThirdPartyForce = false;

    public void init(String rpcAddress, boolean mainnet) {
        this.mainnet = mainnet;
        takeRpcInfo(rpcAddress);
        if (client != null) {
            client.close();
            client = null;
        }
        client = BtcUtil.newInstanceBtcdClient(makeCurrentProperties());
    }

    private synchronized void restartClient() {
        if (client != null) {
            client.close();
            client = null;
        }
        client = BtcUtil.newInstanceBtcdClient(makeCurrentProperties());
    }

    Properties makeCurrentProperties() {
        Properties nodeConfig = new Properties();
        nodeConfig.put(NodeProperties.RPC_PROTOCOL.getKey(), protocol);
        nodeConfig.put(NodeProperties.RPC_HOST.getKey(), host);
        nodeConfig.put(NodeProperties.RPC_PORT.getKey(), port);
        nodeConfig.put(NodeProperties.RPC_USER.getKey(), user);
        nodeConfig.put(NodeProperties.RPC_PASSWORD.getKey(), password);
        nodeConfig.put(NodeProperties.HTTP_AUTH_SCHEME.getKey(), auth_scheme);
        return nodeConfig;
    }

    void takeRpcInfo(String rpc) {
        String[] info = rpc.split(",");
        if (info.length == 1) {
            takeURL(rpc);
        } else if (info.length == 2) {
            throw new RuntimeException("Error Bitcoin Rpc");
        } else {
            takeURL(info[0]);
            user = info[1];
            password = info[2];
        }
        if (host.endsWith("/")) {
            host = host.substring(0, host.length() - 1);
        }
    }

    void takeURL(String url) {
        if (url.contains("https")) {
            protocol = "https";
            host = url.replace("https://", "");
            port = "443";
        } else {
            protocol = "http";
            host = url.replace("http://", "");
            if (host.contains(":")) {
                String[] split = host.split(":");
                host = split[0];
                port = split[1];
            } else {
                port = "80";
            }
        }
    }


    public void changeApi(String rpc, boolean mainnet) {
        if (client != null) {
            client.close();
        }
        // switch api
        init(rpc, mainnet);
    }


    public synchronized BigDecimal getBalance(String address) {
        try {
            TimeUnit.MILLISECONDS.sleep(500);
            if (!mainnet) {
                return takeBalanceFromMempool(address, false);
            } else {
                int[] requestIndexes = new int[]{0, 1, 2};
                BtcUtil.shuffleArray(requestIndexes);
                for (int index : requestIndexes) {
                    BigDecimal balance = requestBalance(address, index);
                    if (balance != null) {
                        return balance;
                    }
                }
                return takeBalanceFromOKX(address);
            }
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    BigDecimal requestBalance(String address, int index) throws Exception {
        switch (index) {
            case 0:
                return takeBalanceFromMempool(address, true);
            case 1:
                return takeBalanceFromBlockchainInfo(address);
            case 2:
                return takeBalanceFromBlockcypher(address);
            default:
                return null;
        }
    }

    BigDecimal takeBalanceFromOKX(String address) throws Exception {
        String url = "https://www.oklink.com/api/v5/explorer/address/address-summary?chainShortName=btc&address=%s";
        List<BasicHeader> headers = new ArrayList<>();
        headers.add(new BasicHeader("Ok-Access-Key", "33bed3e2-1605-467f-a6d0-53888df39b62"));
        String s = HttpClientUtil.get(String.format(url, address), headers);
        Map<String, Object> map = JSONUtils.json2map(s);
        List<Map> data = (List<Map>) map.get("data");
        if (data == null || data.isEmpty()) {
            return BigDecimal.ZERO;
        }
        Map dataMap = data.get(0);
        return new BigDecimal(dataMap.get("balance").toString()).movePointRight(8);
    }

    //public static void main(String[] args) throws Exception {
    //    BtcWalletApi api = new BtcWalletApi();
    //    api.htgContext = new BtcContext();
    //    System.out.println(api.takeBalanceFromOKX("1EzwoHtiXB4iFwedPr49iywjZn2nnekhoj"));
    //    System.out.println(api.takeBalanceFromBlockcypher("1EzwoHtiXB4iFwedPr49iywjZn2nnekhoj"));
    //    System.out.println(api.takeBalanceFromBlockchainInfo("1EzwoHtiXB4iFwedPr49iywjZn2nnekhoj"));
    //    System.out.println(api.takeBalanceFromMempool("1EzwoHtiXB4iFwedPr49iywjZn2nnekhoj", true));
    //    System.out.println(api.takeBalanceFromMempool("mpkGu7LBSLf799X91wAZhSyT6hAb4XiTLG", false));
    //}

    BigDecimal takeBalanceFromBlockcypher(String address) throws Exception {
        String url = "https://api.blockcypher.com/v1/btc/main/addrs/%s/balance";
        String s = HttpClientUtil.get(String.format(url, address));
        Map<String, Object> map = JSONUtils.json2map(s);
        if (map.get("error") != null) {
            return null;
        }
        return new BigDecimal(map.get("balance").toString());
    }

    BigDecimal takeBalanceFromBlockchainInfo(String address) throws Exception {
        String url = "https://blockchain.info/q/addressbalance/%s";
        String s = HttpClientUtil.get(String.format(url, address));
        if (s.contains("error")) {
            return null;
        }
        return new BigDecimal(s.trim());
    }

    BigDecimal takeBalanceFromMempool(String address, boolean mainnet) throws Exception {
        String url = "https://mempool.space/testnet/api/address/%s";
        if (mainnet) {
            url = "https://mempool.space/api/address/%s";
        }
        String s = HttpClientUtil.get(String.format(url, address));
        if (s.contains("Invalid")) {
            return null;
        }
        Map<String, Object> map = JSONUtils.json2map(s);
        Map statsMap = (Map) map.get("chain_stats");
        return new BigDecimal(statsMap.get("funded_txo_sum").toString()).subtract(new BigDecimal(statsMap.get("spent_txo_sum").toString()));
    }

    public RawTransaction getTransactionByHash(String txHash) {
        try {
            RawTransaction tx = (RawTransaction) client.getRawTransaction(txHash, 1);
            return tx;
        } catch (BitcoindException e) {
            throw new RuntimeException(e);
        } catch (CommunicationException e) {
            restartClient();
            throw new RuntimeException(e);
        }
    }

    public String getOpReturnHex(RawTransaction tx) {
        if (tx == null) return EMPTY_STRING;
        List<RawOutput> vOut = tx.getVOut();
        if (HtgCommonTools.isEmptyList(vOut)) return EMPTY_STRING;
        String result = EMPTY_STRING;
        for (RawOutput output : vOut) {
            if (output.getValue().compareTo(BigDecimal.ZERO) > 0)
                continue;
            PubKeyScript script = output.getScriptPubKey();
            if (script.getType() != ScriptTypes.NULL_DATA)
                continue;
            String asm = script.getAsm();
            if (!asm.startsWith("OP_RETURN"))
                continue;
            String content = asm.replace("OP_RETURN ", "");
            result += content;
        }
        return result;
    }

    public Block getBestBlock() {
        try {
            return (Block) client.getBlock(client.getBestBlockHash(), true);
        } catch (BitcoindException e) {
            throw new RuntimeException(e);
        } catch (CommunicationException e) {
            restartClient();
            throw new RuntimeException(e);
        }
    }

    public String getBestBlockHash() {
        try {
            return client.getBestBlockHash();
        } catch (BitcoindException e) {
            throw new RuntimeException(e);
        } catch (CommunicationException e) {
            restartClient();
            throw new RuntimeException(e);
        }
    }

    public long getBestBlockHeight() {
        try {
            return client.getBlockCount();
        } catch (BitcoindException e) {
            throw new RuntimeException(e);
        } catch (CommunicationException e) {
            restartClient();
            throw new RuntimeException(e);
        }
    }

    public BlockInfo getBlockByHeight(long height) {
        try {
            return (BlockInfo) client.getBlockInfo(client.getBlockHash((int) height));
        } catch (BitcoindException e) {
            throw new RuntimeException(e);
        } catch (CommunicationException e) {
            restartClient();
            throw new RuntimeException(e);
        }
    }

    public BlockHeader getBlockHeaderByHeight(long height) {
        try {
            return (BlockHeader) client.getBlockHeader(client.getBlockHash((int) height));
        } catch (BitcoindException e) {
            throw new RuntimeException(e);
        } catch (CommunicationException e) {
            restartClient();
            throw new RuntimeException(e);
        }
    }

    public BlockHeader getBlockHeaderByHash(String hash) {
        try {
            return (BlockHeader) client.getBlockHeader(hash);
        } catch (BitcoindException e) {
            throw new RuntimeException(e);
        } catch (CommunicationException e) {
            restartClient();
            throw new RuntimeException(e);
        }
    }

    public String converterToPsbt(String rawTxHex) {
        String url = String.format("%s://%s:%s", protocol, host, port);
        String method = "converttopsbt";
        String credentials = org.apache.commons.codec.binary.Base64.encodeBase64String((user + ":" + password).getBytes());
        RpcResult rpcResult = JsonRpcUtil.request(url, method, List.of(rawTxHex), List.of(new BasicHeader("Authorization", "Basic " + credentials)));
        String base64Str = (String) rpcResult.getResult();
        if (StringUtils.isBlank(base64Str)) {
            return null;
        }
        return HexUtil.encode(org.apache.commons.codec.binary.Base64.decodeBase64(base64Str));
    }

    public long getFeeRate() {
        String url;
        if (mainnet) {
            url = "https://mempool.space/api/v1/fees/recommended";
        } else {
            url = "https://mempool.space/testnet/api/v1/fees/recommended";
        }
        try {
            String data = HttpClientUtil.get(String.format(url));
            Map<String, Object> map = JSONUtils.json2map(data);
            Object object = map.get("fastestFee");
            if (object == null) {
                throw new RuntimeException("error request");
            }
            return Long.parseLong(object.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public List<RawTransaction> getTxInfoList(BlockInfo blockInfo) {
        return blockInfo.getTx();
    }

    public List<UTXOData> getAccountUTXOs(String address) {
        if (mainnet) {
            String url = "http://api.v2.nabox.io/nabox-api/btc/utxo";
            Map<String, Object> param = new HashMap<>();
            param.put("language", "CHS");
            param.put("chainId", 0);
            param.put("address", address);
            param.put("coinAmount", 0);
            param.put("serviceCharge", 0);
            param.put("utxoType", 1);
            try {
                String data = HttpClientUtil.post(url, param);
                Map<String, Object> map = JSONUtils.json2map(data);
                Object code = map.get("code");
                if (code == null) {
                    return Collections.EMPTY_LIST;
                }
                int codeValue = Integer.parseInt(code.toString());
                if (codeValue != 1000) {
                    return Collections.EMPTY_LIST;
                }
                Map dataMap = (Map) map.get("data");
                List<Map> utxoList = (List<Map>) dataMap.get("utxo");
                if (utxoList == null || utxoList.isEmpty()) {
                    return Collections.EMPTY_LIST;
                }
                List<UTXOData> resultList = new ArrayList<>();
                for (Map utxo : utxoList) {
                    boolean isSpent = (boolean) utxo.get("isSpent");
                    if (isSpent) {
                        continue;
                    }
                    resultList.add(new UTXOData(
                            utxo.get("txid").toString(),
                            Integer.parseInt(utxo.get("vout").toString()),
                            new BigInteger(utxo.get("satoshi").toString())
                    ));
                }
                return resultList;
            } catch (Exception e) {
                return Collections.EMPTY_LIST;
            }
        } else {
            String url = "https://mempool.space/testnet/api/address/%s/utxo";
            try {
                String data = HttpClientUtil.get(String.format(url, address));
                List<Map> list = JSONUtils.json2list(data, Map.class);
                if (list.isEmpty()) {
                    return Collections.EMPTY_LIST;
                }
                List<UTXOData> resultList = new ArrayList<>();
                for (Map utxo : list) {
                    Map statusInfo = (Map) utxo.get("status");
                    boolean confirmed = (boolean) statusInfo.get("confirmed");
                    if (!confirmed) {
                        continue;
                    }
                    resultList.add(new UTXOData(
                            utxo.get("txid").toString(),
                            Integer.parseInt(utxo.get("vout").toString()),
                            new BigInteger(utxo.get("value").toString())
                    ));
                }
                return resultList;
            } catch (Exception e) {
                return Collections.EMPTY_LIST;
            }
        }
    }

    public String broadcast(org.bitcoinj.core.Transaction _tx) {
        try {
            String hash = client.sendRawTransaction(Numeric.toHexStringNoPrefix(_tx.serialize()));
            return hash;
        } catch (ScriptException ex) {
            throw new RuntimeException(ex);
        } catch (VerificationException ex) {
            throw new RuntimeException(ex);
        } catch (CommunicationException e) {
            restartClient();
            throw new RuntimeException(e);
        } catch (BitcoindException e) {
            throw new RuntimeException(e);
        }
    }

    public BtcdClient getClient() {
        return client;
    }
}
