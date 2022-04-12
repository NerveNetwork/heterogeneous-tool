package network.nerve.trx;

import network.nerve.heterogeneous.constant.TrxConstant;
import network.nerve.heterogeneous.model.TRC20TransferEvent;
import network.nerve.heterogeneous.model.TrxEstimateSun;
import network.nerve.heterogeneous.model.TrxSendTransactionPo;
import network.nerve.heterogeneous.model.TrxTransaction;
import network.nerve.heterogeneous.utils.TrxUtil;
import org.junit.Before;
import org.junit.Test;
import org.tron.trident.abi.TypeReference;
import org.tron.trident.abi.datatypes.Address;
import org.tron.trident.abi.datatypes.Function;
import org.tron.trident.abi.datatypes.generated.Uint256;
import org.tron.trident.core.key.KeyPair;
import org.tron.trident.crypto.SECP256K1;
import org.tron.trident.proto.Chain;
import org.tron.trident.proto.Response;
import org.tron.trident.utils.Numeric;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static network.nerve.heterogeneous.utils.TrxUtil.parseTRC20Event;


public class TrxWalletApiTest extends Base {

    protected String from;
    protected String fromPriKey;
    protected String erc20Address;
    protected int erc20Decimals;
    protected SECP256K1.KeyPair keyPair;
    protected int nerveChainId = 5;

    protected void setErc20USDTMain() {
        // 0x370dd53139e0d8923f9feaf1989344ec64f6ff6d
        erc20Address = "TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t";
        erc20Decimals = 6;
    }

    protected void setErc20DX() {
        // 0x370dd53139e0d8923f9feaf1989344ec64f6ff6d
        erc20Address = "TEzJjjC4NrLrYFthGFHzQon5zrErNw1JN9";
        erc20Decimals = 6;
    }

    protected void setErc20K18N() {
        // 0xe10095ee56d7181d59b49f3cbdd596945fd2d68d
        erc20Address = "TWUujuxBNCGPZNiyySfYuBJgFumrvS2iRd";
        erc20Decimals = 18;
    }

    protected void setErc20USDT() {
        erc20Address = "TXCWs4vtLW2wYFHfi7xWeiC9Kuj2jxpKqJ";
        erc20Decimals = 6;
    }

    protected void setErc20NVT() {
        erc20Address = "TJa51xhiz6eLo2jtf8pxPGJ1AjXNvqPC49";
        erc20Decimals = 8;
    }

    /** FortuneCai (FCI) */
    protected void setErc20FCI() {
        // 0x404ced5e5614488129c26999627416f96fdc7fd9
        erc20Address = "TFqCQLGxG2o188eESoYkr1Ji9x85SEXBDP";
        erc20Decimals = 18;
    }

    protected void setErc20QOP() {
        setNile();
        // 0xf59beb9623a105f666698d08a8f96c2ebdc88fa2
        erc20Address = "TYMsLazzLMWCtDamLDLkYtiV6uXsTxFwdr";
        erc20Decimals = 8;
    }

    protected void setM2() {
        this.from = "TFzEXjcejyAdfLSEANordcppsxeGW9jEm2";
        this.fromPriKey = "30002e81d449f16b69bc3e06918ff6ff088863edef8a0ba3d9b06fe5d02744d7";
    }

    protected void setPM() {
        // 0x43a0eca8a75c86f30045a434114d750eb1b4b6e0
        this.from = "TG8o48ycgUCB7UJd46cSnxSJybWwTHmRpm";
        this.fromPriKey = "d8fd23d961076b3616078ff235c4018c6113f3811ed97109e925f7232986b583";
    }

    protected void setUX() {
        this.from = "TTaJsdnYPsBjLLM1u2qMw1e9fLLoVKnNUX";
        this.fromPriKey = "4594348E3482B751AA235B8E580EFEF69DB465B3A291C5662CEDA6459ED12E39";
    }


    protected void setBeta() {
        list = new ArrayList<>();
        list.add("978c643313a0a5473bf65da5708766dafc1cca22613a2480d0197dc99183bb09");// 0x1a9f8b818a73b0f9fde200cd88c42b626d2661cd
        list.add("6e905a55d622d43c499fa844c05db46859aed9bb525794e2451590367e202492");// 0x6c2039b5fdae068bad4931e8cc0b8e3a542937ac
        list.add("d48b870f2cf83a739a134cd19015ed96d377f9bc9e6a41108ac82daaca5465cf");// 0x3c2ff003ff996836d39601ca22394a58ca9c473b
        this.multySignContractAddress = "TYVxuksybZdbyQwoR25V2YUgXYAHikcLro";// old: TWajcnpyyZLRtLkFd6p4ZAMn5y4GpDa6MB
        this.priKey = list.get(0);
        this.address = new KeyPair(priKey).toBase58CheckAddress();

    }

