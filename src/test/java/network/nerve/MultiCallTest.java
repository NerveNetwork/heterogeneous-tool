package network.nerve;

import network.nerve.heterogeneous.HtgTool;
import network.nerve.heterogeneous.core.Api;
import network.nerve.heterogeneous.core.HtgWalletApi;
import network.nerve.heterogeneous.core.TrxWalletApi;
import network.nerve.heterogeneous.core.WalletApi;
import network.nerve.heterogeneous.model.HtgConfig;
import network.nerve.heterogeneous.model.MultiCallModel;
import network.nerve.heterogeneous.model.MultiCallResult;
import network.nerve.heterogeneous.utils.EthFunctionUtil;
import network.nerve.heterogeneous.utils.ListUtil;
import network.nerve.heterogeneous.utils.TrxUtil;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import org.junit.Before;
import org.junit.Test;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static okhttp3.ConnectionSpec.CLEARTEXT;

public class MultiCallTest {

    final CipherSuite[] INFURA_CIPHER_SUITES =
            new CipherSuite[] {
                    CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                    CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384,
                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384,
                    CipherSuite.TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256,
                    CipherSuite.TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256,

                    // Note that the following cipher suites are all on HTTP/2's bad cipher suites list.
                    // We'll
                    // continue to include them until better suites are commonly available. For example,
                    // none
                    // of the better cipher suites listed above shipped with Android 4.4 or Java 7.
                    CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,
                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA,
                    CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA,
                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA,
                    CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256,
                    CipherSuite.TLS_RSA_WITH_AES_256_GCM_SHA384,
                    CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA,
                    CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA,
                    CipherSuite.TLS_RSA_WITH_3DES_EDE_CBC_SHA,

                    // Additional INFURA CipherSuites
                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256,
                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384,
                    CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA256,
                    CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA256
            };
    final ConnectionSpec INFURA_CIPHER_SUITE_SPEC =
            new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .cipherSuites(INFURA_CIPHER_SUITES)
                    .build();

    Api walletApi;

    //批量调用合约接口地址
    String multiCallAddress;

