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
package util;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.SegwitAddress;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;
import org.bouncycastle.math.ec.ECPoint;
import org.tron.trident.utils.Numeric;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * @author: PierreLuo
 * @date: 2023/11/16
 */
public class BTCTool {

    private static final BigInteger _0n = BigInteger.ZERO;
    private static final BigInteger _1n = BigInteger.ONE;
    private static final BigInteger _2n = BigInteger.valueOf(2);
    private static final BigInteger _3n = BigInteger.valueOf(3);
    private static final BigInteger _8n = BigInteger.valueOf(8);
    private static final BigInteger _Pn = new BigInteger("fffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f", 16);

    private static BigInteger mod(BigInteger a, BigInteger b) {
        if (b == null) b = _Pn;
        BigInteger result = a.mod(b);
        return result.compareTo(_0n) >= 0 ? result : b.add(result);
    }

    private static BigInteger invert(BigInteger number, BigInteger modulo) {
        if (modulo == null) modulo = _Pn;
        if (number.compareTo(_0n) == 0 || modulo.compareTo(_0n) <= 0) {
            throw new IllegalArgumentException("invert: expected positive integers");
        }
        BigInteger a = mod(number, modulo);
        BigInteger b = modulo;
        BigInteger x = _0n, y = _1n, u = _1n, v = _0n;
        while (a.compareTo(_0n) != 0) {
            BigInteger[] divAndRemainder = b.divideAndRemainder(a);
            BigInteger q = divAndRemainder[0];
            BigInteger r = divAndRemainder[1];
            BigInteger m = x.subtract(u.multiply(q));
            BigInteger n = y.subtract(v.multiply(q));
            b = a;
            a = r;
            x = u;
            y = v;
            u = m;
            v = n;
        }
        BigInteger gcd = b;
        if (gcd.compareTo(_1n) != 0)
            throw new ArithmeticException("invert: does not exist");
        return mod(x, modulo);
    }

    private static BigInteger[] add(BigInteger X1, BigInteger Y1, BigInteger Z1, BigInteger X2, BigInteger Y2, BigInteger Z2) {
        if (X2.compareTo(_0n) == 0 || Y2.compareTo(_0n) == 0)
            return new BigInteger[]{_0n};
        if (X1.compareTo(_0n) == 0 || Y1.compareTo(_0n) == 0)
            return new BigInteger[]{_1n};
        BigInteger Z1Z1 = mod(Z1.multiply(Z1), _Pn);
        BigInteger Z2Z2 = mod(Z2.multiply(Z2), _Pn);
        BigInteger U1 = mod(X1.multiply(Z2Z2), _Pn);
        BigInteger U2 = mod(X2.multiply(Z1Z1), _Pn);
        BigInteger S1 = mod(Y1.multiply(Z2).multiply(Z2Z2), _Pn);
        BigInteger S2 = mod(Y2.multiply(Z1).multiply(Z1Z1), _Pn);
        BigInteger H = mod(U2.subtract(U1), _Pn);
        BigInteger r = mod(S2.subtract(S1), _Pn);

        if (H.compareTo(_0n) == 0) {
            if (r.compareTo(_0n) == 0) {
                return doublePoint(X1, Y1, Z1);
            } else {
                return new BigInteger[]{_2n};
            }
        }

        BigInteger HH = mod(H.multiply(H), _Pn);
        BigInteger HHH = mod(H.multiply(HH), _Pn);
        BigInteger V = mod(U1.multiply(HH), _Pn);
        BigInteger X3 = mod(r.multiply(r).subtract(HHH).subtract(_2n.multiply(V)), _Pn);
        BigInteger Y3 = mod(r.multiply(V.subtract(X3)).subtract(S1.multiply(HHH)), _Pn);
        BigInteger Z3 = mod(Z1.multiply(Z2).multiply(H), _Pn);
        return new BigInteger[]{X3, Y3, Z3};
    }

