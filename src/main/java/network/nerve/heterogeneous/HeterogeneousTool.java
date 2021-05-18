package network.nerve.heterogeneous;

import network.nerve.heterogeneous.context.BnbContext;
import network.nerve.heterogeneous.core.HtgWalletApi;
import network.nerve.heterogeneous.core.MetaMaskWalletApi;
import network.nerve.heterogeneous.utils.StringUtils;

/**
 * @author: Charlie
 * @date: 2021/5/18
 */
public class HeterogeneousTool {

    private String symbol;
    private String rpcAddress;
    private HtgWalletApi htgWalletApi;

    public HeterogeneousTool(String symbol, String rpcAddress) {
        this.symbol = symbol;
        this.rpcAddress = rpcAddress;
        htgWalletApi = HtgWalletApi.getInstance(symbol, rpcAddress);
    }


    public MetaMaskWalletApi metaMask() {
        return bnbWalletApi;
    }

    public static void initBSC() {
        initBSC(null);
    }
    public static void initBSC(String rpcAddress) {
        if(StringUtils.isNotBlank(rpcAddress)) {
            BnbContext.rpcAddress = rpcAddress;
            bnbWalletApi.restartApi(rpcAddress);
        }
        HeterogeneousTool.symbol = BnbContext.symbol;
        HeterogeneousTool.rpcAddress =  rpcAddress;
        initInstance();
    }

    private static void initInstance() {
        HtgWalletApi.getInstance(HeterogeneousTool.symbol, HeterogeneousTool.rpcAddress);
    }
}
