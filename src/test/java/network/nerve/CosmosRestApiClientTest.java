package network.nerve;

import com.google.protobuf.Any;
import com.jeongen.cosmos.CosmosTypeUrl;
import com.jeongen.cosmos.CosmosWalletApi;
import com.jeongen.cosmos.config.CosmosChainConfig;
import com.jeongen.cosmos.crypro.CosmosCredentials;
import com.jeongen.cosmos.vo.SendInfo;
import cosmos.base.abci.v1beta1.Abci;
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
        initCosmos();
    }

    private void initCRO() {
        List<String> apiUrlList = new ArrayList<>();
        apiUrlList.add("https://rest.mainnet.crypto.org");
        apiUrlList.add("https://cryptocom-api.polkachu.com");
        apiUrlList.add("https://rest-cryptoorgchain.ecostake.com");
        apiUrlList.add("https://api-cryptoorgchain-ia.cosmosia.notional.ventures");
        cosmosApi = new CosmosWalletApi(CosmosChainConfig.CRO, apiUrlList);
    }

    private void initINJ() {
        List<String> apiUrlList = new ArrayList<>();
        apiUrlList.add("https://injective-lcd.quickapi.com:443");
        apiUrlList.add("https://injective-api.polkachu.com");
        apiUrlList.add("https://api-injective-ia.cosmosia.notional.ventures/");

        cosmosApi = new CosmosWalletApi(CosmosChainConfig.INJ, apiUrlList);
    }

    private void initTerra() {
        List<String> apiUrlList = new ArrayList<>();
        apiUrlList.add("https://fcd.terrav2.ccvalidators.com:443/");
        apiUrlList.add("https://lcd-terra.wildsage.io:443/");
        apiUrlList.add("https://terra-mainnet-lcd.autostake.net:443");

        cosmosApi = new CosmosWalletApi(CosmosChainConfig.TERRA, apiUrlList);
    }

    private void initCosmos() {
        List<String> apiUrlList = new ArrayList<>();
        apiUrlList.add("https://api.cosmos.network");
        apiUrlList.add("https://cosmos-hub2-mainnet.token.im");
        apiUrlList.add("https://api-cosmoshub.pupmos.network");
        cosmosApi = new CosmosWalletApi(CosmosChainConfig.COSMOS, apiUrlList);
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
    public void testAddress() {
        System.out.println(cosmosApi.getAddress(priKey));
    }

    @Test
    public void testValidateAddress() {
        //String address = "kava1sunalksjd69ap92vvtwwl9lr306lpe4tfz46kg";
        //String address = "cosmos1sunalksjd69ap92vvtwwl9lr306lpe4t4hp8q0";
        String address = "inj1xzpl0mfx0h9yzvududqpcns9fke2rnf0pyn4w7";
        System.out.println(cosmosApi.validateAddress(address));
    }


    @Test
    public void getAtomBalance() {
        //String address = "kava1sunalksjd69ap92vvtwwl9lr306lpe4tfz46kg";
        //String address = "cosmos1sunalksjd69ap92vvtwwl9lr306lpe4t4hp8q0";
        //String address = "cro17u63qdx6tn2nn364phx8k06jgavrrmxghekwrn";
        //String address = "terra17u63qdx6tn2nn364phx8k06jgavrrmxgfxyhaz";
        String address = "inj1xzpl0mfx0h9yzvududqpcns9fke2rnf0pyn4w7";
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
        //8E8F67C24E8C2044F64E5EA50657CAB5B33DDC635B7F1A7DE82B13C55CBA1AA9    sendMultiTx
        //666A7D88DEBA5F85754C34A9EB3CDDD6C84356EE5DD080701F84217F231BE9DA    delegate
        //E6B53A1AA873C0DEA27D3D430DF539AC29F709AD2BE5E70AACC1108585EBE2B1    unDelegate
        String txHash = "F05641CF7B0297C50F05914AB9ACD4884E82E781EEB1B18062BD7CF5CFC3465A";
        try {
            ServiceOuterClass.GetTxResponse response = cosmosApi.getTx(txHash);
            String typeUrl = response.getTx().getBody().getMessages(0).getTypeUrl();
            if (typeUrl.equals(CosmosTypeUrl.DELEGATE.getType())) {
                Any any = response.getTx().getBody().getMessages(0);
                Tx.MsgDelegate delegate = any.unpack(Tx.MsgDelegate.class);
                System.out.println(delegate.getValidatorAddress());
            }

            System.out.println(response.getTxResponse().getTxhash());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 单笔转账
     */
    @Test
    public void testSendTx() {
        //String toAddress = "cosmos17u63qdx6tn2nn364phx8k06jgavrrmxg0z7hlz";
        //String toAddress = "kava17u63qdx6tn2nn364phx8k06jgavrrmxgnh22f9";
        //String toAddress = "inj1wz4cg0rhxam7hzcn557z795xvpsu8wfphm7y7n";
        //String toAddress = "terra17u63qdx6tn2nn364phx8k06jgavrrmxgfxyhaz";
        String toAddress = "cro17u63qdx6tn2nn364phx8k06jgavrrmxghekwrn";

        // 私钥生成公钥、地址
        byte[] privateKey = Hex.decode(priKey);
        CosmosCredentials credentials = CosmosCredentials.create(privateKey, cosmosApi.getAddressUtil());
        // 转账地址
        System.out.println("address:" + credentials.getAddress());

        SendInfo sendInfo = SendInfo.builder()
                .credentials(credentials)
                .toAddress(toAddress)
                .amount(new BigDecimal("0.001"))
                .demon(cosmosApi.getApiClient().getTokenDemon())
                .build();
        try {
            Abci.TxResponse txResponse = cosmosApi.sendTransferTx(credentials, sendInfo);
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
                    System.out.println(validator.getDescription().getDetails());
                }
                System.out.println(validator.getOperatorAddress() + " ----" + validator.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void queryStakingByValidatorAddress() {
        try {
            QueryOuterClass.QueryValidatorResponse response = cosmosApi.queryStakingByValidatorAddress("cosmosvaloper1sjllsnramtg3ewxqwwrwjxfgc4n4ef9u2lcnj0");
            Staking.Validator validator = response.getValidator();
            if (validator.getStatus() == Staking.BondStatus.BOND_STATUS_BONDED) {
                System.out.println(validator.getDescription().getMoniker());
                System.out.println(validator.getDescription().getDetails());
            }
            System.out.println(validator.getOperatorAddress() + " ----" + validator.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void queryStakingValidatorsByUser() {
        String address = "cro17u63qdx6tn2nn364phx8k06jgavrrmxghekwrn";
        try {
            QueryOuterClass.QueryDelegatorDelegationsResponse response = cosmosApi.queryStakingValidatorsByUser(address);
            System.out.println(response.getDelegationResponsesCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDelegate() {
        String validator = "crocncl1qvhu7slzcdf3rer07y3au2xwxkwugr77xsylxv";

        // 私钥生成公钥、地址
        byte[] privateKey = Hex.decode(priKey);
        CosmosCredentials credentials = CosmosCredentials.create(privateKey, cosmosApi.getAddressUtil());
        // 生成资产转移信息
        SendInfo sendInfo = SendInfo.builder()
                .credentials(credentials)
                .toAddress(validator)       //设置接收地址为验证节点人地址
                .amount(new BigDecimal("0.01"))
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
        String validator = "crocncl1qvhu7slzcdf3rer07y3au2xwxkwugr77xsylxv";

        // 私钥生成公钥、地址
        byte[] privateKey = Hex.decode(priKey);
        CosmosCredentials credentials = CosmosCredentials.create(privateKey, cosmosApi.getAddressUtil());

        SendInfo sendInfo = SendInfo.builder()
                .credentials(credentials)
                .toAddress(validator)       //设置接收地址为验证节点人地址
                .amount(new BigDecimal("0.01"))
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
        String validator = "crocncl1qvhu7slzcdf3rer07y3au2xwxkwugr77xsylxv";
        String address = "cro17u63qdx6tn2nn364phx8k06jgavrrmxghekwrn";
        try {
            cosmos.distribution.v1beta1.QueryOuterClass.QueryDelegationRewardsResponse response = cosmosApi.queryDelegationReward(address, validator);
            System.out.println(response.getRewards(0).getAmount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sentWithdrawRewardTx() {
        String validator = "crocncl1qvhu7slzcdf3rer07y3au2xwxkwugr77xsylxv";

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

}