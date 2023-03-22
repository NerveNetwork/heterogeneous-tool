package network.nerve;

import com.google.protobuf.Any;
import com.jeongen.cosmos.CosmosTypeUrl;
import com.jeongen.cosmos.CosmosWalletApi;
import com.jeongen.cosmos.config.CosmosChainConfig;
import com.jeongen.cosmos.crypro.CosmosCredentials;
import com.jeongen.cosmos.util.ATOMUnitUtil;
import com.jeongen.cosmos.vo.SendInfo;
import cosmos.base.abci.v1beta1.Abci;
import cosmos.base.tendermint.v1beta1.Query;
import cosmos.staking.v1beta1.QueryOuterClass;
import cosmos.staking.v1beta1.Staking;
import cosmos.staking.v1beta1.Tx;
import cosmos.tx.v1beta1.ServiceOuterClass;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CosmosRestApiClientTest {

    private CosmosWalletApi cosmosApi;

    private String priKey;

    @Before
    public void before() {
        List<String> apiUrlList = new ArrayList<>();
        apiUrlList.add("https://rest.mainnet.crypto.org");
        apiUrlList.add("https://cryptocom-api.polkachu.com");
        apiUrlList.add("https://rest-cryptoorgchain.ecostake.com");
        apiUrlList.add("https://api-cryptoorgchain-ia.cosmosia.notional.ventures");

        //cosmosApi = new CosmosWalletApi(CosmosChainConfig.cosmos);
        //cosmosApi = new CosmosWalletApi(CosmosChainConfig.kava);
        //cosmosApi = new CosmosWalletApi(CosmosChainConfig.kava_test);
        cosmosApi = new CosmosWalletApi(apiUrlList, CosmosChainConfig.cro);

        //kava key
        //priKey = "74830feb34efd850875b899157e978325860eb445091e428e54143560b07eccd4f04";
        //cro key
        priKey = "7ce617815b0e2f570d0c7eb77339d85fbdaf132f389ee5a2d1f9a30c05861b45";
    }

    @Test
    public void lastBlockHeight() {
        try {
            System.out.println(cosmosApi.getLatestHeight());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getNodeInfo() {
        try {
            Query.GetNodeInfoResponse response = cosmosApi.getInfo();
            System.out.println(response.getDefaultNodeInfo().getNetwork());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddress() {
        System.out.println(cosmosApi.getAddress(priKey));
        String pubKey = "036cd8979b36f54e3a4482423e783a02a7f0da187ce82a2587f324d97b86c7622a";
        System.out.println(cosmosApi.getAddressByPubKey(pubKey));
    }

    @Test
    public void testValidateAddress() {
        String address = "kava1sunalksjd69ap92vvtwwl9lr306lpe4tfz46kg";
        //String address = "cosmos1sunalksjd69ap92vvtwwl9lr306lpe4t4hp8q0";
        System.out.println(cosmosApi.validateAddress(address));
    }


    @Test
    public void getAtomBalance() {
        //String address = "kava1sunalksjd69ap92vvtwwl9lr306lpe4tfz46kg";
        //String address = "cosmos1sunalksjd69ap92vvtwwl9lr306lpe4t4hp8q0";
        String address = "cro17u63qdx6tn2nn364phx8k06jgavrrmxghekwrn";
        try {
            System.out.println(cosmosApi.getAtomBalance(address).toPlainString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getTokenBalance() {
        //String address = "kava1sunalksjd69ap92vvtwwl9lr306lpe4tfz46kg";
        String address = "cosmos1m7uyxn26sz6w4755k6rch4dc2fj6cmzajkszvn";
        // token = uosmo
        String tokenAddress = "14F9BC3E44B8A9C1BE1FB08980FAB87034C9905EF17CF2F5008FC085218811CC";
        try {
            System.out.println(cosmosApi.getTokenBalance(address, tokenAddress));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getTx() {
        //FBDA733EFD449E73AB631FF9DFD96C5004397E83E7F71A069804CAFD4F046C1F    sendMultiTx
        //666A7D88DEBA5F85754C34A9EB3CDDD6C84356EE5DD080701F84217F231BE9DA    delegate
        //E6B53A1AA873C0DEA27D3D430DF539AC29F709AD2BE5E70AACC1108585EBE2B1    unDelegate
        String txHash = "24D5FDE0A5EAFD83385098277ABE84EF030845F346AE34BECE92892729D18BD0";
        try {
            ServiceOuterClass.GetTxResponse response = cosmosApi.getTx(txHash);
            int code = response.getTxResponse().getCode();
            if (code != 0) {
                //只要code !=0 ，就表示交易虽然上链了，但是执行失败，具体失败原因,见rawLog
                System.out.println(response.getTxResponse().getRawLog());
            }
            String typeUrl = response.getTx().getBody().getMessages(0).getTypeUrl();
            if (typeUrl.equals(CosmosTypeUrl.DELEGATE.getType())) {
                Any any = response.getTx().getBody().getMessages(0);
                Tx.MsgDelegate delegate = any.unpack(Tx.MsgDelegate.class);
                System.out.println(delegate.getValidatorAddress());
            }

            System.out.println(response.getTxResponse().getTxhash());
        } catch (Exception e) {
            String errorMsg = e.getMessage();
            if (errorMsg.indexOf("tx not found") != -1) {
                System.out.println("交易未找到");
            } else {
                //其他错误，排查网络连接以外的错误提示，都提示为查询失败
                e.printStackTrace();
            }
        }
    }

    /**
     * 单笔转账
     */
    @Test
    public void testSendTx() {
        //String toAddress = "cosmos17u63qdx6tn2nn364phx8k06jgavrrmxg0z7hlz";
        //String toAddress = "kava17u63qdx6tn2nn364phx8k06jgavrrmxgnh22f9";
        String toAddress = "cro17u63qdx6tn2nn364phx8k06jgavrrmxghekwrn";
        // 私钥生成公钥、地址
        byte[] privateKey = Hex.decode(priKey);
        CosmosCredentials credentials = CosmosCredentials.create(privateKey, cosmosApi.getAddressUtil());
        String memo = "";
        // 转账地址
        System.out.println("address:" + credentials.getAddress());

        SendInfo sendInfo = SendInfo.builder()
                .credentials(credentials)
                .toAddress(toAddress)
                .amount(new BigDecimal("0.01"))
                .demon(cosmosApi.getApiClient().getTokenDemon())
                .build();
        try {
            Abci.TxResponse txResponse = cosmosApi.sendTransferTx(credentials, sendInfo, memo);
            System.out.println(txResponse.getTxhash());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSendMultiTx() {
        //String toAddress = "cosmos17u63qdx6tn2nn364phx8k06jgavrrmxg0z7hlz";
        String toAddress = "kava17u63qdx6tn2nn364phx8k06jgavrrmxgnh22f9";
        // 私钥生成公钥、地址
        byte[] privateKey = Hex.decode(priKey);
        CosmosCredentials credentials = CosmosCredentials.create(privateKey, cosmosApi.getAddressUtil());
        // 获取地址
        System.out.println("address:" + credentials.getAddress());
        List<SendInfo> sendList = new ArrayList<>();
        sendList.add(SendInfo.builder().credentials(credentials).toAddress(toAddress).amount(new BigDecimal("1")).demon(cosmosApi.getApiClient().getTokenDemon()).build());
        sendList.add(SendInfo.builder().credentials(credentials).toAddress(toAddress).amount(new BigDecimal("1")).demon(cosmosApi.getApiClient().getTokenDemon()).build());

        try {
            // 生成、签名、广播交易
            Abci.TxResponse txResponse = cosmosApi.sendMultiTransferTx(credentials, sendList);
            System.out.println(txResponse.getTxhash());
        } catch (Exception e) {
            e.printStackTrace();
        }

//
        // 获取指定高度的交易
//        ServiceOuterClass.GetTxsEventResponse txsEventByHeight = cosmosRestApiClient.getTxsEventByHeight(10099441L, "");
//        System.out.println(txsEventByHeight);
    }

    @Test
    public void queryStakingValidators() {
        try {
            QueryOuterClass.QueryValidatorsResponse response = cosmosApi.queryStakingValidatorsByStatus(Staking.BondStatus.BOND_STATUS_BONDED);
            for (Staking.Validator validator : response.getValidatorsList()) {
                if (validator.getStatus() == Staking.BondStatus.BOND_STATUS_BONDED) {
                    System.out.println(validator.getDescription().getMoniker());
                }
                System.out.println(validator.getOperatorAddress() + " ----" + validator.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void queryStakingValidatorsByUser() {
        String address = "kava1sunalksjd69ap92vvtwwl9lr306lpe4tfz46kg";
        try {
            QueryOuterClass.QueryDelegatorDelegationsResponse response = cosmosApi.queryStakingValidatorsByUser(address);

            System.out.println(response.getDelegationResponsesCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDelegate() {
        String validator = "kavavaloper1pqfu5354t9les6ks8jlhv0dwe9esw95aw9hfy2";

        // 私钥生成公钥、地址
        byte[] privateKey = Hex.decode(priKey);
        CosmosCredentials credentials = CosmosCredentials.create(privateKey, cosmosApi.getAddressUtil());
        // 生成资产转移信息
        SendInfo sendInfo = SendInfo.builder()
                .credentials(credentials)
                .toAddress(validator)       //设置接收地址为验证节点人地址
                .amount(new BigDecimal("10"))
                .demon(cosmosApi.getApiClient().getTokenDemon())
                .build();
        try {
            // 发送质押交易
            Abci.TxResponse response = cosmosApi.sendDelegateTx(credentials, sendInfo);
            System.out.println(response.getTxhash());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUnDelegate() {
        String validator = "kavavaloper1pqfu5354t9les6ks8jlhv0dwe9esw95aw9hfy2";

        // 私钥生成公钥、地址
        byte[] privateKey = Hex.decode(priKey);
        CosmosCredentials credentials = CosmosCredentials.create(privateKey, cosmosApi.getAddressUtil());

        SendInfo sendInfo = SendInfo.builder()
                .credentials(credentials)
                .toAddress(validator)       //设置接收地址为验证节点人地址
                .amount(new BigDecimal("5"))
                .demon(cosmosApi.getApiClient().getTokenDemon())
                .build();

        try {
            Abci.TxResponse response = cosmosApi.sendUnDelegateTx(credentials, sendInfo);
            System.out.println(response.getTxhash());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void queryReward() {
        String validator = "kavavaloper1jl42l225565y3hm9dm4my33hjgdzleucqryhlx";
        String address = "kava1jwfatymv77dnkjqt2tuk2tp090cy6fcq93nuc3";

        try {
            cosmos.distribution.v1beta1.QueryOuterClass.QueryDelegationRewardsResponse response = cosmosApi.queryDelegationReward(address, validator);
            System.out.println(response.getRewards(0).getAmount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sentWithdrawRewardTx() {
        String validator = "kavavaloper1pqfu5354t9les6ks8jlhv0dwe9esw95aw9hfy2";

        // 私钥生成公钥、地址
        byte[] privateKey = Hex.decode(priKey);
        CosmosCredentials credentials = CosmosCredentials.create(privateKey, cosmosApi.getAddressUtil());

        try {
            Abci.TxResponse response = cosmosApi.sendWithdrawRewardTx(credentials, validator);
            System.out.println(response.getTxhash());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test() {
        String value = "548892";
        System.out.println(ATOMUnitUtil.nanoAtomToAtom(value).toPlainString());
    }
}