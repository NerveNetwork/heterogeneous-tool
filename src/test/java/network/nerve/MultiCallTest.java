package network.nerve;

import network.nerve.heterogeneous.core.HtgWalletApi;
import network.nerve.heterogeneous.model.MultiCallModel;
import network.nerve.heterogeneous.model.MultiCallResult;
import network.nerve.heterogeneous.utils.EthFunctionUtil;
import network.nerve.heterogeneous.utils.ListUtil;
import org.junit.Before;
import org.junit.Test;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.http.HttpService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MultiCallTest {

    HtgWalletApi walletApi;

    //批量调用合约接口地址
    String multiCallAddress;

    public void initBsc(boolean prod) {
        String rpcAddress;
        int chainId;
        if(prod) {
            multiCallAddress = "0x07616A4fb60F854054137A7b9b5C3f8c8dd2dc01";
            rpcAddress = "https://bsc.mytokenpocket.vip";
            chainId = 56;
        }else {
            multiCallAddress = "0xFe73616F621d1C42b12CA14d2aB68Ed689d1D38B";
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
        if(prod) {
            multiCallAddress = "0xf5b4224Fae4f3900417e73Ea626f86476D2181f3";
            rpcAddress = "http://geth.nerve.network/";
            chainId = 1;
        }else {
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
        if(prod) {
            multiCallAddress = "0xd1F3BE686D64e1EA33fcF64980b65847aA43D79C";
            rpcAddress = "https://http-mainnet.hecochain.com";
            chainId = 128;
        }else {
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
        if(prod) {
            multiCallAddress = "0xd1F3BE686D64e1EA33fcF64980b65847aA43D79C";
            rpcAddress = "https://exchainrpc.okex.org";
            chainId = 66;
        }else {
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
        if(prod) {
            multiCallAddress = "0xd1F3BE686D64e1EA33fcF64980b65847aA43D79C";
            rpcAddress = "https://api.harmony.one/";
            chainId = 1666600000;
        }else {
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
        if(prod) {
            multiCallAddress = "0xd1F3BE686D64e1EA33fcF64980b65847aA43D79C";
            rpcAddress = "https://rpc-mainnet.matic.quiknode.pro";
            chainId = 137;
        }else {
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
        if(prod) {
            multiCallAddress = "0x4564512f7216a617BC8C8B1E0b2893C7CB17927e";
            rpcAddress = "https://rpc-mainnet.kcc.network";
            chainId = 321;
        }else {
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
        //initBsc(true);
        //initHt(true);
        //initOKex(true);
        //initHarmony(true);
        //initKcc();
        //initPolygon(true);
        initKcc(true);
    }



    /**
     * 查询erc20资产信息
     */
    @Test
    public void testQueryERE20Token() {
        //token地址
        String tokenAddress = "0x0039f574ee5cc39bdd162e9a88e3eb1f111baf48"; //USDT

        List<MultiCallModel> callList = new ArrayList<>();
        MultiCallModel m1 = new MultiCallModel(tokenAddress, EthFunctionUtil.getERC20NameFunction());
        MultiCallModel m2 = new MultiCallModel(tokenAddress, EthFunctionUtil.getERC20SymbolFunction());
        MultiCallModel m3 = new MultiCallModel(tokenAddress, EthFunctionUtil.getERC20DecimalFunction());
        callList.add(m1);
        callList.add(m2);
        callList.add(m3);

        try {
            MultiCallResult result = walletApi.multiCall(multiCallAddress, callList);
            if(result.getCallError() != null) {
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

    /**
     * 查询用户余额
     */
    @Test
    public void getBalance() {
        //用户地址
        String userAddress = "0x04f8e3b9a7de4d3f90a0bd34325c35433d94482d";
        //token地址
        List<String> tokenAddressList = new ArrayList<>();

        String[] arr = new String []{"0xd96f5a0dff6612695c115f188144af56dbd55ccf",
                "0xdac17f958d2ee523a2206206994597c13d831ec7"};


        tokenAddressList = ListUtil.of(arr);
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
            for (int i = 1; i < resultList.size(); i++) {
                typeList = resultList.get(i);
                uint256 = (Uint256) typeList.get(0);
                System.out.println("contract(" + callList.get(i).getContractAddress() + ")资产：" + uint256.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEthCall() {
        Web3j web3j = Web3j.build(new HttpService("https://api.s0.b.hmny.io/"));
        //token地址
        String tokenAddress = "0xd0a347e0ebea8f8efc26d539e17853c8e7a721c4"; //USDT
        Function function = EthFunctionUtil.getERC20NameFunction();
        String encodeFunctionData = FunctionEncoder.encode(function);
        Transaction tx = Transaction.createEthCallTransaction(Address.DEFAULT.getValue(), tokenAddress, encodeFunctionData);
        try {
            EthCall ethCall = web3j.ethCall(tx, DefaultBlockParameterName.PENDING).sendAsync().get();
            System.out.println(ethCall.getResult());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
