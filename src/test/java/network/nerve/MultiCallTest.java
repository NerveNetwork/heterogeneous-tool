package network.nerve;

import network.nerve.heterogeneous.core.HtgWalletApi;
import network.nerve.heterogeneous.model.MultiCallModel;
import network.nerve.heterogeneous.model.MultiCallResult;
import network.nerve.heterogeneous.utils.EthFunctionUtil;
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

    public void initBsc() {
        multiCallAddress = "0xBf69f8353Ac6eB9C1A794AEE9C869B3dFC511ea2";
        String rpcAddress = "https://data-seed-prebsc-1-s2.binance.org:8545/";
        String symbol = "BNB";
        int chainId = 97;
        String chainName = "BSC";
        walletApi = HtgWalletApi.getInstance(symbol, chainName, rpcAddress, chainId);
    }

    public void initEth() {
        multiCallAddress = "0x1a805559fdcea613c6cd92d46d0e92daf50fa10c";
        String rpcAddress = "https://ropsten.infura.io/v3/7e086d9f3bdc48e4996a3997b33b032f";
        String symbol = "ETH";
        int chainId = 3;
        String chainName = "Ethereum";
        walletApi = HtgWalletApi.getInstance(symbol, chainName, rpcAddress, chainId);
    }

    public void initHt() {
        multiCallAddress = "0x2284f11fbf29fc6669332ceaf78488abf5b0bff7";
        String rpcAddress = "https://http-testnet.hecochain.com";
        String symbol = "HT";
        int chainId = 256;
        String chainName = "Heco";
        walletApi = HtgWalletApi.getInstance(symbol, chainName, rpcAddress, chainId);
    }

    public void initOKex() {
        multiCallAddress = "0x2284f11fbf29fc6669332ceaf78488abf5b0bff7";
        String rpcAddress = "https://exchaintestrpc.okex.org";
        String symbol = "OKT";
        int chainId = 65;
        String chainName = "OKExChain";
        walletApi = HtgWalletApi.getInstance(symbol, chainName, rpcAddress, chainId);
    }

    public void initHarmony() {
        multiCallAddress = "0x38C075c42fbc18Ac0c2AC6910425e0004986100A";
        String rpcAddress = "https://api.s0.b.hmny.io/";
        String symbol = "ONE";
        int chainId = 1666700000;
        String chainName = "Harmony";
        walletApi = HtgWalletApi.getInstance(symbol, chainName, rpcAddress, chainId);
    }

    public void initPolygon() {
        multiCallAddress = "0x0ebc72a917f8d9e9f35cddb87176bf99806f423e";
        String rpcAddress = "https://matic-mumbai.chainstacklabs.com";
        String symbol = "MATIC";
        int chainId = 80001;
        String chainName = "Polygon";
        walletApi = HtgWalletApi.getInstance(symbol, chainName, rpcAddress, chainId);
    }

    public void initKcc() {
        multiCallAddress = "0x4d3b8efcc04ca63be112da5147c80c87ac969f5b";
        String rpcAddress = "https://rpc-testnet.kcc.network";
        String symbol = "KCS";
        int chainId = 322;
        String chainName = "KCC";
        walletApi = HtgWalletApi.getInstance(symbol, chainName, rpcAddress, chainId);
    }

    @Before
    public void init() {

        initKcc();
    }

    @Test
    public void testEthCall() {
        Web3j web3j = Web3j.build(new HttpService("https://api.s0.b.hmny.io/"));
        //token地址
        String tokenAddress = "0x04f8e3b9a7de4d3f90a0bd34325c35433d94482d"; //USDT
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

    /**
     * 查询erc20资产信息
     */
    @Test
    public void testQueryERE20Token() {
        //token地址
        String tokenAddress = "0x04f8e3b9a7de4d3f90a0bd34325c35433d94482d"; //USDT

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
                System.out.println("contract(" + callList.get(i).getContractAddress() + ")资产：" + uint256.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
