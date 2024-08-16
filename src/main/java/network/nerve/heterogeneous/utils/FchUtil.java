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
package network.nerve.heterogeneous.utils;

import fchClass.Cash;
import fchClass.P2SH;
import network.nerve.base.basic.AddressTool;
import network.nerve.heterogeneous.constant.Constant;
import network.nerve.heterogeneous.model.BitCoinFeeInfo;
import network.nerve.heterogeneous.model.RechargeData;
import network.nerve.heterogeneous.model.UTXOData;
import org.bitcoinj.base.VarInt;
import org.bitcoinj.crypto.ECKey;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;

import java.math.BigInteger;
import java.util.*;

/**
 * @author: PierreLuo
 * @date: 2023/12/26
 */
public class FchUtil {

    public static P2SH genMultiP2sh(List<byte[]> pubKeyList, int m) {
        List<ECKey> keys = new ArrayList();
        Iterator var3 = pubKeyList.iterator();

        byte[] redeemScriptBytes;
        while (var3.hasNext()) {
            redeemScriptBytes = (byte[]) var3.next();
            ECKey ecKey = ECKey.fromPublicOnly(redeemScriptBytes);
            keys.add(ecKey);
        }

        keys = new ArrayList<>(keys);
        Collections.sort(keys, ECKey.PUBKEY_COMPARATOR);

        Script multiSigScript = ScriptBuilder.createMultiSigOutputScript(m, keys);
        redeemScriptBytes = multiSigScript.getProgram();

        try {
            P2SH p2sh = P2SH.parseP2shRedeemScript(javaTools.HexUtil.encode(redeemScriptBytes));
            return p2sh;
        } catch (Exception var7) {
            var7.printStackTrace();
            return null;
        }
    }

    private static long calcFeeMultiSign(int inputNum, int outputNum, int opReturnBytesLen, int m, int n) {

        long op_mLen = 1;
        long op_nLen = 1;
        long pubKeyLen = 33;
        long pubKeyLenLen = 1;
        long op_checkmultisigLen = 1;

        long redeemScriptLength = op_mLen + (n * (pubKeyLenLen + pubKeyLen)) + op_nLen + op_checkmultisigLen; //105 n=3
        long redeemScriptVarInt = VarInt.sizeOf(redeemScriptLength);//1 n=3

        long op_pushDataLen = 1;
        long sigHashLen = 1;
        long signLen = 64;
        long signLenLen = 1;
        long zeroByteLen = 1;

        long mSignLen = m * (signLenLen + signLen + sigHashLen); //132 m=2

        long scriptLength = zeroByteLen + mSignLen + op_pushDataLen + redeemScriptVarInt + redeemScriptLength;//236 m=2
        long scriptVarInt = VarInt.sizeOf(scriptLength);

        long preTxIdLen = 32;
        long preIndexLen = 4;
        long sequenceLen = 4;

        long inputLength = preTxIdLen + preIndexLen + sequenceLen + scriptVarInt + scriptLength;//240 n=3,m=2


        long opReturnLen = 0;
        if (opReturnBytesLen != 0)
            opReturnLen = calcOpReturnLen(opReturnBytesLen);

        long outputValueLen = 8;
        long unlockScriptLen = 25; //If sending to multiSignAddr, it will be 23.
        long unlockScriptLenLen = 1;
        long outPutLen = outputValueLen + unlockScriptLenLen + unlockScriptLen;

        long inputCountLen = 1;
        long outputCountLen = 1;
        long txVerLen = 4;
        long nLockTimeLen = 4;
        long txFixedLen = inputCountLen + outputCountLen + txVerLen + nLockTimeLen;

        long length;
        length = txFixedLen + inputLength * inputNum + outPutLen * (outputNum + 1) + opReturnLen;

        return length;
    }

    private static int calcSplitNumP2SH(long fromTotal, long transfer, long feeRate, long splitGranularity, int inputNum, int opReturnBytesLen, int m, int n) {
        // numerator and denominator
        long numerator = fromTotal - transfer - calcFeeMultiSign(inputNum, 1, opReturnBytesLen, m, n) * feeRate + 34 * feeRate;
        long denominator = 34 * feeRate + splitGranularity;
        int splitNum = (int) (numerator / denominator);
        if (splitNum == 0 && numerator % denominator > 0) {
            splitNum = 1;
        }
        return splitNum;
    }

