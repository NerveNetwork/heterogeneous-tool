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


import network.nerve.heterogeneous.model.UTXOData;
import network.nerve.heterogeneous.utils.HttpClientUtil;
import network.nerve.heterogeneous.utils.JSONUtils;
import network.nerve.heterogeneous.utils.JsonRpcUtil;
import network.nerve.heterogeneous.utils.RpcResult;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * @author: PierreLuo
 * @date: 2024/7/16
 */
public class BchUtxoWalletApi extends BtcWalletApi {

    @Override
    public BigDecimal getBalance(String address) {
        return BigDecimal.ZERO;
    }

    @Override
    public long getFeeRate() {
        return 1;
    }

    @Override
    public List<UTXOData> getAccountUTXOs(String address) {
        if (mainnet) {
            String url = "http://api.v2.nabox.io/nabox-api/bch/utxo";
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
            String url = "http://bchutxo.nerve.network/jsonrpc";
            try {
                RpcResult request = JsonRpcUtil.request(url, "getAddressUTXO", List.of(address));
                List<Map> list = (List<Map>) request.getResult();
                if (list == null || list.isEmpty()) {
                    return Collections.EMPTY_LIST;
                }
                List<UTXOData> resultList = new ArrayList<>();
                for (Map utxo : list) {
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
}