    public void initBsc(boolean prod) {
        String symbol = "BNB";
        String chainName = "BSC";
        String rpcAddress;
        int chainId;
        if (prod) {
            multiCallAddress = "0x07616A4fb60F854054137A7b9b5C3f8c8dd2dc01";
            rpcAddress = "https://bsc.mytokenpocket.vip";
            chainId = 56;
            walletApi = HtgWalletApi.getInstance(symbol, chainName, rpcAddress, chainId);
        } else {
            multiCallAddress = "0x2e31a3FBE1796c1CeC99BD2F3E87c0f085d2afB1";
            rpcAddress = "https://data-seed-prebsc-1-s1.binance.org:8545/";
            chainId = 97;
            final OkHttpClient.Builder builder =
                    new OkHttpClient.Builder().proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 1087))).connectionSpecs(Arrays.asList(INFURA_CIPHER_SUITE_SPEC, CLEARTEXT));
            OkHttpClient okHttpClient = builder.build();
            Web3j web3j = Web3j.build(new HttpService(rpcAddress, okHttpClient));
            HtgWalletApi htgWalletApi = HtgWalletApi.getInstance(symbol, chainName, rpcAddress, chainId);
            htgWalletApi.setWeb3j(web3j);
            walletApi = htgWalletApi;
        }


    }

    public void initEth(boolean prod) {
        String rpcAddress;
        int chainId;
        if (prod) {
            multiCallAddress = "0xf5b4224Fae4f3900417e73Ea626f86476D2181f3";
            rpcAddress = "http://geth.nerve.network/";
            chainId = 1;
        } else {
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

    public void initTRON(boolean prod) {
        String rpcAddress;
        int chainId;
        if (prod) {
            multiCallAddress = "TGpKTqKQyV66T9SK6Fy3JLGfCyRkJNDJkX";
            rpcAddress = "5ed12db3-3462-459e-9fe0-510b875a3d2d";
            chainId = 100000002;
        } else {
            multiCallAddress = "TJfF8mmmy3Br1VvBygq16TSnnsiNL6LEBD";
            rpcAddress = "";
            chainId = 100000001;
        }

        String symbol = "TRX";
        String chainName = "TRON";
        walletApi = TrxWalletApi.getInstance(rpcAddress);
    }

    public void init() {
        initEth(true);
        //initBsc(false);
        //initHt(true);
        //initOKex(true);
        //initHarmony(true);
        //initKcc();
        // initPolygon(true);
        //initKcc(true);
        //initTRON(false);
    }


    /**
     * 查询erc20资产信息
     */

    public void testQueryERE20Token() {
        //token地址
        String tokenAddress = "0x02e1aFEeF2a25eAbD0362C4Ba2DC6d20cA638151"; //BUSD
        //String tokenAddress = "TXCWs4vtLW2wYFHfi7xWeiC9Kuj2jxpKqJ"; //USDT
        List<MultiCallModel> callList = new ArrayList<>();
        MultiCallModel m1 = new MultiCallModel(tokenAddress, EthFunctionUtil.getERC20NameFunction());
        MultiCallModel m2 = new MultiCallModel(tokenAddress, EthFunctionUtil.getERC20SymbolFunction());
        MultiCallModel m3 = new MultiCallModel(tokenAddress, EthFunctionUtil.getERC20DecimalFunction());
        MultiCallModel m4 = new MultiCallModel(tokenAddress, EthFunctionUtil.getTotalSupply());
        callList.add(m1);
        callList.add(m2);
        callList.add(m3);
        callList.add(m4);

        try {
            MultiCallResult result = walletApi.multiCall(multiCallAddress, callList);
            if (result.getCallError() != null) {
                System.out.println(result.getCallError().getMessage());
                return;
            }

            List<List<Type>> resultList = result.getMultiResultList();

            String assetName, symbol;
            int decimals;
            BigInteger totalSupply;

            List<Type> typeList = resultList.get(0);
            Utf8String nameString = (Utf8String) typeList.get(0);
            assetName = nameString.getValue();

            typeList = resultList.get(1);
            Utf8String symbolString = (Utf8String) typeList.get(0);
            symbol = symbolString.toString();

            typeList = resultList.get(2);
            Uint8 uint8 = (Uint8) typeList.get(0);
            decimals = uint8.getValue().intValue();

            typeList = resultList.get(3);
            Uint256 uint256 = (Uint256) typeList.get(0);
            totalSupply = uint256.getValue();

            System.out.println(assetName + "," + symbol + "," + decimals + "," + totalSupply);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testQueryErc1155Token() {
        initEth(true);
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

    public void testGetHeight() {
        String symbol = "TRON";
        String chainName = "TRON";
        int chainId = 100000002;
        String rpcAddress = "5ed12db3-3462-459e-9fe0-510b875a3d2d";
        HtgConfig htgConfig = new HtgConfig(symbol, chainName, chainId, rpcAddress);

        HtgTool.initOne(htgConfig);
        WalletApi walletApi = HtgTool.getWalletApi(chainId);

        try {
            long height = walletApi.getBlockHeight();
            System.out.println(height);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 查询用户余额
     */

    public void getErc20Balance() {
        initTRON(true);
        //用户地址
        String userAddress = "TThDV3hvxyuM9bzzvJj1shENAfXE1XyDji";
        //token地址
        List<String> tokenAddressList = new ArrayList<>();

//        String[] arr = new String[]{"TEzJjjC4NrLrYFthGFHzQon5zrErNw1JN9",
//                "TXCWs4vtLW2wYFHfi7xWeiC9Kuj2jxpKqJ"};
//        tokenAddressList = ListUtil.of(arr);

        List<MultiCallModel> callList = new ArrayList<>();
        //查询主资产的时候，callModel第一个参数为批量接口的合约地址
        MultiCallModel m1 = new MultiCallModel(multiCallAddress, EthFunctionUtil.queryEthBalanceFunction(userAddress));
        callList.add(m1);

//        for(String tokenAddress : tokenAddressList) {
//            //查询其他资产时，callModel第一个参数就是token的合约地址
//            MultiCallModel m2 = new MultiCallModel(tokenAddress, EthFunctionUtil.queryEER20BalanceFunction(userAddress));
//            callList.add(m2);
//        }

        try {
            MultiCallResult result = walletApi.multiCall(multiCallAddress, callList);
            List<List<Type>> resultList = result.getMultiResultList();

            //获取第一个，主资产
            List<Type> typeList = resultList.get(0);
            Uint256 uint256 = (Uint256) typeList.get(0);
            System.out.println("主资产：" + uint256.getValue());

            //其余的token资产，从第二条开始取值
//            for (int i = 1; i < resultList.size(); i++) {
//                typeList = resultList.get(i);
//                uint256 = (Uint256) typeList.get(0);
//                System.out.println("contract(" + callList.get(i).getContractAddress() + ")资产：" + uint256.getValue());
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void testQueryErc721Token() {
        //token地址
        String tokenAddress = "0x3E38Aa35790551AE73560658D6620e60908a3684";

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


    public void testEthCall() {
        Web3j web3j = Web3j.build(new HttpService("https://api.s0.b.hmny.io/"));
        //token地址
        String tokenAddress = "0xd0a347e0ebea8f8efc26d539e17853c8e7a721c4"; //USDT
        Function function = EthFunctionUtil.getERC20NameFunction();
        String encodeFunctionData = FunctionEncoder.encode(function);
        Transaction tx = Transaction.createEthCallTransaction(Address.DEFAULT.getValue(), tokenAddress, encodeFunctionData);
        try {
            EthCall ethCall = web3j.ethCall(tx, DefaultBlockParameterName.LATEST).sendAsync().get();
            System.out.println(ethCall.getResult());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    public void testErc721OwnerOf() {
        Web3j web3j = Web3j.build(new HttpService("https://data-seed-prebsc-1-s1.binance.org:8545/"));
        String tokenAddress = "0xE1C8E1f7e2D8afFfA5271456af454243C39B6542";

        Function function = EthFunctionUtil.getERC721OwnerOf(BigInteger.ZERO);
        String encodeFunctionData = FunctionEncoder.encode(function);
        Transaction tx = Transaction.createEthCallTransaction(Address.DEFAULT.getValue(), tokenAddress, encodeFunctionData);
        EthCall ethCall = null;
        try {
            ethCall = web3j.ethCall(tx, DefaultBlockParameterName.LATEST).sendAsync().get();
            System.out.println(ethCall.getResult());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }


    public void testQueryErc721() throws ExecutionException, InterruptedException {
        Web3j web3j = Web3j.build(new HttpService("https://data-seed-prebsc-1-s1.binance.org:8545/"));
        String tokenAddress = "0xE1C8E1f7e2D8afFfA5271456af454243C39B6542";

        long start, end;
        start = System.currentTimeMillis();
        Function function = EthFunctionUtil.getERC721OwnerOf(BigInteger.ZERO);
        String encodeFunctionData = FunctionEncoder.encode(function);
        Transaction tx = Transaction.createEthCallTransaction(Address.DEFAULT.getValue(), tokenAddress, encodeFunctionData);
        EthCall ethCall = web3j.ethCall(tx, DefaultBlockParameterName.LATEST).sendAsync().get();
        List<Type> resultList = FunctionReturnDecoder.decode(ethCall.getResult(), function.getOutputParameters());


        function = EthFunctionUtil.getERC20SymbolFunction();
        encodeFunctionData = FunctionEncoder.encode(function);
        tx = Transaction.createEthCallTransaction(Address.DEFAULT.getValue(), tokenAddress, encodeFunctionData);
        ethCall = web3j.ethCall(tx, DefaultBlockParameterName.LATEST).sendAsync().get();
        resultList = FunctionReturnDecoder.decode(ethCall.getResult(), function.getOutputParameters());

        function = EthFunctionUtil.queryEER20BalanceFunction("0x45cCF4B9F8447191C38F5134d8C58F874335028d");
        encodeFunctionData = FunctionEncoder.encode(function);
        tx = Transaction.createEthCallTransaction(Address.DEFAULT.getValue(), tokenAddress, encodeFunctionData);
        ethCall = web3j.ethCall(tx, DefaultBlockParameterName.LATEST).sendAsync().get();
        resultList = FunctionReturnDecoder.decode(ethCall.getResult(), function.getOutputParameters());

        function = EthFunctionUtil.getERC721Approved(BigInteger.ZERO);
        encodeFunctionData = FunctionEncoder.encode(function);
        tx = Transaction.createEthCallTransaction(Address.DEFAULT.getValue(), tokenAddress, encodeFunctionData);
        ethCall = web3j.ethCall(tx, DefaultBlockParameterName.LATEST).sendAsync().get();
        resultList = FunctionReturnDecoder.decode(ethCall.getResult(), function.getOutputParameters());

        function = EthFunctionUtil.getTotalSupply();
        encodeFunctionData = FunctionEncoder.encode(function);
        tx = Transaction.createEthCallTransaction(Address.DEFAULT.getValue(), tokenAddress, encodeFunctionData);
        ethCall = web3j.ethCall(tx, DefaultBlockParameterName.LATEST).sendAsync().get();
        resultList = FunctionReturnDecoder.decode(ethCall.getResult(), function.getOutputParameters());

        end = System.currentTimeMillis();

        System.out.println("------use:" + (end - start));
    }

}
