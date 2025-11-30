/**
 * MIT License
 * <p>
 * Copyright (c) 2017-2019 nuls.io
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

import network.nerve.base.RPCUtil;
import network.nerve.base.basic.AddressTool;
import network.nerve.base.basic.NulsByteBuffer;
import network.nerve.base.basic.TransactionFeeCalculator;
import network.nerve.base.data.*;
import network.nerve.base.data.Transaction;
import network.nerve.base.signture.P2PHKSignature;
import network.nerve.core.basic.NulsData;
import network.nerve.core.basic.VarInt;
import network.nerve.core.exception.NulsException;
import network.nerve.core.model.LongUtils;
import network.nerve.heterogeneous.model.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import static network.nerve.base.basic.TransactionFeeCalculator.KB;
import static network.nerve.base.basic.TransactionFeeCalculator.NORMAL_PRICE_PRE_1024_BYTES;
import static network.nerve.core.constant.TxType.*;
import static network.nerve.heterogeneous.constant.Constant.STRING;
import static network.nerve.heterogeneous.utils.StringUtils.isBlank;

/**
 * @author: PierreLuo
 * @date: 2018/8/25
 */
public class NulsContractUtil {

    public static final String ZERO_ADDRESS = null;
    private static final int BYTE_COUNT = 8;               // 8 字节
    private static final int HEX_LENGTH = BYTE_COUNT * 2;  // 16 字符

    public static final String METHOD_CROSS_OUT_II = "crossOutII";
    public static final String NRC20_METHOD_TRANSFER = "transfer";
    public static final String METHOD_APPROVE = "approve";

    public static final String[] CROSSOUTII_TYPE = new String[]{"String", "BigInteger", "Address", "String"};
    public static final String[] APPROVE_TYPE = new String[]{"Address", "BigInteger"};

    public static String[][] twoDimensionalArray(Object[] args, String[] types) {
        if (args == null) {
            return null;
        } else {
            int length = args.length;
            String[][] two = new String[length][];
            Object arg;
            for (int i = 0; i < length; i++) {
                arg = args[i];
                if (arg == null) {
                    two[i] = new String[0];
                    continue;
                }
                if (arg instanceof String) {
                    String argStr = (String) arg;
                    // 非String类型参数，若传参是空字符串，则赋值为空一维数组，避免数字类型转化异常 -> 空字符串转化为数字
                    if (types != null && isBlank(argStr) && !STRING.equalsIgnoreCase(types[i])) {
                        two[i] = new String[0];
                    } else {
                        two[i] = new String[]{argStr};
                    }
                } else if (arg.getClass().isArray()) {
                    int len = Array.getLength(arg);
                    String[] result = new String[len];
                    for (int k = 0; k < len; k++) {
                        result[k] = valueOf(Array.get(arg, k));
                    }
                    two[i] = result;
                } else if (arg instanceof List) {
                    List resultArg = (List) arg;
                    int size = resultArg.size();
                    String[] result = new String[size];
                    for (int k = 0; k < size; k++) {
                        result[k] = valueOf(resultArg.get(k));
                    }
                    two[i] = result;
                } else {
                    two[i] = new String[]{valueOf(arg)};
                }
            }
            return two;
        }
    }

    public static byte[] extractContractAddressFromTxData(Transaction tx) {
        if (tx == null) {
            return null;
        }
        int txType = tx.getType();
        if (txType == CREATE_CONTRACT
                || txType == CALL_CONTRACT
                || txType == DELETE_CONTRACT) {
            return extractContractAddressFromTxData(tx.getTxData());
        }
        return null;
    }

    private static byte[] extractContractAddressFromTxData(byte[] txData) {
        if (txData == null) {
            return null;
        }
        int length = txData.length;
        if (length < Address.ADDRESS_LENGTH * 2) {
            return null;
        }
        byte[] contractAddress = new byte[Address.ADDRESS_LENGTH];
        System.arraycopy(txData, Address.ADDRESS_LENGTH, contractAddress, 0, Address.ADDRESS_LENGTH);
        return contractAddress;
    }


    public static String[][] twoDimensionalArray(Object[] args) {
        return twoDimensionalArray(args, null);
    }

