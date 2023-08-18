package network.nerve.heterogeneous.utils;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class EthFunctionUtil {

    /**
     * 查询主资产余额
     *
     * @param address 用户地址
     * @return
     */
    public static Function queryEthBalanceFunction(String address) {
        return new Function(
                "getEthBalance",
                ListUtil.of(new Address(TrxUtil.trxAddress2eth(address))),
                ListUtil.of(new TypeReference<Uint256>() {
                }));
    }

    /**
     * 查询erc20资产余额
     *
     * @param address 用户地址
     * @return
     */
    public static Function queryEER20BalanceFunction(String address) {
        return new Function(
                "balanceOf",
                ListUtil.of(new Address(TrxUtil.trxAddress2eth(address))),
                ListUtil.of(new TypeReference<Uint256>() {
                }));
    }

    //获取erc20资产符号
    public static Function getERC20SymbolFunction() {
        return new Function(
                "symbol",
                ListUtil.of(),
                ListUtil.of(new TypeReference<Utf8String>() {
                }));
    }

    //获取erc20资产名称
    public static Function getERC20NameFunction() {
        return new Function(
                "name",
                ListUtil.of(),
                ListUtil.of(new TypeReference<Utf8String>() {
                }));
    }

    //获取erc20资产小数位
    public static Function getERC20DecimalFunction() {
        return new Function(
                "decimals",
                ListUtil.of(),
                ListUtil.of(new TypeReference<Uint8>() {
                }));
    }

    //获取erc20转账函数
    public static Function getERC20TransferFunction(String to, BigInteger value) {
        return new Function(
                "transfer",
                Arrays.asList(new Address(TrxUtil.trxAddress2eth(to)), new Uint256(value)),
                Arrays.asList(new TypeReference<Type>() {
                }));
    }

    //获取erc20转账函数
    public static Function getERC20TransferFromFunction(String from, String to, BigInteger value) {
        return new Function(
                "transferFrom",
                Arrays.asList(new Address(from), new Address(to), new Uint256(value)),
                Arrays.asList(new TypeReference<Type>() {
                }));
    }

    //查询erc721资产的url地址
    public static Function getERC721TokenUrl(BigInteger tokenId) {
        return new Function(
                "tokenURI",
                ListUtil.of(new Uint256(tokenId)),
                ListUtil.of(new TypeReference<Utf8String>() {
                }));
    }

    //查询erc1155资产的url地址
    public static Function getERC1155URI(BigInteger tokenId) {
        return new Function(
                "uri",
                ListUtil.of(new Uint256(tokenId)),
                ListUtil.of(new TypeReference<Utf8String>() {
                }));
    }

    //查询erc721资产的url地址
    public static Function getTokenMetadata(BigInteger tokenId) {
        return new Function(
                "tokenMetaData",
                ListUtil.of(new Uint256(tokenId)),
                ListUtil.of(new TypeReference<Utf8String>() {
                }));
    }

    public static Function getERC721OwnerOf(BigInteger tokenId) {
        return new Function(
                "ownerOf",
                ListUtil.of(new Uint256(tokenId)),
                ListUtil.of(new TypeReference<Address>() {
                }));
    }

    public static Function getERC721Approved(BigInteger tokenId) {
        return new Function(
                "getApproved",
                ListUtil.of(new Uint256(tokenId)),
                ListUtil.of(new TypeReference<Address>() {
                }));
    }

    public static Function getERC721TransferFunction(String from, String to, BigInteger tokenId, String data) {
        DynamicBytes dynamicBytes;
        if (StringUtils.isBlank(data)) {
            dynamicBytes = new DynamicBytes(new byte[]{});
        } else {
            dynamicBytes = new DynamicBytes(HexUtil.decode(data));
        }
        return new Function(
                "safeTransferFrom",
                Arrays.asList(new Address(from), new Address(to), new Uint256(tokenId), dynamicBytes),
                Arrays.asList(new TypeReference<Type>() {
                }));
    }

    public static Function getERC1155TransferFunction(String from, String to, List<Uint256> tokenIdList, List<Uint256> values, String data) {
        DynamicBytes dynamicBytes;
        if (StringUtils.isBlank(data)) {
            dynamicBytes = new DynamicBytes(new byte[]{});
        } else {
            dynamicBytes = new DynamicBytes(HexUtil.decode(data));
        }
        DynamicArray<Uint256> tokenIds = new DynamicArray<>(Uint256.class, tokenIdList);
        DynamicArray<Uint256> tokenValues = new DynamicArray<>(Uint256.class, values);

        List params = ListUtil.of(new Address(from), new Address(to), tokenIds, tokenValues, dynamicBytes);
        return new Function(
                "safeBatchTransferFrom",
                params,
                Arrays.asList(new TypeReference<Uint256>() {
                }));
    }


    public static Function getTotalSupply() {
        return new Function(
                "totalSupply",
                ListUtil.of(),
                ListUtil.of(new TypeReference<Uint256>() {
                }));
    }
}
