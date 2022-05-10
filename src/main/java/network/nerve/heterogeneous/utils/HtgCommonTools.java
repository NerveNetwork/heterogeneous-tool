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


import network.nerve.heterogeneous.constant.Constant;
import network.nerve.heterogeneous.crypto.StructuredDataEncoder;
import org.apache.commons.lang3.ArrayUtils;
import org.bouncycastle.math.ec.ECPoint;
import org.tron.trident.core.key.KeyPair;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author: Loki
 * @date: 2020/11/18
 */
public class HtgCommonTools {

    public static boolean isTimeOutError(String error) {
        if (StringUtils.isBlank(error)) {
            return false;
        }
        return error.contains("timeout") || error.contains("timed out");
    }

    public static String ethSign(String priKey, String dataHex) {
        dataHex = Numeric.cleanHexPrefix(dataHex);
        byte[] bytes = HexUtil.decode(dataHex);
        return ethSign(priKey, bytes);
    }

    public static String personalSign(String priKey, String data) {
        byte[] bytes = dataToBytes(data);
        Credentials credentials = Credentials.create(priKey);
        Sign.SignatureData signatureData = Sign.signPrefixedMessage(bytes, credentials.getEcKeyPair());
        byte[] bytesValue = org.apache.commons.lang3.ArrayUtils.addAll(signatureData.getR(), signatureData.getS());
        bytesValue = ArrayUtils.addAll(bytesValue, signatureData.getV());
        String result = "0x" + HexUtil.encode(bytesValue);
        return result;
    }

    public static String signTypedDataV4(String priKey, String json) throws IOException {
        json = json.replace("\\\"", "\"");
        StructuredDataEncoder encoder = new StructuredDataEncoder(json);
        byte[] hash = encoder.hashStructuredData();
        return ethSign(priKey, hash);
    }

    public static BigDecimal calNVTOfWithdraw(BigDecimal nvtUSD, BigDecimal gasPrice, BigDecimal ethUSD, boolean isETHToken) {
        BigDecimal gasLimit;
        if (isETHToken) {
            gasLimit = BigDecimal.valueOf(210000L);
        } else {
            gasLimit = BigDecimal.valueOf(190000L);
        }
        BigDecimal nvtAmount = calNVTByGasPrice(nvtUSD, gasPrice, ethUSD, gasLimit);
        nvtAmount = nvtAmount.divide(BigDecimal.TEN.pow(8), 0, RoundingMode.UP).movePointRight(8);
        return nvtAmount;
    }

    public static BigDecimal calNVTByGasPrice(BigDecimal nvtUSD, BigDecimal gasPrice, BigDecimal ethUSD, BigDecimal gasLimit) {
        BigDecimal nvtAmount = ethUSD.multiply(gasPrice).multiply(gasLimit).divide(nvtUSD.multiply(BigDecimal.TEN.pow(10)), 0, RoundingMode.UP);
        return nvtAmount;
    }

    public static String genEthAddressByCompressedPublickey(String compressedPublickey) {
        ECPoint ecPoint = network.nerve.heterogeneous.crypto.Sign.CURVE.getCurve().decodePoint(Numeric.hexStringToByteArray(compressedPublickey));
        byte[] encoded = ecPoint.getEncoded(false);
        String orginPubkeyStr = Constant.HEX_PREFIX + Numeric.toHexStringNoPrefix(encoded).substring(2);
        return Constant.HEX_PREFIX + Keys.getAddress(orginPubkeyStr);
    }

    public static Function getNameERC20Function() {
        return new Function(
                Constant.METHOD_VIEW_ERC20_NAME,
                ListUtil.of(),
                ListUtil.of(new TypeReference<Utf8String>() {}));
    }
    public static Function getSymbolERC20Function() {
        return new Function(
                Constant.METHOD_VIEW_ERC20_SYMBOL,
                ListUtil.of(),
                ListUtil.of(new TypeReference<Utf8String>() {}));
    }
    public static Function getDecimalsERC20Function() {
        return new Function(
                Constant.METHOD_VIEW_ERC20_DECIMALS,
                ListUtil.of(),
                ListUtil.of(new TypeReference<Uint8>() {}));
    }

    private static String ethSign(String priKey, byte[] bytes) {
        Credentials credentials = Credentials.create(priKey);
        //得到签名
        Sign.SignatureData signatureData = Sign.signMessage(bytes, credentials.getEcKeyPair(), false);
        byte[] bytesValue = org.apache.commons.lang3.ArrayUtils.addAll(signatureData.getR(), signatureData.getS());
        bytesValue = ArrayUtils.addAll(bytesValue, signatureData.getV());
        String result = "0x" + HexUtil.encode(bytesValue);
        return result;
    }

    private static byte[] dataToBytes(String data) {
        if (StringUtils.isBlank(data)) {
            return null;
        }
        String cleanData = Numeric.cleanHexPrefix(data);
        try {
            boolean isHex = true;
            char[] chars = cleanData.toCharArray();
            for (char c : chars) {
                int digit = Character.digit(c, 16);
                if (digit == -1) {
                    isHex = false;
                    break;
                }
            }
            if (isHex) {
                return HexUtil.decode(cleanData);
            }
            return data.getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            return data.getBytes(StandardCharsets.UTF_8);
        }
    }

    public static String personalSignForTRON(String priKey, String data) {
        byte[] message = dataToBytes(data);
        int messageLength = message.length;
        Credentials credentials = Credentials.create(priKey);
        byte[] prefix = "\u0019TRON Signed Message:\n32".getBytes();
        byte[] result = new byte[prefix.length + messageLength];
        System.arraycopy(prefix, 0, result, 0, prefix.length);
        System.arraycopy(message, 0, result, prefix.length, messageLength);

        Sign.SignatureData signatureData = Sign.signMessage(result, credentials.getEcKeyPair());
        byte[] bytesValue = org.apache.commons.lang3.ArrayUtils.addAll(signatureData.getR(), signatureData.getS());
        bytesValue = ArrayUtils.addAll(bytesValue, signatureData.getV());
        String resultHex = "0x" + HexUtil.encode(bytesValue);
        return resultHex;
    }

    public static String signTxForTRON(String priKey, String txid) {
        byte[] signature = KeyPair.signTransaction(Numeric.hexStringToByteArray(txid), new KeyPair(priKey));
        return Numeric.toHexStringNoPrefix(signature);
    }
}
