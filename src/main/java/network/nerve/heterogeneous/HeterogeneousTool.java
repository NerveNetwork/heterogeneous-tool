package network.nerve.heterogeneous;

import network.nerve.heterogeneous.core.HtgWalletApi;

/**
 * @author: Charlie
 * @date: 2021/5/18
 */
public class HeterogeneousTool {

    private String symbol;
    private String chainName;
    private String rpcAddress;
    private HtgWalletApi htgWalletApi;

    public HeterogeneousTool(String symbol, String chainName, String rpcAddress) {
        this.symbol = symbol;
        this.chainName = chainName;
        this.rpcAddress = rpcAddress;
        htgWalletApi = HtgWalletApi.getInstance(this.symbol, this.chainName, this.rpcAddress);
    }

//
//    public MetaMaskWalletApi metaMask() {
//        return bnbWalletApi;
//    }
//
//    public static void initBSC() {
//        initBSC(null);
//    }
//    public static void initBSC(String rpcAddress) {
//        if(StringUtils.isNotBlank(rpcAddress)) {
//            BnbContext.rpcAddress = rpcAddress;
//            bnbWalletApi.restartApi(rpcAddress);
//        }
//        HeterogeneousTool.symbol = BnbContext.symbol;
//        HeterogeneousTool.rpcAddress =  rpcAddress;
//        initInstance();
//    }
//
//    private static void initInstance() {
//        HtgWalletApi.getInstance(HeterogeneousTool.symbol, HeterogeneousTool.rpcAddress);
//    }
}
