package network.nerve.heterogeneous.utils;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;

public class EthFunctionUtil {

    /**
     * 查询主资产余额
     * @param address 用户地址
     * @return
     */
    public static Function queryEthBalanceFunction(String address) {
        return new Function(
                "getEthBalance",
                ListUtil.of(new Address(address)),
                ListUtil.of(new TypeReference<Uint256>() {
                }));
    }

    /**
     * 查询erc20资产余额
     * @param address 用户地址
     * @return
     */
    public static Function queryEER20BalanceFunction(String address) {
        return new Function(
                "balanceOf",
                ListUtil.of(new Address(address)),
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
}
