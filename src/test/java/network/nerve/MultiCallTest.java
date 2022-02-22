package network.nerve;

import network.nerve.heterogeneous.BSCTool;
import network.nerve.heterogeneous.core.HtgWalletApi;
import network.nerve.heterogeneous.model.MultiCallModel;
import network.nerve.heterogeneous.model.MultiCallResult;
import network.nerve.heterogeneous.utils.EthFunctionUtil;
import network.nerve.heterogeneous.utils.ListUtil;
import org.junit.Before;
import org.junit.Test;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * 批量查询测试类
 */
public class MultiCallTest {

    HtgWalletApi walletApi;

    //批量调用合约接口地址
    String multiCallAddress;

    public void initBsc(boolean prod) {
        String rpcAddress;
        int chainId;
        if (prod) {
            multiCallAddress = "0x07616A4fb60F854054137A7b9b5C3f8c8dd2dc01";
            rpcAddress = "https://bsc.mytokenpocket.vip";
            chainId = 56;
        } else {
            multiCallAddress = "0x2e31a3FBE1796c1CeC99BD2F3E87c0f085d2afB1";
            rpcAddress = "https://data-seed-prebsc-1-s1.binance.org:8545/";
            chainId = 97;
        }

        String symbol = "BNB";
        String chainName = "BSC";
        walletApi = HtgWalletApi.getInstance(symbol, chainName, rpcAddress, chainId);

    }

    public void initEth(boolean prod) {
        String rpcAddress;
        int chainId;
        if (prod) {
            multiCallAddress = "0xf5b4224Fae4f3900417e73Ea626f86476D2181f3";
            rpcAddress = "http://geth.nerve.network/";
            chainId = 1;
        } else {
            // rinkeby: 0x9EF66192D30019F7ED8C5C616e8CDBeE878CB522
            // ropsten: 0x47c323E4F1845476A133D75e59163F8f11E1Ef74
            multiCallAddress = "0x47c323E4F1845476A133D75e59163F8f11E1Ef74";
            rpcAddress = "https://ropsten.infura.io/v3/7e086d9f3bdc48e4996a3997b33b032f";
            chainId = 3;
        }

        String symbol = "ETH";
        String chainName = "Ethereum";
        walletApi = HtgWalletApi.getInstance(symbol, chainName, rpcAddress, chainId);
    }

    public void initHt(boolean prod) {
        String rpcAddress;
        int chainId;
        if (prod) {
            multiCallAddress = "0xd1F3BE686D64e1EA33fcF64980b65847aA43D79C";
            rpcAddress = "https://http-mainnet.hecochain.com";
            chainId = 128;
        } else {
            multiCallAddress = "0x4564512f7216a617BC8C8B1E0b2893C7CB17927e";
            rpcAddress = "https://http-testnet.hecochain.com";
            chainId = 256;
        }

        String symbol = "HT";
        String chainName = "Heco";
        walletApi = HtgWalletApi.getInstance(symbol, chainName, rpcAddress, chainId);
    }

    public void initOKex(boolean prod) {
        String rpcAddress;
        int chainId;
        if (prod) {
            multiCallAddress = "0xd1F3BE686D64e1EA33fcF64980b65847aA43D79C";
            rpcAddress = "https://exchainrpc.okex.org";
            chainId = 66;
        } else {
            multiCallAddress = "0x0111E01E78af5608e33569Edd997Fe2f700A0721";
            rpcAddress = "https://exchaintestrpc.okex.org";
            chainId = 65;
        }
        String symbol = "OKT";
        String chainName = "OKExChain";
        walletApi = HtgWalletApi.getInstance(symbol, chainName, rpcAddress, chainId);
    }

    public void initHarmony(boolean prod) {
        String rpcAddress;
        int chainId;
        if (prod) {
            multiCallAddress = "0xd1F3BE686D64e1EA33fcF64980b65847aA43D79C";
            rpcAddress = "https://api.harmony.one/";
            chainId = 1666600000;
        } else {
            multiCallAddress = "0x767188de0CE73c8771E72c4caF4a18De2303DF01";
            rpcAddress = "https://api.s0.pops.one/";
            chainId = 1666700000;
        }
        String symbol = "ONE";
        String chainName = "Harmony";
        walletApi = HtgWalletApi.getInstance(symbol, chainName, rpcAddress, chainId);
    }

    public void initPolygon(boolean prod) {
        String rpcAddress;
        int chainId;
        if (prod) {
            multiCallAddress = "0xd1F3BE686D64e1EA33fcF64980b65847aA43D79C";
            rpcAddress = "https://rpc-mainnet.matic.network";
            chainId = 137;
        } else {
            multiCallAddress = "0x4D3B8eFcC04cA63Be112Da5147C80c87aC969F5B";
            rpcAddress = "https://matic-mumbai.chainstacklabs.com";
            chainId = 80001;
        }

        String symbol = "MATIC";
        String chainName = "Polygon";
        walletApi = HtgWalletApi.getInstance(symbol, chainName, rpcAddress, chainId);
    }

