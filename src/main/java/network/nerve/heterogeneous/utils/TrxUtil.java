/**
 * MIT License
 * <p>
 * Copyright (c) 2019-2020 nerve.network
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

import com.google.protobuf.InvalidProtocolBufferException;
import network.nerve.heterogeneous.constant.TrxConstant;
import network.nerve.heterogeneous.model.TRC20TransferEvent;
import network.nerve.heterogeneous.model.TrxAccount;
import network.nerve.heterogeneous.model.TrxTransaction;
import org.bouncycastle.jcajce.provider.digest.SHA256;
import org.tron.trident.abi.FunctionReturnDecoder;
import org.tron.trident.abi.TypeReference;
import org.tron.trident.abi.datatypes.*;
import org.tron.trident.abi.datatypes.generated.Uint256;
import org.tron.trident.abi.datatypes.generated.Uint8;
import org.tron.trident.core.ApiWrapper;
import org.tron.trident.core.key.KeyPair;
import org.tron.trident.crypto.SECP256K1;
import org.tron.trident.crypto.tuwenitypes.Bytes;
import org.tron.trident.crypto.tuwenitypes.Bytes32;
import org.tron.trident.crypto.tuwenitypes.MutableBytes;
import org.tron.trident.crypto.tuwenitypes.UInt256;
import org.tron.trident.proto.Chain;
import org.tron.trident.proto.Contract;
import org.tron.trident.proto.Response;
import org.tron.trident.utils.Base58Check;
import org.tron.trident.utils.Numeric;
import org.web3j.crypto.ECDSASignature;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static network.nerve.heterogeneous.constant.TrxConstant.EMPTY_STRING;


/**
 * @author: Mimi
 * @date: 2020-02-26
 */
public class TrxUtil {

    private static final List<TypeReference<Type>> revertReasonType = Collections.singletonList(TypeReference.create((Class<Type>) AbiTypes.getType("string")));
    private static final String errorMethodId = "0x08c379a0";



    private static BigInteger extractPublicKey(String from, Chain.Transaction tx) {
        SECP256K1.Signature sign = SECP256K1.Signature.decode(Bytes.wrap(tx.getSignature(0).toByteArray()));
        ECDSASignature signature = new ECDSASignature(sign.getR(), sign.getS());
        SHA256.Digest digest = new SHA256.Digest();
        digest.update(tx.getRawData().toByteArray());
        byte[] hashBytes = digest.digest();

        BigInteger recoverPubKey = Sign.recoverFromSignature(sign.getRecId(), signature, hashBytes);
        if (recoverPubKey != null) {
            String address = TrxConstant.HEX_PREFIX + Keys.getAddress(recoverPubKey);
            if (trxAddress2eth(from).equals(address.toLowerCase())) {
                return recoverPubKey;
            }
        }
        return null;
    }

    public static List<Object> parseEvent(String eventData, Event event) {
        List<Type> typeList = FunctionReturnDecoder.decode(eventData, event.getParameters());
        return typeList.stream().map(type -> type.getValue()).collect(Collectors.toList());
    }

    public static TRC20TransferEvent parseTRC20Event(Response.TransactionInfo.Log log) {
        String from = new Address(new BigInteger(log.getTopics(1).toByteArray())).getValue();
        String to = new Address(new BigInteger(log.getTopics(2).toByteArray())).getValue();
        BigInteger value = new BigInteger(log.getData().toByteArray());
        return new TRC20TransferEvent(from, to, value);
    }

    public static List<Object> parseInput(String inputData, List<TypeReference<Type>> parameters) {
        if(StringUtils.isBlank(inputData)) {
            return null;
        }
        inputData = Numeric.cleanHexPrefix(inputData);
        if(inputData.length() < 8) {
            return null;
        }
        inputData = inputData.substring(8);
        List<Type> typeList = FunctionReturnDecoder.decode(inputData, parameters);
        return typeList.stream().map(type -> type.getValue()).collect(Collectors.toList());
    }

    public static String rightPadding(String orgin, String padding, int total) {
        StringBuilder sb = new StringBuilder(orgin);
        for (int i = 0, length = total - orgin.length(); i < length; i++) {
            sb.append(padding);
        }
        return sb.toString();
    }

