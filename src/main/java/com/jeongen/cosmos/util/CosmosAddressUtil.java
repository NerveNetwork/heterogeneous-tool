package com.jeongen.cosmos.util;

import org.bitcoinj.core.Bech32;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Utils;
import org.bitcoinj.crypto.ChildNumber;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CosmosAddressUtil {

    /**
     * 地址前缀
     */
    private String prefix;

    public CosmosAddressUtil(String prefix) {
        this.prefix = prefix;
    }

    public String publicKeyToAddress(byte[] publicKey) {
        byte[] pubKeyHash = Utils.sha256hash160(publicKey);
        return convertAndEncode(pubKeyHash);
    }

    public String ecKeyToAddress(ECKey ecKey) {
        byte[] pubKeyHash = ecKey.getPubKeyHash();
        return convertAndEncode(pubKeyHash);
    }

    public String convertAndEncode(byte[] pubKeyHash) {
        byte[] convertBits = CosmosAddressUtil.convertBits(pubKeyHash, 8, 5, true);
        return Bech32.encode(this.prefix, convertBits);
    }

    public boolean verifyCosmosAddress(String address) {
        if (address == null || address.trim().equals("")) {
            return false;
        }

        String subAddress = address.substring(prefix.length());
        if (subAddress.length() != 39) {
            return false;
        }

        Bech32.Bech32Data decodeData;
        try {
            decodeData = Bech32.decode(address);
        } catch (Exception e) {
            return false;
        }
        if (!prefix.equals(decodeData.hrp)) {
            return false;
        }
        if (decodeData.data.length != 32) {
            return false;
        }
        return true;
    }

    public static List<ChildNumber> decodePath(String path) {
        final int HIGHEST_BIT = 0x80000000;

        String[] splitPath = path.split("/");

        int start = 0;
        if (splitPath[0].equals("m")) {
            start = 1;
        }

        int[] result = new int[splitPath.length - start];
        for (int i = start; i < splitPath.length; i++) {
            String splitPathItem = splitPath[i];
            if (splitPathItem.endsWith("'")) {
                splitPathItem = splitPathItem.substring(0, splitPathItem.length() - 1);
                result[i - start] = Integer.parseInt(splitPathItem) + HIGHEST_BIT;
            } else {
                result[i - start] = Integer.parseInt(splitPathItem);
            }
        }

        List<ChildNumber> arr = new ArrayList<>();
        for (int j : result) {
            arr.add(new ChildNumber(j));
        }
        return arr;
    }

    private static byte[] convertBits(byte[] data, int fromBits, int toBits, boolean pad) {
        int acc = 0;
        int bits = 0;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int maxv = (1 << toBits) - 1;
        for (int i = 0; i < data.length; i++) {
            int value = data[i] & 0xff;
            if ((value >>> fromBits) != 0) {
                throw new RuntimeException("ERR_BAD_FORMAT invalid data range: data[" + i + "]=" + value + " (fromBits=" + fromBits + ")");
            }
            acc = (acc << fromBits) | value;
            bits += fromBits;
            while (bits >= toBits) {
                bits -= toBits;
                baos.write((acc >>> bits) & maxv);
            }
        }
        if (pad) {
            if (bits > 0) {
                baos.write((acc << (toBits - bits)) & maxv);
            }
        } else if (bits >= fromBits) {
            throw new RuntimeException("ERR_BAD_FORMAT illegal zero padding");
        } else if (((acc << (toBits - bits)) & maxv) != 0) {
            throw new RuntimeException("ERR_BAD_FORMAT non-zero padding");
        }
        return baos.toByteArray();
    }
}