    public static String valueOf(Object obj) {
        return (obj == null) ? null : obj.toString();
    }


    public static boolean isContractTransaction(Transaction tx) {
        if (tx == null) {
            return false;
        }
        int txType = tx.getType();
        if (txType == CREATE_CONTRACT
                || txType == CALL_CONTRACT
                || txType == DELETE_CONTRACT
                || txType == CONTRACT_TRANSFER
                || txType == CONTRACT_RETURN_GAS) {
            return true;
        }
        return false;
    }

    public static boolean isGasCostContractTransaction(Transaction tx) {
        if (tx == null) {
            return false;
        }
        int txType = tx.getType();
        if (txType == CREATE_CONTRACT
                || txType == CALL_CONTRACT) {
            return true;
        }
        return false;
    }


    public static boolean isLockContract(long lastestHeight, long blockHeight) throws NulsException {
        if (blockHeight > 0) {
            long confirmCount = lastestHeight - blockHeight;
            if (confirmCount < 7) {
                return true;
            }
        }
        return false;
    }


    public static String bigInteger2String(BigInteger bigInteger) {
        if (bigInteger == null) {
            return "0";
        }
        return bigInteger.toString();
    }

    public static String simplifyErrorMsg(String errorMsg) {
        String resultMsg = "contract error - ";
        if (isBlank(errorMsg)) {
            return resultMsg;
        }
        if (errorMsg.contains("Exception:")) {
            String[] msgs = errorMsg.split("Exception:", 2);
            return resultMsg + msgs[1].trim();
        }
        return resultMsg + errorMsg;
    }



    public static String argToString(String[][] args) {
        if (args == null) {
            return "";
        }
        String result = "";
        for (String[] a : args) {
            result += Arrays.toString(a) + "| ";
        }
        return result;
    }


    public static String asString(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static byte[] asBytes(String string) {
        return Base64.getDecoder().decode(string);
    }

    public static BigInteger minus(BigInteger a, BigInteger b) {
        BigInteger result = a.subtract(b);
        if (result.compareTo(BigInteger.ZERO) < 0) {
            throw new RuntimeException("Negative number detected.");
        }
        return result;
    }

    public static int extractTxTypeFromTx(String txString) throws NulsException {
        String txTypeHexString = txString.substring(0, 4);
        NulsByteBuffer byteBuffer = new NulsByteBuffer(RPCUtil.decode(txTypeHexString));
        return byteBuffer.readUint16();
    }

    public static String toString(String[][] a) {
        if (a == null)
            return "null";

        int iMax = a.length - 1;
        if (iMax == -1)
            return "[]";

        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0; ; i++) {
            b.append(Arrays.toString(a[i]));
            if (i == iMax) {
                b.append(']');
                break;
            }
            b.append(", ");
        }
        return b.toString();
    }



    public static CallContractTransaction newCallTx(int decimals, int chainId, int assetId, BigInteger senderBalance, String nonce, CallContractData callContractData, String remark,
                                                    List<ProgramMultyAssetValue> multyAssetValues, List<AccountAmountDto> nulsValueToOthers) {
        return newCallTx(decimals, chainId, assetId, senderBalance, nonce, callContractData, 0, remark, multyAssetValues, nulsValueToOthers);
    }

