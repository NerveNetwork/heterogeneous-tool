package network.nerve; /**
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

import network.nerve.heterogeneous.utils.HexUtil;
import network.nerve.heterogeneous.utils.HtgCommonTools;
import network.nerve.heterogeneous.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.nio.charset.StandardCharsets;

/**
 * @author: PierreLuo
 * @date: 2023/3/3
 */
public class NulsPersonalSignTest {

    public void personalSignTest () {
        String priKey = "...";
        String data = "test";
        String result = nulsPersonalSign(priKey, data);
        System.out.println(result);
    }

    static String nulsPersonalSign(String priKey, String data) {
        byte[] bytes = dataToBytes(data);
        System.out.println(String.format("data hex: %s", HexUtil.encode(bytes)));
        Credentials credentials = Credentials.create(priKey);
        Sign.SignatureData signatureData = Sign.signPrefixedMessage(bytes, credentials.getEcKeyPair());
        byte[] bytesValue = ArrayUtils.addAll(signatureData.getR(), signatureData.getS());
        bytesValue = ArrayUtils.addAll(bytesValue, signatureData.getV());
        String result = "0x" + HexUtil.encode(bytesValue);
        return result;
    }

    static byte[] getEthereumMessagePrefix(int messageLength) {
        return "\u0019NULS Signed Message:\n".concat(String.valueOf(messageLength)).getBytes();
    }

    static byte[] getEthereumMessageHash(byte[] message) {
        byte[] prefix = getEthereumMessagePrefix(message.length);
        byte[] result = new byte[prefix.length + message.length];
        System.arraycopy(prefix, 0, result, 0, prefix.length);
        System.arraycopy(message, 0, result, prefix.length, message.length);
        return Hash.sha3(result);
    }

    static Sign.SignatureData signPrefixedMessage(byte[] message, ECKeyPair keyPair) {
        return Sign.signMessage(getEthereumMessageHash(message), keyPair, false);
    }

    static byte[] dataToBytes(String data) {
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
}