    @Before
    public void before() {
        setBeta();
    }

    /**
     * 获取最新高度
     */
    @Test
    public void getHeight() throws Exception {
        System.out.println(walletApi.getBlockHeight());
    }

    /**
     * 查询主资产TRX余额
     */
    @Test
    public void getBalanceOfTrxTest() throws Exception {
        setUX();
        BigDecimal balance = walletApi.getBalance(from);
        System.out.println(balance);
    }

    /**
     * 查询Trc20 Token余额, token name, symbol, decimals
     */
    @Test
    public void getBalanceOfTrc20Test() throws Exception {
        setUX();
        setErc20DX();
        BigInteger erc20Balance = walletApi.getERC20Balance(from, erc20Address);
        System.out.println(erc20Balance);
        System.out.println(walletApi.getERC20Name(erc20Address));
        System.out.println(walletApi.getERC20Symbol(erc20Address));
        System.out.println(walletApi.getERC20Decimals(erc20Address));
    }

    /**
     * 获取交易基本信息
     */
    @Test
    public void getTx() throws Exception {
        String txHash = "d1958721f6c9fb379866760542b2dfff4fb0897aeb03888790f7f1e037b912a5";
        Chain.Transaction tx = walletApi.getTransactionByHash(txHash);
        TrxTransaction txInfo = TrxUtil.generateTxInfo(tx);
        System.out.println(txInfo.getFrom());
    }


    /**
     * 获取交易执行结果
     */
    @Test
    public void getTRC20TxReceiptAndParseEvent() throws Exception {
        String txHash = "501c346a6c15885a6972f350aa522fd2f55f19b8302f860995b93770476d31a5";
        Response.TransactionInfo txInfo = walletApi.getTransactionReceipt(txHash);
        List<Response.TransactionInfo.Log> logs = txInfo.getLogList();
        for (Response.TransactionInfo.Log log : logs) {
            String address = TrxUtil.ethAddress2trx(log.getAddress().toByteArray());
            String eventHash = Numeric.toHexString(log.getTopics(0).toByteArray());
            System.out.println(String.format("address: %s, topics: %s", address, eventHash));
            TRC20TransferEvent trc20Event = null;
            if (TrxConstant.EVENT_HASH_ERC20_TRANSFER.equals(eventHash)) {
                trc20Event = parseTRC20Event(log);
            } else {
                System.err.println("未知事件");
            }
            if (trc20Event != null) {
                System.out.println(trc20Event);
            }

        }
    }

    /**
     * 链内TRX转账
     */
    @Test
    public void transferTrx() throws Exception {
        setUX();
        String to = "TFzEXjcejyAdfLSEANordcppsxeGW9jEm2";
        to = TrxUtil.ethAddress2trx(to);
        String value = "1.1";
        TrxSendTransactionPo trx = walletApi.transferTrx(from, to, TrxUtil.convertTrxToSun(new BigDecimal(value)), fromPriKey);
        System.out.println(trx.getTxHash());
    }

    /**
     * 链内TRC20转账
     */
    @Test
    public void transferTRC20() throws Exception {
        setUX();
        setErc20USDT();
        String to = "TFzEXjcejyAdfLSEANordcppsxeGW9jEm2";
        String value = "1";
        BigInteger convertValue = new BigInteger(value).multiply(BigInteger.TEN.pow(erc20Decimals));
        // 估算feeLimit
        Function function = TrxUtil.getTransferERC20Function(to, convertValue);
        TrxEstimateSun estimateSun = walletApi.estimateSunUsed(from, erc20Address, function);
        if (estimateSun.isReverted()) {
            System.err.println(String.format("交易验证失败，原因: %s", estimateSun.getRevertReason()));
            return;
        }
        BigInteger feeLimit = TrxConstant.TRX_20;
        if (estimateSun.getSunUsed() > 0) {
            feeLimit = BigInteger.valueOf(estimateSun.getSunUsed());
        }
        TrxSendTransactionPo trx = walletApi.transferTRC20Token(
                from, to, convertValue, fromPriKey, erc20Address, feeLimit);
        System.out.println(trx.getTxHash());
    }

