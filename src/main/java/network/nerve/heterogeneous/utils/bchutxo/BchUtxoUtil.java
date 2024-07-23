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
package network.nerve.heterogeneous.utils.bchutxo;

import com.neemre.btcdcli4j.core.domain.RawInput;
import com.neemre.btcdcli4j.core.domain.SignatureScript;
import network.nerve.base.basic.AddressTool;
import network.nerve.heterogeneous.model.BitCoinFeeInfo;
import network.nerve.heterogeneous.model.RechargeData;
import network.nerve.heterogeneous.model.UTXOData;
import network.nerve.heterogeneous.utils.*;
import network.nerve.heterogeneous.utils.bchutxo.addr.CashAddressFactory;
import network.nerve.heterogeneous.utils.bchutxo.addr.MainNetParamsForAddr;
import network.nerve.heterogeneous.utils.bchutxo.addr.TestNet4ParamsForAddr;
import org.bitcoinj.base.LegacyAddress;
import org.bitcoinj.base.VarInt;
import org.bitcoinj.crypto.ECKey;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;
import org.bitcoinj.script.ScriptChunk;
import org.bitcoinj.script.ScriptPattern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static network.nerve.heterogeneous.constant.TrxConstant.EMPTY_STRING;
import static org.bitcoinj.script.ScriptOpCodes.OP_1;
import static org.bitcoinj.script.ScriptOpCodes.OP_CHECKMULTISIG;

/**
 * @author: PierreLuo
 * @date: 2024/7/16
 */
public class BchUtxoUtil {

    public static String getBchAddress(String pubKey, boolean mainnet) {
        String btcLegacyAddress = BtcUtil.getBtcLegacyAddress(HexUtil.decode(pubKey), mainnet);
        return CashAddressFactory.create().getFromBase58(mainnet ? MainNetParamsForAddr.get() : TestNet4ParamsForAddr.get(), btcLegacyAddress).toString();
    }

    public static String takeMultiSignAddressWithP2SH(RawInput input, boolean mainnet) {
        SignatureScript scriptSig = input.getScriptSig();
        if (scriptSig == null || StringUtils.isBlank(scriptSig.getHex())) {
            return EMPTY_STRING;
        }
        String hex = scriptSig.getHex();
        Script script = Script.parse(HexUtil.decode(hex));
        List<ScriptChunk> chunks = script.chunks();
        ScriptChunk scriptChunk = chunks.get(chunks.size() - 1);
        byte[] stBytes = scriptChunk.data;
        if (stBytes.length < 105
                || stBytes[0] < OP_1 + 1
                || stBytes[stBytes.length - 2] < OP_1 + 2
                || (0xff & stBytes[stBytes.length - 1]) != OP_CHECKMULTISIG
        ) {
            return EMPTY_STRING;
        }

        Script redeemScript = Script.parse(scriptChunk.data);
        Script scriptPubKey = ScriptBuilder.createP2SHOutputScript(redeemScript);
        String multiSigAddress = LegacyAddress.fromScriptHash(mainnet ? MainNetParams.get() : TestNet3Params.get(), ScriptPattern.extractHashFromP2SH(scriptPubKey)).toString();
        String newMultiSigAddr = CashAddressFactory.create().getFromBase58(mainnet ? MainNetParamsForAddr.get() : TestNet4ParamsForAddr.get(), multiSigAddress).toString();
        return newMultiSigAddr;
    }

    public static String multiAddrByECKey(List<ECKey> pubKeys, int m, boolean mainnet) {
        String base58 = BtcUtil.makeMultiAddr(pubKeys, m, mainnet);
        return CashAddressFactory.create().getFromBase58(mainnet ? MainNetParamsForAddr.get() : TestNet4ParamsForAddr.get(), base58).toString();
    }


    public static String multiAddr(List<byte[]> pubKeyList, int m, boolean mainnet) {
        return multiAddrByECKey(pubKeyList.stream().map(p -> ECKey.fromPublicOnly(p)).collect(Collectors.toList()), m, mainnet);
    }

    public static long calcFeeSize(int inputNum, int outputNum, int opReturnBytesLen) {
        long priceInSatoshi = 1L;
        long length = 0L;
        if (opReturnBytesLen == 0) {
            length = 10L + 141L * (long)inputNum + 34L * (long)(outputNum + 1);
        } else {
            length = 10L + 141L * (long)inputNum + 34L * (long)(outputNum + 1) + (long)(opReturnBytesLen + VarInt.sizeOf((long)opReturnBytesLen) + 1 + VarInt.sizeOf((long)(opReturnBytesLen + VarInt.sizeOf((long)opReturnBytesLen) + 1)) + 8);
        }

        return priceInSatoshi * length;
    }

    public static Object[] calcFeeAndUTXO(List<UTXOData> utxos, long amount, long feeRate, int opReturnBytesLen) {
        boolean enoughUTXO = false;
        long _fee = 0, total = 0;
        List<UTXOData> resultList = new ArrayList<>();
        for (int i = 0; i < utxos.size(); i++) {
            UTXOData utxo = utxos.get(i);
            total = total + utxo.getAmount().longValue();
            resultList.add(utxo);
            _fee = calcFeeSize(resultList.size(), 1, opReturnBytesLen) * feeRate;
            long totalSpend = amount + _fee;
            if (total >= totalSpend) {
                enoughUTXO = true;
                break;
            }
        }
        if (!enoughUTXO) {
            throw new RuntimeException("not enough utxo, may need more: " + (amount + _fee - total));
        }
        return new Object[]{_fee, resultList};
    }