    private static long calcFeeMultiSignWithSplitGranularity(long fromTotal, long transfer, long feeRate, Long splitGranularity, int inputNum, int opReturnBytesLen, int m, int n) {
        long feeSize;
        if (splitGranularity != null && splitGranularity > 0) {
            if (splitGranularity < Constant.MIN_SPLIT_GRANULARITY) {
                throw new RuntimeException("error splitGranularity: " + splitGranularity);
            }
            int splitNum = calcSplitNumP2SH(fromTotal, transfer, feeRate, splitGranularity, inputNum, opReturnBytesLen, m, n);
            feeSize = calcFeeMultiSign(inputNum, splitNum, opReturnBytesLen, m, n);
        } else {
            feeSize = calcFeeMultiSign(inputNum, 1, opReturnBytesLen, m, n);
        }
        return feeSize;
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

    public static final Comparator<Cash> BITCOIN_SYS_COMPARATOR = new Comparator<Cash>() {
        @Override
        public int compare(Cash o1, Cash o2) {
            // order asc
            int compare = Long.valueOf(o1.getValue()).compareTo(o2.getValue());
            if (compare == 0) {
                int compare1 = o1.getBirthTxId().compareTo(o2.getBirthTxId());
                if (compare1 == 0) {
                    return Integer.compare(o1.getBirthIndex(), o2.getBirthIndex());
                }
                return compare1;
            }
            return compare;
        }
    };

    public static long calcFeeWithdrawal(List<Cash> utxos, long amount, long feeRate, boolean mainnet, Long splitGranularity) {
        int opReturnSize = 64;
        int m, n;
        if (mainnet) {
            m = 10;
            n = 15;
        } else {
            m = 2;
            n = 3;
        }
        long fee = 0;
        List<Cash> usingUtxos = new ArrayList<>();
        long totalMoney = 0;
        long totalSpend = 0;
        Collections.sort(utxos, BITCOIN_SYS_COMPARATOR);
        for (Cash utxo : utxos) {
            usingUtxos.add(utxo);
            totalMoney += utxo.getValue();
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

    public static long calcFeeWithdrawal2(List<UTXOData> utxos, long amount, long feeRate, boolean mainnet, Long splitGranularity) {
        int opReturnSize = 64;
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

    public static String makeDepositOpReturn(
            String nerveTo,
            long amount,
            String remark) throws Exception {
        RechargeData rechargeData = new RechargeData();
        rechargeData.setTo(AddressTool.getAddress(nerveTo));
        rechargeData.setValue(amount);
        rechargeData.setExtend0(remark);
        return HexUtil.encode(rechargeData.serialize());
    }

    public static BitCoinFeeInfo getMinimumFeeOfWithdrawal(String nerveTxHash, boolean mainnet) {
        String rpc;
        if (mainnet) {
            rpc = "https://api.nerve.network/jsonrpc";
        } else {
            rpc = "http://beta.api.nerve.network/jsonrpc";
        }
        RpcResult request = JsonRpcUtil.request(rpc, "getMinimumFeeOfWithdrawal", List.of(202, nerveTxHash));
        Map map = (Map) request.getResult();
        Integer minimumFee = Integer.parseInt(map.get("minimumFee").toString());
        Integer utxoSize = Integer.parseInt(map.get("utxoSize").toString());
        Long feeRate = Long.parseLong(map.get("feeRate").toString());
        return new BitCoinFeeInfo(minimumFee, utxoSize, feeRate);
    }

    public static Cash converterUTXOToCash(String txid, int vout, long value) {
        Cash cash = new Cash();
        cash.setBirthTxId(txid);
        cash.setBirthIndex(vout);
        cash.setValue(value);
        return cash;
    }

    public static UTXOData converterCashToUTXOData(Cash cash) {
        UTXOData utxo = new UTXOData();
        utxo.setTxid(cash.getBirthTxId());
        utxo.setVout(cash.getBirthIndex());
        utxo.setAmount(BigInteger.valueOf(cash.getValue()));
        return utxo;
    }
}