    public static CallContractTransaction newCallTx(int decimals, int chainId, int assetId, BigInteger senderBalance, String nonce, CallContractData callContractData, long time, String remark,
                                                    List<ProgramMultyAssetValue> multyAssetValues, List<AccountAmountDto> nulsValueToOthers) {
        try {
            CallContractTransaction tx = new CallContractTransaction();
            if (StringUtils.isNotBlank(remark)) {
                tx.setRemark(remark.getBytes(StandardCharsets.UTF_8));
            }
            if (time == 0) {
                tx.setTime(System.currentTimeMillis() / 1000);
            } else {
                tx.setTime(time);
            }

            byte[] sender = callContractData.getSender();
            BigInteger value = callContractData.getValue();
            byte[] contractAddress = callContractData.getContractAddress();
            List<CoinFrom> froms = new ArrayList<>();
            List<CoinTo> tos = new ArrayList<>();
            if (value.compareTo(BigInteger.ZERO) > 0) {
                CoinFrom coinFrom = new CoinFrom(sender, chainId, assetId, value, RPCUtil.decode(nonce), (byte) 0);
                froms.add(coinFrom);
                CoinTo coinTo = new CoinTo(contractAddress, chainId, assetId, value);
                tos.add(coinTo);
            }
            int _assetChainId, _assetId;
            if (multyAssetValues != null) {
                for (ProgramMultyAssetValue multyAssetValue : multyAssetValues) {
                    BigInteger _value = multyAssetValue.getValue();
                    _assetChainId = multyAssetValue.getAssetChainId();
                    _assetId = multyAssetValue.getAssetId();

                    CoinFrom coinFrom = new CoinFrom(sender, _assetChainId, _assetId, _value, RPCUtil.decode(multyAssetValue.getNonce()), (byte) 0);
                    froms.add(coinFrom);

                    CoinTo coinTo = new CoinTo(contractAddress, _assetChainId, _assetId, _value);
                    tos.add(coinTo);
                }
            }

            // 计算CoinData
            CoinData coinData = new CoinData();
            coinData.setFrom(froms);
            coinData.setTo(tos);
            long gasUsed = callContractData.getGasLimit();
            BigInteger imputedValue = BigInteger.valueOf(LongUtils.mul(gasUsed, callContractData.getPrice()));
            byte[] feeAccountBytes = sender;
            BigInteger feeValue = imputedValue;
            // 计算向其他地址转账
            if (nulsValueToOthers != null && !nulsValueToOthers.isEmpty()) {
                for (AccountAmountDto dto : nulsValueToOthers) {
                    feeValue = feeValue.add(dto.getValue());
                    coinData.addTo(new CoinTo(AddressTool.getAddress(dto.getTo()), chainId, assetId, dto.getValue()));
                }
            }
            CoinFrom feeAccountFrom = null;
            for (CoinFrom from : froms) {
                _assetChainId = from.getAssetsChainId();
                _assetId = from.getAssetsId();
                if (Arrays.equals(from.getAddress(), feeAccountBytes) && _assetChainId == chainId && _assetId == assetId) {
                    from.setAmount(from.getAmount().add(feeValue));
                    feeAccountFrom = from;
                    break;
                }
            }
            if (feeAccountFrom == null) {
                feeAccountFrom = new CoinFrom(feeAccountBytes, chainId, assetId, feeValue, RPCUtil.decode(nonce), (byte) 0);
                coinData.addFrom(feeAccountFrom);
            }
            tx.setCoinData(coinData.serialize());
            tx.setTxData(callContractData.serialize());

            BigInteger txSizeFee = getNormalUnsignedTxFee(decimals, tx.getSize() + 130);
            feeAccountFrom.setAmount(feeAccountFrom.getAmount().add(txSizeFee));

            tx.setCoinData(coinData.serialize());
            return tx;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static BigInteger calcTransferTxFee(int addressCount, int fromLength, int toLength, String remark, BigInteger price) {
        int size = 10;
        size += addressCount * P2PHKSignature.SERIALIZE_LENGTH;
        size += 70 * fromLength;
        size += 68 * toLength;
        if (StringUtils.isNotBlank(remark)) {
            size += StringUtils.bytes(remark).length;
        }
        size = size / 1024 + 1;
        return price.multiply(new BigInteger(size + ""));
    }

    public static final BigInteger getNormalTxFee(int decimals, int size) {
        BigInteger price = new BigDecimal(NORMAL_PRICE_PRE_1024_BYTES).movePointRight(decimals - 8).toBigInteger();
        BigInteger fee = price.multiply(new BigInteger(String.valueOf(size/KB)));
        if (size % KB > 0) {
            fee = fee.add(price);
        }
        return fee;
    }

    public static final BigInteger getNormalUnsignedTxFee(int decimals, int size) {
        BigInteger price = new BigDecimal(NORMAL_PRICE_PRE_1024_BYTES).movePointRight(decimals - 8).toBigInteger();
        size += P2PHKSignature.SERIALIZE_LENGTH;
        BigInteger fee = price.multiply(new BigInteger(String.valueOf(size/KB)));
        if (size % KB > 0) {
            fee = fee.add(price);
        }
        return fee;
    }

    public static String[][] multyAssetStringArray(List<ProgramMultyAssetValue> multyAssetValues) {
        int length;
        if (multyAssetValues == null || (length = multyAssetValues.size()) == 0) {
            return null;
        }
        String[][] array = new String[length][];
        ProgramMultyAssetValue value;
        for (int i = 0; i < length; i++) {
            value = multyAssetValues.get(i);
            array[i] = new String[]{value.getValue().toString(), String.valueOf(value.getAssetChainId()), String.valueOf(value.getAssetId())};
        }
        return array;
    }

    public static String[][] multyAssetStringArray(ProgramMultyAssetValue[] multyAssetValues) {
        int length;
        if (multyAssetValues == null || (length = multyAssetValues.length) == 0) {
            return null;
        }
        String[][] array = new String[length][];
        ProgramMultyAssetValue value;
        for (int i = 0; i < length; i++) {
            value = multyAssetValues[i];
            array[i] = new String[]{value.getValue().toString(), String.valueOf(value.getAssetChainId()), String.valueOf(value.getAssetId())};
        }
        return array;
    }

    private static CoinData makeCoinData(int chainId, int assetsId, BigInteger senderBalance, String nonce, ContractData contractData, int txSize, int txDataSize) {
        CoinData coinData = new CoinData();
        long gasUsed = contractData.getGasLimit();
        BigInteger imputedValue = BigInteger.valueOf(LongUtils.mul(gasUsed, contractData.getPrice()));
        // 总花费
        BigInteger value = contractData.getValue();
        BigInteger totalValue = imputedValue.add(value);

        CoinFrom coinFrom = new CoinFrom(contractData.getSender(), chainId, assetsId, totalValue, RPCUtil.decode(nonce), (byte) 0);
        coinData.addFrom(coinFrom);

        if (value.compareTo(BigInteger.ZERO) > 0) {
            CoinTo coinTo = new CoinTo(contractData.getContractAddress(), chainId, assetsId, value);
            coinData.addTo(coinTo);
        }

        BigInteger fee = TransactionFeeCalculator.getNormalUnsignedTxFee(txSize + txDataSize + calcSize(coinData));
        totalValue = totalValue.add(fee);
        if (senderBalance.compareTo(totalValue) < 0) {
            // Insufficient balance
            throw new RuntimeException("Insufficient balance");
        }
        coinFrom.setAmount(totalValue);
        return coinData;
    }

    private static int calcSize(NulsData nulsData) {
        if (nulsData == null) {
            return 0;
        }
        int size = nulsData.size();
        // 计算tx.size()时，当coinData和txData为空时，计算了1个长度，若此时nulsData不为空，则要扣减这1个长度
        return VarInt.sizeOf(size) + size - 1;
    }

    public static String genNulsAddressByCompressedPublickey(int nativeId, String prefix, String pub) {
        return AddressTool.getStringAddressByBytes(
                AddressTool.getAddress(HexUtil.decode(pub), nativeId), prefix
        );
    }

    public static BigInteger hexToBigInteger(String hex) {
        if (hex == null) {
            throw new NullPointerException("hex is null");
        }
        // 去掉可能的 0x 前缀
        if (hex.startsWith("0x") || hex.startsWith("0X")) {
            hex = hex.substring(2);
        }
        // 统一为大写，方便处理
        hex = hex.toUpperCase();

        // 补足或截断到 16 字符
        if (hex.length() > HEX_LENGTH) {
            hex = hex.substring(hex.length() - HEX_LENGTH);
        } else {
            hex = repeat("0", HEX_LENGTH - hex.length()) + hex;
        }

        // 直接用 BigInteger 构造（内部会按大端处理）
        return new BigInteger(hex, 16);
    }

    public static String repeat(String str, int count) {
        if (count <= 0) return "";
        if (count == 1) return str;

        StringBuilder sb = new StringBuilder(str.length() * count);
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
}