    public static List<Object> parseTRC20TransferInput(String inputData) {
        if(StringUtils.isBlank(inputData)) {
            return null;
        }
        inputData = Numeric.cleanHexPrefix(inputData);
        if(inputData.length() < 8) {
            return null;
        }
        List<Object> result = new ArrayList<>();
        inputData = inputData.substring(8);
        String toAddress;
        BigInteger value;
        if (inputData.length() < 64) {
            toAddress = TrxConstant.HEX_PREFIX + rightPadding(inputData, "0", 64).substring(24, 64);
            value = BigInteger.ZERO;
        } else if (inputData.length() < 128) {
            toAddress = TrxConstant.HEX_PREFIX + inputData.substring(24, 64);
            value = new BigInteger(rightPadding(inputData, "0", 128).substring(64, 128), 16);
        } else {
            toAddress = TrxConstant.HEX_PREFIX + inputData.substring(24, 64);
            value = new BigInteger(inputData.substring(64, 128), 16);
        }
        result.add(ethAddress2trx(toAddress));
        result.add(value);
        return result;
    }

    public static List<Object> parseTRC20TransferFromInput(String inputData) {
        if(StringUtils.isBlank(inputData)) {
            return null;
        }
        inputData = Numeric.cleanHexPrefix(inputData);
        if(inputData.length() < 8) {
            return null;
        }
        List<Object> result = new ArrayList<>();
        inputData = inputData.substring(8);
        String fromAddress;
        String toAddress;
        BigInteger value;
        if (inputData.length() < 64) {
            fromAddress = TrxConstant.HEX_PREFIX + rightPadding(inputData, "0", 64).substring(24, 64);
            toAddress = TrxConstant.ZERO_ADDRESS;
            value = BigInteger.ZERO;
        } else if (inputData.length() < 128) {
            fromAddress = TrxConstant.HEX_PREFIX + inputData.substring(24, 64);
            toAddress = TrxConstant.HEX_PREFIX + rightPadding(inputData, "0", 128).substring(88, 128);
            value = BigInteger.ZERO;
        } else if (inputData.length() < 192) {
            fromAddress = TrxConstant.HEX_PREFIX + inputData.substring(24, 64);
            toAddress = TrxConstant.HEX_PREFIX + inputData.substring(88, 128);
            value = new BigInteger(rightPadding(inputData, "0", 192).substring(128, 192), 16);
        } else {
            fromAddress = TrxConstant.HEX_PREFIX + inputData.substring(24, 64);
            toAddress = TrxConstant.HEX_PREFIX + inputData.substring(88, 128);
            value = new BigInteger(inputData.substring(128, 192), 16);
        }
        result.add(ethAddress2trx(fromAddress));
        result.add(ethAddress2trx(toAddress));
        result.add(value);
        return result;
    }

    public static <T>  T[] list2array(List<T> list) {
        if(list == null || list.isEmpty()) {
            return null;
        }
        T[] array = (T[]) Array.newInstance(list.get(0).getClass(), list.size());
        return list.toArray(array);
    }

    public static Function getNameERC20Function() {
        return new Function(
                TrxConstant.METHOD_VIEW_ERC20_NAME,
                ListUtil.of(),
                ListUtil.of(new TypeReference<Utf8String>() {}));
    }
    public static Function getSymbolERC20Function() {
        return new Function(
                TrxConstant.METHOD_VIEW_ERC20_SYMBOL,
                ListUtil.of(),
                ListUtil.of(new TypeReference<Utf8String>() {}));
    }
    public static Function getDecimalsERC20Function() {
        return new Function(
                TrxConstant.METHOD_VIEW_ERC20_DECIMALS,
                ListUtil.of(),
                ListUtil.of(new TypeReference<Uint8>() {}));
    }

    public static Function getBalanceOfERC20Function(String address) {
        return new Function("balanceOf",
                Arrays.asList(new Address(address)),
                Arrays.asList(new TypeReference<Uint256>() {
                }));
    }

    public static Function getTransferERC20Function(String recipient, BigInteger value) {
        return new Function(
                "transfer",
                Arrays.asList(new Address(recipient), new Uint256(value)),
                Arrays.asList(new TypeReference<Type>() {}));
    }

    public static  Function getERC20TransferFromFunction(String sender, String recipient, BigInteger value) {
        return new Function(
                "transferFrom",
                ListUtil.of(new Address(sender),
                        new Address(recipient),
                        new Uint256(value)),
                ListUtil.of(new TypeReference<Type>() {
                })
        );
    }

    public static  Function getERC20ApproveFunction(String spender, BigInteger value) {
        return new Function(
                "approve",
                ListUtil.of(new Address(spender),
                        new Uint256(value)),
                ListUtil.of(new TypeReference<Type>() {
                })
        );
    }

    public static Function getCrossOutFunction(String to, BigInteger value, String erc20) {
        return new Function(
                TrxConstant.METHOD_CROSS_OUT,
                ListUtil.of(new Utf8String(to),
                        new Uint256(value),
                        new Address(erc20)),
                ListUtil.of(new TypeReference<Type>() {
                })
        );
    }