    /**
     * 跨链转入 - 主资产
     */
    @Test
    public void depositTRXByCrossOut() throws Exception {
        setUX();
        String to = "TNVTdTSPRnXkDiagy7enti1KL75NU5AxC9sQA";
        String value = "15";
        BigInteger valueBig = TrxUtil.convertTrxToSun(new BigDecimal(value));
        String erc20 = TrxConstant.ZERO_ADDRESS_TRX;
        Function function = TrxUtil.getCrossOutFunction(to, valueBig, erc20);
        BigInteger feeLimit = TrxConstant.TRX_100;
        TrxSendTransactionPo callContract = walletApi.callContract(from, fromPriKey, multySignContractAddress, feeLimit, function, valueBig);
        System.out.println(callContract.toString());
    }
    /**
     * 跨链转入 - TRC20(包含检查授权和授权流程)
     */
    @Test
    public void depositERC20ByCrossOut() throws Exception {
        setUX();
        setErc20DX();
        // ERC20 转账数量
        String sendAmount = "3.3";
        // Nerve 接收地址
        String to = "TNVTdTSPRnXkDiagy7enti1KL75NU5AxC9sQA";

        BigInteger convertAmount = new BigDecimal(sendAmount).multiply(BigDecimal.TEN.pow(erc20Decimals)).toBigInteger();
        Function allowanceFunction = new Function("allowance",
                Arrays.asList(new Address(from), new Address(multySignContractAddress)),
                Arrays.asList(new TypeReference<Uint256>() {
                }));

        BigInteger allowanceAmount = (BigInteger) walletApi.callViewFunction(erc20Address, allowanceFunction).get(0).getValue();
        if (allowanceAmount.compareTo(convertAmount) < 0) {
            // erc20授权
            String approveAmount = "99999999";
            Function approveFunction = TrxUtil.getERC20ApproveFunction(multySignContractAddress, new BigInteger(approveAmount).multiply(BigInteger.TEN.pow(erc20Decimals)));
            String authHash = this.sendTx(from, fromPriKey, approveFunction, null, erc20Address);
            System.out.println(String.format("TRC20授权充值[%s], 授权hash: %s", approveAmount, authHash));
            while (walletApi.getTransactionReceipt(authHash) == null) {
                System.out.println("等待8秒查询[TRC20授权]交易打包结果");
                TimeUnit.SECONDS.sleep(8);
            }
            //TimeUnit.SECONDS.sleep(8);
            BigInteger tempAllowanceAmount = (BigInteger) walletApi.callViewFunction(erc20Address, allowanceFunction).get(0).getValue();
            while (tempAllowanceAmount.compareTo(convertAmount) < 0) {
                System.out.println("等待8秒查询[TRC20授权]交易额度");
                TimeUnit.SECONDS.sleep(8);
                tempAllowanceAmount = (BigInteger) walletApi.callViewFunction(erc20Address, allowanceFunction).get(0).getValue();
            }
        }
        System.out.println("[TRC20授权]额度已满足条件");
        // crossOut erc20转账
        Function crossOutFunction = TrxUtil.getCrossOutFunction(to, convertAmount, erc20Address);
        String hash = this.sendTx(from, fromPriKey, crossOutFunction, null, multySignContractAddress);
        System.out.println(String.format("TRC20充值[%s], 充值hash: %s", sendAmount, hash));
    }

    /**
     * 估算TRC20转账的feeLimit
     */
    @Test
    public void estimateSun() throws Exception {
        setMain();
        from = "THmbMWg4XrFpPWQKUojF1Hh9KjVmjXQTNX";
        setErc20USDTMain();
        String to = "THmbMWg4XrFpPWQKUojF1Hh9KjVmjXQTNX";
        String value = "1324768678567867.98";
        BigInteger convertValue = new BigDecimal(value).movePointRight(erc20Decimals).toBigInteger();
        // 估算feeLimit
        Function function = TrxUtil.getTransferERC20Function(to, convertValue);
        TrxEstimateSun estimateSun = walletApi.estimateSunUsed(from, erc20Address, function);
        if (estimateSun.isReverted()) {
            System.err.println(String.format("交易验证失败，原因: %s", estimateSun.getRevertReason()));
            return;
        }
        System.out.println(String.format("sunUsed: %s", estimateSun.getSunUsed()));
    }

    /**
     * 授权清零
     */
    @Test
    public void cleanAllowance() throws Exception {
        setUX();
        setErc20DX();
        // 清零授权
        String approveAmount = "0";
        Function approveFunction = TrxUtil.getERC20ApproveFunction(multySignContractAddress, new BigInteger(approveAmount).multiply(BigInteger.TEN.pow(erc20Decimals)));
        String authHash = this.sendTx(from, fromPriKey, approveFunction, null, erc20Address);
        System.out.println(String.format("TRC20授权清零, 授权hash: %s", authHash));
    }

    @Test
    public void addressTest() {
        List<String> list = new ArrayList<>();
        list.add("TVAhzC3rBLy6XLceHfha3ddiVHTUpUovyY");
        list.add("0x595d5364e5eb77e3707ce2710215db97a835a82d");
        for (String address : list) {
            System.out.println("-------");
            System.out.println(TrxUtil.ethAddress2trx(address));
            System.out.println(TrxUtil.trxAddress2eth(address));
            System.out.println("-------");
        }
    }

}