    public void initKcc(boolean prod) {
        String rpcAddress;
        int chainId;
        if (prod) {
            multiCallAddress = "0x4564512f7216a617BC8C8B1E0b2893C7CB17927e";
            rpcAddress = "https://rpc-mainnet.kcc.network";
            chainId = 321;
        } else {
            multiCallAddress = "0x0111E01E78af5608e33569Edd997Fe2f700A0721";
            rpcAddress = "https://rpc-testnet.kcc.network";
            chainId = 322;
        }

        String symbol = "KCS";
        String chainName = "KCC";
        walletApi = HtgWalletApi.getInstance(symbol, chainName, rpcAddress, chainId);
    }

    @Before
    public void init() {
        //initEth(true);
        initBsc(false);
        //initHt(true);
        //initOKex(true);
        //initHarmony(true);
        //initKcc();
        //initPolygon(true);
        //initKcc(true);
    }

    /**
     * 查询erc20资产信息
     */
    @Test
    public void testQueryERE20Token() {
        //token地址
        String tokenAddress = "0x7c5ece743b5368e7691af6b2b5804821890952ff"; //USDT

        List<MultiCallModel> callList = new ArrayList<>();
        MultiCallModel m1 = new MultiCallModel(tokenAddress, EthFunctionUtil.getERC20NameFunction());
        MultiCallModel m2 = new MultiCallModel(tokenAddress, EthFunctionUtil.getERC20SymbolFunction());
        MultiCallModel m3 = new MultiCallModel(tokenAddress, EthFunctionUtil.getERC20DecimalFunction());
        callList.add(m1);
        callList.add(m2);
        callList.add(m3);

        try {
            MultiCallResult result = walletApi.multiCall(multiCallAddress, callList);
            if (result.getCallError() != null) {
                System.out.println(result.getCallError().getMessage());
                return;
            }

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


    @Test
    public void testQueryErc721Token() {
        //token地址
        String tokenAddress = "0xE1C8E1f7e2D8afFfA5271456af454243C39B6542";

        List<MultiCallModel> callList = new ArrayList<>();
        MultiCallModel m1 = new MultiCallModel(tokenAddress, EthFunctionUtil.getERC20NameFunction());
        MultiCallModel m2 = new MultiCallModel(tokenAddress, EthFunctionUtil.getERC20SymbolFunction());
        //有的NFT资产查询URL地址函数名是getTokenUrl，有个是getTokenMetadata
        MultiCallModel m3 = new MultiCallModel(tokenAddress, EthFunctionUtil.getERC721TokenUrl(BigInteger.ZERO));
        MultiCallModel m4 = new MultiCallModel(tokenAddress, EthFunctionUtil.getTokenMetadata(BigInteger.ZERO));
        callList.add(m1);
        callList.add(m2);
        callList.add(m3);
        callList.add(m4);

        try {
            MultiCallResult result = walletApi.tryMultiCall(multiCallAddress, callList);
            if (result.getCallError() != null) {
                System.out.println(result.getCallError().getMessage());
                return;
            }
            List<List<Type>> list = result.getMultiResultList();
            //解析返回的list时，一定要按照添加批量查询时的顺序来
            List<Type> typeList = list.get(0);
            //typeList[0]是一个boolean值，表示接口是否有值返回，typeList[1]才是具体返回值
            Bool bool = (Bool) typeList.get(0);
            if (bool.getValue()) {
                Utf8String string = (Utf8String) typeList.get(1);
                System.out.println("token name: " + string.getValue());
            }

            typeList = list.get(1);
            bool = (Bool) typeList.get(0);
            if (bool.getValue()) {
                Utf8String string = (Utf8String) typeList.get(1);
                System.out.println("token symbol: " + string.getValue());
            }

            typeList = list.get(2);
            bool = (Bool) typeList.get(0);
            if (bool.getValue()) {
                Utf8String string = (Utf8String) typeList.get(1);
                System.out.println("tokenUrl: " + string.getValue());
            }

            typeList = list.get(3);
            bool = (Bool) typeList.get(0);
            if (bool.getValue()) {
                Utf8String string = (Utf8String) typeList.get(1);
                System.out.println("tokenMeta: " + string.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询用户余额
     */
    @Test
    public void getErc20Balance() {
        //用户地址
        String userAddress = "0x45cCF4B9F8447191C38F5134d8C58F874335028d";
        //token地址
        String[] arr = new String[]{"0x02e1afeef2a25eabd0362c4ba2dc6d20ca638151",
                "0x05626a6b2f3c146ab0cd189cd0bc281f1db4e493"};

        List<String> tokenAddressList = ListUtil.of(arr);
        List<MultiCallModel> callList = new ArrayList<>();

        for (String tokenAddress : tokenAddressList) {
            //查询其他资产时，callModel第一个参数就是token的合约地址
            MultiCallModel m2 = new MultiCallModel(tokenAddress, EthFunctionUtil.queryEER20BalanceFunction(userAddress));
            callList.add(m2);
        }

        try {
            MultiCallResult result = walletApi.multiCall(multiCallAddress, callList);
            List<List<Type>> resultList = result.getMultiResultList();

            List<Type> typeList;
            Uint256 uint256;
            //其余的token资产，从第二条开始取值
            for (int i = 0; i < resultList.size(); i++) {
                typeList = resultList.get(i);
                uint256 = (Uint256) typeList.get(0);
                System.out.println("contract(" + callList.get(i).getContractAddress() + ")资产：" + uint256.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
