package network.nerve;

import network.nerve.heterogeneous.core.HtgWalletApi;
import network.nerve.heterogeneous.model.MultiCallModel;
import network.nerve.heterogeneous.model.MultiCallResult;
import network.nerve.heterogeneous.utils.EthFunctionUtil;
import network.nerve.heterogeneous.utils.ListUtil;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
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

    public void initAStar() {
        String rpcAddress;
        int chainId;
        multiCallAddress = "0xd1F3BE686D64e1EA33fcF64980b65847aA43D79C";
        rpcAddress = "https://astar.public.blastapi.io";
        chainId = 592;
        String symbol = "ASTR";
        String chainName = "Astar";
        walletApi = HtgWalletApi.getInstance(symbol, chainName, rpcAddress, chainId);
    }

    public void initXDC() {
        String rpcAddress = "https://rpc.xinfin.network";
        int chainId = 50;
        multiCallAddress = "xdcd1F3BE686D64e1EA33fcF64980b65847aA43D79C".toLowerCase();
        String symbol = "XDC";
        String chainName = "XDC";
        walletApi = HtgWalletApi.getInstance(symbol, chainName, rpcAddress, chainId);
    }

    public void initWan() {
        String symbol = "WAN";
        String chainName = "WanChain";
        int chainId = 888;
        multiCallAddress = "0x6899aA135037a4C8a3cAB11622d35CEa4CD63747";
        String rpcAddress = "https://gwan-ssl.wandevs.org:56891";
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

    public void initCelo() {
        String rpcAddress = "https://1rpc.io/celo";
        int chainId = 42220;
        multiCallAddress = "0xB3cb9Ec46bEDB9A85b79fBf52339de238a8e7f3e".toLowerCase();
        String symbol = "CELO";
        String chainName = "CELO";
        walletApi = HtgWalletApi.getInstance(symbol, chainName, rpcAddress, chainId);
    }

    @Before
    public void init() {
        //initEth(true);
        //initBsc(true);
        //initHt(true);
        //initOKex(true);
        //initHarmony(true);
        //initKcc();
        //initPolygon(true);
        //initKcc(true);
        //initWan();
        //initXDC();
        //initAStar();
    }

    /**
     * 查询erc20资产信息
     */
    @Test
    public void testQueryERE20Token() {
        //token地址
        String tokenAddress1 = "0x9DE0405064BEDd88399098b4fbb2f7fA462992E0"; //USDT
        String tokenAddress2 = "0x9DE0405064BEDdsdfsdfsdbb2f7ffsdfsdf92sf";  //NABOX
        String tokenAddress3 = "0x9DE0405064BEDd8839sfdsafdsfsdfsdaaaabbb"; //NULS

        BigInteger tokenId = BigInteger.ONE;
        List<MultiCallModel> callList = new ArrayList<>();
        MultiCallModel m1 = new MultiCallModel(tokenAddress1, EthFunctionUtil.getERC20NameFunction());
        MultiCallModel m2 = new MultiCallModel(tokenAddress2, EthFunctionUtil.getERC721TokenUrl(tokenId));
        MultiCallModel m3 = new MultiCallModel(tokenAddress3, EthFunctionUtil.getERC20DecimalFunction());
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
    public void testQueryERE20TokenApprove() throws Exception {
        initCelo();
        //token地址
        String celo = "0x471EcE3750Da237f93B8E339c536989b8978a438";
        String cUSD = "0x765DE816845861e75A25fCA122bb6898B8B1282a";
        String mentoLabsBrokerRouter = "0x777A8255cA72412f0d706dc03C9D1987306B4CaD";
        List<String> accList = IOUtils.readLines(new FileInputStream(new File("/Users/pierreluo/IdeaProjects/heterogeneous-tool/src/test/resources/acc1001_1100.txt")), StandardCharsets.UTF_8);
        List<MultiCallModel> callList = new ArrayList<>();
        for (String acc : accList) {
            String[] split = acc.split(",");
            String ac = split[1].trim();
            MultiCallModel m1 = new MultiCallModel(cUSD, EthFunctionUtil.getERC20AllowanceFunction(ac, mentoLabsBrokerRouter));
            callList.add(m1);
        }
        BigInteger approve = new BigInteger("4665640564039457584007913129639935");
        try {
            MultiCallResult result = walletApi.multiCall(multiCallAddress, callList);
            if (result.getCallError() != null) {
                System.out.println(result.getCallError().getMessage());
                return;
            }

            List<List<Type>> resultList = result.getMultiResultList();

            for (int i = 0; i < resultList.size(); i++) {
                List<Type> types = resultList.get(i);
                Uint256 uint256 = (Uint256) types.get(0);
                BigInteger allowance = uint256.getValue();
                if (allowance.compareTo(approve) < 0) {
                    String acc = accList.get(i);
                    System.out.println(String.format("acc: %s, celo allowance: %s", acc, allowance));
                }
            }
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

    @Test
    public void testQueryErc1155Token() {
        //token地址
        String tokenAddress = "0xf0ea56402b2e2b27556d7abf4236c7327722fe41";

        List<MultiCallModel> callList = new ArrayList<>();
        MultiCallModel m1 = new MultiCallModel(tokenAddress, EthFunctionUtil.getERC20NameFunction());
        MultiCallModel m2 = new MultiCallModel(tokenAddress, EthFunctionUtil.getERC20SymbolFunction());
        MultiCallModel m3 = new MultiCallModel(tokenAddress, EthFunctionUtil.getERC1155URI(BigInteger.ONE));
        callList.add(m1);
        callList.add(m2);
        callList.add(m3);

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
            if (bool.getValue()) {
                Utf8String string = (Utf8String) typeList.get(1);
                System.out.println("token uri: " + string.getValue());
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
        String userAddress = "0x6565f70d9a1eEEbCE196E29B7885DCd9cd877F6B".toLowerCase();
        //token地址
//        String[] arr = new String[]{"0x02e1afeef2a25eabd0362c4ba2dc6d20ca638151",
//                "0x05626a6b2f3c146ab0cd189cd0bc281f1db4e493"};

//        List<String> tokenAddressList = ListUtil.of(arr);
        List<MultiCallModel> callList = new ArrayList<>();

//        for (String tokenAddress : tokenAddressList) {
//            //查询其他资产时，callModel第一个参数就是token的合约地址
//            MultiCallModel m2 = new MultiCallModel(tokenAddress, EthFunctionUtil.queryEER20BalanceFunction(userAddress));
//            callList.add(m2);
//        }

        MultiCallModel m2 = new MultiCallModel(multiCallAddress, EthFunctionUtil.queryEthBalanceFunction(userAddress));
        callList.add(m2);
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

    public static Function getSenderStatus(String sender, String token) {
        return new Function(
                "getSenderStatus",
                Arrays.asList(new Address(sender), new Address(token)),
                Arrays.asList(
                        new TypeReference<Uint>() {}, // 对应 activeTimeStamp (uint256)
                        new TypeReference<Uint>() {}  // 对应 allowance (uint256)
                )
        );
    }

    @Test
    public void testAlphaData() {
        String sender = "0x0Fdb956B85630912f56d1cf7BE8aC2c923e407f7";
        String zkjToken = "0xC71B5F631354BE6853eFe9C3Ab6b9590F8302e81";
        //token地址
        String contract = "0x709e23ff3D44793716Cbce26941c18C17B37f791";

        List<MultiCallModel> callList = new ArrayList<>();
        MultiCallModel m1 = new MultiCallModel(contract, getSenderStatus(sender, zkjToken));
        MultiCallModel m2 = new MultiCallModel(contract, getSenderStatus(sender, zkjToken));
        MultiCallModel m3 = new MultiCallModel(contract, getSenderStatus(sender, zkjToken));
        MultiCallModel m4 = new MultiCallModel(contract, getSenderStatus(sender, zkjToken));
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
            for (List<Type> types : list) {
                for (int i = 0; i < 3; i++) {
                    System.out.print(types.get(i).getValue().toString() + ", ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