    public static int getByzantineCount(int count) {
        int directorCount = count;
        int ByzantineRateCount = directorCount * 66;
        int minPassCount = ByzantineRateCount / 100;
        if (ByzantineRateCount % 100 > 0) {
            minPassCount++;
        }
        return minPassCount;
    }

    public static long calcFeeMultiSign(int inputNum, int outputNum, int opReturnBytesLen, int m, int n) {

        long redeemScriptLength = 1 + (n * (33L + 1)) + 1 + 1;
        long redeemScriptVarInt = VarInt.sizeOf(redeemScriptLength);
        long scriptLength = 2 + (m * (1 + 1 + 69L + 1 + 1)) + redeemScriptVarInt + redeemScriptLength;
        long scriptVarInt = VarInt.sizeOf(scriptLength);
        long inputLength = 40 + scriptVarInt + scriptLength;

        int totalOpReturnLen = calcOpReturnLen(opReturnBytesLen);
        long length = 10 + inputLength * inputNum + (long) 43 * (outputNum + 1) + totalOpReturnLen;
        return length;
    }

    private static int calcOpReturnLen(int opReturnBytesLen) {
        int dataLen;
        if (opReturnBytesLen < 76) {
            dataLen = opReturnBytesLen + 1;
        } else if (opReturnBytesLen < 256) {
            dataLen = opReturnBytesLen + 2;
        } else dataLen = opReturnBytesLen + 3;
        int scriptLen;
        scriptLen = (dataLen + 1) + VarInt.sizeOf(dataLen + 1);
        int amountLen = 8;
        return scriptLen + amountLen;
    }

    public static int calcSplitNumP2SH(long fromTotal, long transfer, long feeRate, long splitGranularity, int inputNum, int opReturnBytesLen, int m, int n) {
        // numerator and denominator
        long numerator = fromTotal - transfer - calcFeeMultiSign(inputNum, 1, opReturnBytesLen, m, n) * feeRate + 43 * feeRate;
        long denominator = 43 * feeRate + splitGranularity;
        int splitNum = (int) (numerator / denominator);
        if (splitNum == 0 && numerator % denominator > 0) {
            splitNum = 1;
        }
        return splitNum;
    }

    public static final long MIN_SPLIT_GRANULARITY = 100000;// 0.001
    public static long calcFeeMultiSignWithSplitGranularity(long fromTotal, long transfer, long feeRate, Long splitGranularity, int inputNum, int opReturnBytesLen, int m, int n) {
        long feeSize;
        if (splitGranularity != null && splitGranularity > 0) {
            if (splitGranularity < MIN_SPLIT_GRANULARITY) {
                throw new RuntimeException("error splitGranularity: " + splitGranularity);
            }
            int splitNum = calcSplitNumP2SH(fromTotal, transfer, feeRate, splitGranularity, inputNum, opReturnBytesLen, m, n);
            feeSize = calcFeeMultiSign(inputNum, splitNum, opReturnBytesLen, m, n);
        } else {
            feeSize = calcFeeMultiSign(inputNum, 1, opReturnBytesLen, m, n);
        }
        return feeSize;
    }

    public static BitCoinFeeInfo getMinimumFeeOfWithdrawal(String nerveTxHash, boolean mainnet) {
        String rpc;
        if (mainnet) {
            rpc = "https://api.nerve.network/jsonrpc";
        } else {
            rpc = "http://beta.api.nerve.network/jsonrpc";
        }
        RpcResult request = JsonRpcUtil.request(rpc, "getMinimumFeeOfWithdrawal", List.of(203, nerveTxHash));
        Map map = (Map) request.getResult();
        Integer minimumFee = Integer.parseInt(map.get("minimumFee").toString());
        Integer utxoSize = Integer.parseInt(map.get("utxoSize").toString());
        Long feeRate = Long.parseLong(map.get("feeRate").toString());
        return new BitCoinFeeInfo(minimumFee, utxoSize, feeRate);
    }

    public static byte[] makeDepositOpReturn(
            String nerveTo,
            long amount,
            String remark) throws Exception {
        RechargeData rechargeData = new RechargeData();
        rechargeData.setTo(AddressTool.getAddress(nerveTo));
        rechargeData.setValue(amount);
        rechargeData.setExtend0(remark);
        return rechargeData.serialize();
    }

    public static long calcFeeWithdrawal(List<UTXOData> utxos, long amount, long feeRate, boolean mainnet, Long splitGranularity) {
        int opReturnSize = 32;
        int m, n;
        if (mainnet) {
            m = 10;
            n = 15;
        } else {
            m = 2;
            n = 3;
        }
        long fee = 0;
        List<UTXOData> usingUtxos = new ArrayList<>();
        long totalMoney = 0;
        long totalSpend = 0;
        boolean enoughUTXO = false;
        Collections.sort(utxos, BtcUtil.BITCOIN_SYS_COMPARATOR);
        for (UTXOData utxo : utxos) {
            usingUtxos.add(utxo);
            totalMoney += utxo.getAmount().longValue();
            long feeSize = calcFeeMultiSignWithSplitGranularity(totalMoney, amount, feeRate, splitGranularity, usingUtxos.size(), opReturnSize, m, n);
            fee = feeSize * feeRate;
            totalSpend = amount + fee;
            if (totalMoney >= totalSpend) {
                break;
            }
        }
        if (totalMoney < totalSpend) {
            throw new RuntimeException("not enough utxo, may need more: " + (totalSpend - totalMoney));
        }
        return fee;
    }
}