    public static Function getCrossOutIIFunction(String to, BigInteger value, String erc20, String data) {
        DynamicBytes dynamicBytes;
        if (StringUtils.isNotBlank(data)) {
            dynamicBytes = new DynamicBytes(Numeric.hexStringToByteArray(data));
        } else {
            dynamicBytes = new DynamicBytes(Numeric.hexStringToByteArray(TrxConstant.HEX_PREFIX));
        }
        return new Function(
                "crossOutII",
                ListUtil.of(new Utf8String(to),
                        new Uint256(value),
                        new Address(erc20),
                        dynamicBytes),
                ListUtil.of(new TypeReference<Type>() {
                })
        );
    }

    public static Function getOneClickCrossChainFunction(BigInteger feeAmount, int desChainId, String desToAddress,
                                                         BigInteger tipping, String tippingAddress, String desExtend) {
        if (tipping == null) {
            tipping = BigInteger.ZERO;
        }
        if (StringUtils.isBlank(tippingAddress)) {
            tippingAddress = EMPTY_STRING;
        }
        DynamicBytes dynamicBytes;
        if (StringUtils.isNotBlank(desExtend)) {
            dynamicBytes = new DynamicBytes(Numeric.hexStringToByteArray(desExtend));
        } else {
            dynamicBytes = new DynamicBytes(Numeric.hexStringToByteArray(TrxConstant.HEX_PREFIX));
        }
        return new Function(
                "oneClickCrossChain",
                ListUtil.of(
                        new Uint256(feeAmount),
                        new Uint256(desChainId),
                        new Utf8String(desToAddress),
                        new Uint256(tipping),
                        new Utf8String(tippingAddress),
                        dynamicBytes
                ),
                ListUtil.of(new TypeReference<Type>() {
                })
        );
    }

    public static String dataSign(String hashStr, String prikey) {
        byte[] hash = Numeric.hexStringToByteArray(hashStr);
        KeyPair pair = new KeyPair(prikey);
        SECP256K1.Signature sig = SECP256K1.sign(Bytes32.wrap(hash), pair.getRawPair());
        MutableBytes bytes = MutableBytes.create(65);
        UInt256.valueOf(sig.getR()).toBytes().copyTo(bytes, 0);
        UInt256.valueOf(sig.getS()).toBytes().copyTo(bytes, 32);
        byte recId = sig.getRecId();
        if (recId <= 1) {
            recId += 27;
        }
        bytes.set(64, recId);
        byte[] signed = bytes.toArray();
        String signedHex = Numeric.toHexStringNoPrefix(signed);
        return signedHex;
    }

