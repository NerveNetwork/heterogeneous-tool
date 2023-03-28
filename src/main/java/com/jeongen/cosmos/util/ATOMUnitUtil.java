package com.jeongen.cosmos.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

// 1 atom = 1,000,000 uATOM
// 	atom  = "atom"  // 1 (base denom unit)
//	matom = "matom" // 10^-3 (milli)
//	uatom = "uatom" // 10^-6 (micro)
//	natom = "natom" // 10^-9 (nano)
// https://blog.cosmos.network/phase-ii-initiated-cosmos-atom-transfers-enabled-by-governance-831a7e555ab6
public class ATOMUnitUtil {

    private int decimals;

    public ATOMUnitUtil(int decimals) {
        this.decimals = decimals;
    }

    // uatom 即 micro atom
    public BigDecimal microAtomToAtom(String uatomString) {
        BigDecimal uatom = new BigDecimal(uatomString);
        return uatom.movePointLeft(decimals).stripTrailingZeros();
    }

    public BigDecimal microAtomToAtom(BigInteger uatomBigInteger) {
        BigDecimal uatom = new BigDecimal(uatomBigInteger);
        return uatom.movePointLeft(decimals).stripTrailingZeros();
    }

    public BigDecimal atomToMicroAtom(String atomVal) {
        BigDecimal atom = new BigDecimal(atomVal);
        return atomToMicroAtom(atom);
    }

    public BigDecimal atomToMicroAtom(BigDecimal atom) {
        return atom.movePointRight(decimals).stripTrailingZeros();
    }

    public BigInteger atomToMicroAtomBigInteger(BigDecimal atom) {
        BigDecimal bigDecimal = atom.movePointRight(decimals);
        if (getNumberOfDecimalPlaces(bigDecimal) != 0) {
            throw new RuntimeException("atom to uAtom: 转换成整数后，含有小数点:" + bigDecimal);
        }
        // 忽略小数位
        return bigDecimal.toBigInteger();
    }

    // 小数位位数
    public int getNumberOfDecimalPlaces(BigDecimal bigDecimal) {
        String string = bigDecimal.stripTrailingZeros().toPlainString();
        int index = string.indexOf(".");
        return index < 0 ? 0 : string.length() - index - 1;
    }

    // uatom 即 micro atom
    public BigDecimal nanoAtomToAtom(String uatomString) {
        BigDecimal uatom = new BigDecimal(uatomString);
        return uatom.movePointLeft(decimals + 3).stripTrailingZeros().setScale(decimals, RoundingMode.UP);
    }

    public BigDecimal nanoAtomToAtom(BigInteger uatomBigInteger) {
        BigDecimal uatom = new BigDecimal(uatomBigInteger);
        return uatom.movePointLeft(decimals + 3).stripTrailingZeros().setScale(decimals, RoundingMode.UP);
    }

    public BigDecimal atomToNanoAtom(String atomVal) {
        BigDecimal atom = new BigDecimal(atomVal);
        return atomToNanoAtom(atom);
    }

    public BigDecimal atomToNanoAtom(BigDecimal atom) {
        return atom.movePointRight(decimals + 3).stripTrailingZeros();
    }

    public BigInteger atomToNanoAtomBigInteger(BigDecimal atom) {
        BigDecimal bigDecimal = atom.movePointRight(9);
        if (getNumberOfDecimalPlaces(bigDecimal) != 0) {
            throw new RuntimeException("atom to nanoAtom: 转换成整数后，含有小数点:" + bigDecimal);
        }
        // 忽略小数位
        return bigDecimal.toBigInteger();
    }

}