    private static BigInteger[] doublePoint(BigInteger X1, BigInteger Y1, BigInteger Z1) {
        BigInteger A = mod(X1.multiply(X1), _Pn);
        BigInteger B = mod(Y1.multiply(Y1), _Pn);
        BigInteger C = mod(B.multiply(B), _Pn);
        BigInteger x1b = X1.add(B);
        BigInteger D = mod(_2n.multiply(mod(x1b.multiply(x1b), _Pn)).subtract(A).subtract(C), _Pn);
        BigInteger E = mod(_3n.multiply(A), _Pn);
        BigInteger F = mod(E.multiply(E), _Pn);
        BigInteger X3 = mod(F.subtract(_2n.multiply(D)), _Pn);
        BigInteger Y3 = mod(E.multiply(D.subtract(X3)).subtract(_8n.multiply(C)), _Pn);
        BigInteger Z3 = mod(_2n.multiply(Y1).multiply(Z1), _Pn);
        return new BigInteger[]{X3, Y3, Z3};
    }

    private static BigInteger[] toAffine(BigInteger x, BigInteger y, BigInteger z, BigInteger invZ) {
        boolean isZero = x.compareTo(_0n) == 0 && y.compareTo(_1n) == 0 && z.compareTo(_0n) == 0;
        if (invZ == null)
            invZ = isZero ? _8n : invert(z, _Pn);
        BigInteger iz1 = invZ;
        BigInteger iz2 = mod(iz1.multiply(iz1), _Pn);
        BigInteger iz3 = mod(iz2.multiply(iz1), _Pn);
        BigInteger ax = mod(x.multiply(iz2), _Pn);
        BigInteger ay = mod(y.multiply(iz3), _Pn);
        BigInteger zz = mod(z.multiply(iz1), _Pn);
        if (isZero)
            return new BigInteger[]{_0n, _0n};
        if (zz.compareTo(_1n) != 0)
            throw new ArithmeticException("invZ was invalid");
        return new BigInteger[]{ax, ay};
    }

    /**
     * 对tag进行utf8编码，得到bytes数组A，
     * 对A使用Sha256进行hash，得到的hash数组B
     * 数组B和自己连接，再和msg数组连接, 得到一个 [B + B + msg] 的数组C
     * 对C使用Sha256进行hash，得到的hash数组D
     * 返回D
     */
    private static byte[] taggedHash(String tag, byte[] msg) {
        byte[] tagHash = Sha256Hash.hash(tag.getBytes());
        byte[] doubleTagHash = Arrays.copyOf(tagHash, tagHash.length * 2);
        System.arraycopy(tagHash, 0, doubleTagHash, tagHash.length, tagHash.length);
        byte[] input = new byte[tagHash.length * 2 + msg.length];
        System.arraycopy(doubleTagHash, 0, input, 0, doubleTagHash.length);
        System.arraycopy(msg, 0, input, doubleTagHash.length, msg.length);
        return Sha256Hash.hash(input);
    }

    public static String genBtcTaprootAddressByPub(String pub, boolean mainnet) {
        byte[] pubBytes = Numeric.hexStringToByteArray(pub);
        if (pubBytes[0] == 0x03) {
            pubBytes[0] = 0x02;
        }
        ECKey ecKey = ECKey.fromPublicOnly(pubBytes);
        ECPoint pubKeyPoint = ecKey.getPubKeyPoint();
        byte[] x = pubKeyPoint.getXCoord().getEncoded();
        byte[] y = pubKeyPoint.getYCoord().getEncoded();
        byte[] t = taggedHash("TapTweak", x);
        ECKey tKey = ECKey.fromPrivate(t);
        byte[] tweakX = tKey.getPubKeyPoint().getXCoord().getEncoded();
        byte[] tweakY = tKey.getPubKeyPoint().getYCoord().getEncoded();
        BigInteger px = new BigInteger(1, x);
        BigInteger py = new BigInteger(1, y);
        BigInteger tx = new BigInteger(1, tweakX);
        BigInteger ty = new BigInteger(1, tweakY);
        BigInteger[] addRe = add(px, py, _1n, tx, ty, _1n);
        BigInteger[] toAffineRe = toAffine(addRe[0], addRe[1], addRe[2], null);
        String outKey = toAffineRe[0].toString(16);
        SegwitAddress segwitAddress = SegwitAddress.fromProgram(mainnet ? MainNetParams.get() : TestNet3Params.get(), 1, Numeric.hexStringToByteArray(outKey));
        return segwitAddress.toBech32();
    }

}