    public static Boolean verifySign(String signAddress, String vHash, String signed) {
        signed = Numeric.cleanHexPrefix(signed);
        if (signed.length() != 130) {
            return false;
        }
        String r = "0x" + signed.substring(0, 64);
        String s = "0x" + signed.substring(64, 128);
        int v = Integer.parseInt(signed.substring(128), 16);
        if (v >= 27) {
            v -= 27;
        }
        ECDSASignature signature = new ECDSASignature(Numeric.decodeQuantity(r), Numeric.decodeQuantity(s));
        byte[] hashBytes = Numeric.hexStringToByteArray(vHash);
        BigInteger recover = Sign.recoverFromSignature(v, signature, hashBytes);
        if (recover != null) {
            String address = "0x" + Keys.getAddress(recover);
            if (trxAddress2eth(signAddress).equals(address.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static String trxAddress2eth(String address) {
        if (StringUtils.isBlank(address)) {
            return null;
        }
        if (address.startsWith("0x") && address.length() == 42) {
            return address;
        }
        byte[] bytes = ApiWrapper.parseAddress(address).toByteArray();
        int length = bytes.length;
        if (length == 20) {
            return Numeric.toHexString(bytes);
        } else if (length == 21) {
            byte[] result = new byte[20];
            System.arraycopy(bytes, 1, result, 0, 20);
            return Numeric.toHexString(result);
        } else {
            return null;
        }
    }

    public static String ethAddress2trx(String address) {
        if (StringUtils.isBlank(address)) {
            return null;
        }
        if (address.startsWith("T")) {
            // need check? Base58Check.base58ToBytes(address);
            return address;
        }
        return ethAddress2trx(Numeric.hexStringToByteArray(address));
    }

    public static String ethAddress2trx(byte[] address) {
        if (address == null) {
            return null;
        }
        int length = address.length;
        if (length == 21) {
            return Base58Check.bytesToBase58(address);
        } else if (length == 20) {
            byte[] result = new byte[21];
            result[0] = 65;
            System.arraycopy(address, 0, result, 1, 20);
            return Base58Check.bytesToBase58(result);
        } else {
            return null;
        }
    }

    public static byte[] address2Bytes(String address) {
        if (StringUtils.isBlank(address)) {
            return null;
        }
        byte[] bytes = ApiWrapper.parseAddress(address).toByteArray();
        int length = bytes.length;
        if (length == 21) {
            return bytes;
        } else if (length == 20) {
            byte[] result = new byte[21];
            result[0] = 65;
            System.arraycopy(address, 0, result, 1, 20);
            return result;
        } else {
            return null;
        }
    }

    public static TrxAccount createAccount(String priKey) {
        KeyPair keyPair = new KeyPair(priKey);
        byte[] pubKey = keyPair.getRawPair().getPublicKey().getEncoded();
        TrxAccount account = new TrxAccount();
        account.setAddress(keyPair.toBase58CheckAddress());
        account.setPubKey(pubKey);
        account.setPriKey(keyPair.getRawPair().getPrivateKey().getEncoded());
        return account;
    }

    public static String calcTxHash(Chain.Transaction tx) {
        SHA256.Digest digest = new SHA256.Digest();
        digest.update(tx.getRawData().toByteArray());
        byte[] txid = digest.digest();
        String txHash = Numeric.toHexString(txid);
        return txHash;
    }

    public static TrxTransaction generateTxInfo(Chain.Transaction tx) throws InvalidProtocolBufferException {
        if (tx == null) {
            return null;
        }
        Chain.Transaction.Contract contract = tx.getRawData().getContract(0);
        Chain.Transaction.Contract.ContractType type = contract.getType();
        // 过滤 非TRX转账和调用合约交易
        if (Chain.Transaction.Contract.ContractType.TransferContract != type &&
                Chain.Transaction.Contract.ContractType.TriggerSmartContract != type) {
            return null;
        }
        String from = EMPTY_STRING, to = EMPTY_STRING, input = EMPTY_STRING;
        BigInteger value = BigInteger.ZERO;
        // 转账
        if (Chain.Transaction.Contract.ContractType.TransferContract == type) {
            Contract.TransferContract tc = Contract.TransferContract.parseFrom(contract.getParameter().getValue());
            from = TrxUtil.ethAddress2trx(tc.getOwnerAddress().toByteArray());
            to = TrxUtil.ethAddress2trx(tc.getToAddress().toByteArray());
            value = BigInteger.valueOf(tc.getAmount());
        } else if (Chain.Transaction.Contract.ContractType.TriggerSmartContract == type) {
            // 调用合约
            Contract.TriggerSmartContract tg = Contract.TriggerSmartContract.parseFrom(contract.getParameter().getValue());
            from = TrxUtil.ethAddress2trx(tg.getOwnerAddress().toByteArray());
            to = TrxUtil.ethAddress2trx(tg.getContractAddress().toByteArray());
            value = BigInteger.valueOf(tg.getCallValue());
            input = Numeric.toHexString(tg.getData().toByteArray());
        }
        // 计算txHash
        String trxTxHash = TrxUtil.calcTxHash(tx);
        TrxTransaction trxTxInfo = new TrxTransaction(tx, trxTxHash, from, to, value, input, type);
        return trxTxInfo;
    }

    public static BigInteger convertTrxToSun(BigDecimal value) {
        value = value.movePointRight(6);
        return value.toBigInteger();
    }

    public static BigDecimal convertSunToTrx(BigInteger balance) {
        BigDecimal cardinalNumber = new BigDecimal("1000000");
        BigDecimal decimalBalance = new BigDecimal(balance);
        BigDecimal value = decimalBalance.divide(cardinalNumber, 6, RoundingMode.DOWN);
        return value;
    }

    public static boolean isErrorInResult(String result) {
        return result != null && result.startsWith(errorMethodId);
    }

    public static String getRevertReason(String result) {
        if (isErrorInResult(result)) {
            String hexRevertReason = result.substring(errorMethodId.length());
            List<Type> decoded = FunctionReturnDecoder.decode(hexRevertReason, revertReasonType);
            Utf8String decodedRevertReason = (Utf8String) decoded.get(0);
            return decodedRevertReason.getValue();
        }
        return null;
    }

    public static boolean checkTransactionSuccess(Response.TransactionInfo receipt) {
        if (receipt == null) {
            return false;
        }
        long energyUsage = receipt.getReceiptOrBuilder().getEnergyUsage();
        if (energyUsage == 0) {
            // 没有能量消耗，视为普通转账交易
            return true;
        }
        if (receipt.getReceipt().getResult() != Chain.Transaction.Result.contractResult.SUCCESS) {
            return false;
        }
        return true;
    }
}
