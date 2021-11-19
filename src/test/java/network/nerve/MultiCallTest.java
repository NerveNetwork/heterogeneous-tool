package network.nerve;

import network.nerve.heterogeneous.core.HtgWalletApi;
import network.nerve.heterogeneous.model.MultiCallModel;
import network.nerve.heterogeneous.model.MultiCallResult;
import network.nerve.heterogeneous.utils.EthFunctionUtil;
import org.junit.Before;
import org.junit.Test;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;

import java.util.ArrayList;
import java.util.List;

public class MultiCallTest {

    HtgWalletApi walletApi;

    //批量调用合约接口地址
    String multiCallAddress = "0xBf69f8353Ac6eB9C1A794AEE9C869B3dFC511ea2";

    @Before
    public void init() {
        initBsc();
    }

    public void initBsc() {
        String rpcAddress = "https://data-seed-prebsc-1-s2.binance.org:8545/";
        String symbol = "BNB";
        int chainId = 97;
        String chainName = "BSC";
        walletApi = HtgWalletApi.getInstance(symbol, chainName, rpcAddress, chainId);
    }

    /**
     * 查询erc20资产信息
     */
    @Test
    public void testQueryERE20Token() {
        //token地址
        String tokenAddress = "0x6996b009e1a11b0278ff9c74061b5ac2346031c5"; //USDT

        List<MultiCallModel> callList = new ArrayList<>();
        MultiCallModel m1 = new MultiCallModel(tokenAddress, EthFunctionUtil.getERC20NameFunction());
        MultiCallModel m2 = new MultiCallModel(tokenAddress, EthFunctionUtil.getERC20SymbolFunction());
        MultiCallModel m3 = new MultiCallModel(tokenAddress, EthFunctionUtil.getERC20DecimalFunction());
        callList.add(m1);
        callList.add(m2);
        callList.add(m3);

        try {
            MultiCallResult result = walletApi.multiCall(multiCallAddress, callList);
            List<List<Type>> resultList = result.getMultiResultList();

            String assetName, symbol;
            int decimals;

            List<Type> typeList = resultList.get(0);
            Utf8String nameString = (Utf8String) typeList.get(0);
            assetName = nameString.getValue();

            typeList = resultList.get(1);
            Utf8String symbolString = (Utf8String) typeList.get(0);
            symbol = symbolString.toString();

            typeList = resultList.get(2);
            Uint8 uint8 = (Uint8) typeList.get(0);
            decimals = uint8.getValue().intValue();

            System.out.println(assetName + "," + symbol + "," + decimals);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询用户余额
     */
    @Test
    public void getBalance() {
        //用户地址
        String userAddress = "0x45cCF4B9F8447191C38F5134d8C58F874335028d";
        //token地址
        String usdtAddress = "0x6996b009e1a11b0278ff9c74061b5ac2346031c5"; //USDT
        String naboxAddress = "0x5bb4ddd9f1332dfb395b9b2dbc14b145ee73a77d";
        List<MultiCallModel> callList = new ArrayList<>();
        //查询主资产的时候，callModel第一个参数为批量接口的合约地址
        MultiCallModel m1 = new MultiCallModel(multiCallAddress, EthFunctionUtil.queryEthBalanceFunction(userAddress));
        //查询其他资产时，callModel第一个参数就是token的合约地址
        MultiCallModel m2 = new MultiCallModel(usdtAddress, EthFunctionUtil.queryEER20BalanceFunction(userAddress));
        MultiCallModel m3 = new MultiCallModel(naboxAddress, EthFunctionUtil.queryEER20BalanceFunction(userAddress));
        callList.add(m1);
        callList.add(m2);
        callList.add(m3);

        try {
            MultiCallResult result = walletApi.multiCall(multiCallAddress, callList);
            List<List<Type>> resultList = result.getMultiResultList();

            //获取第一个，主资产
            List<Type> typeList = resultList.get(0);
            Uint256 uint256 = (Uint256) typeList.get(0);
            System.out.println("主资产：" + uint256.getValue());

            //其余的token资产，从第二条开始取值
            for (int i = 1; i < resultList.size(); i++) {
                typeList = resultList.get(i);
                uint256 = (Uint256) typeList.get(0);
                System.out.println("contract(" + callList.get(i).getContractAddress()+")资产：" + uint256.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
