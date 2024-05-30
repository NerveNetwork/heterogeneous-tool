/**
 * MIT License
 * <p>
 * Copyright (c) 2017-2018 nuls.io
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package network.nerve;

import network.nerve.heterogeneous.ETHTool;
import network.nerve.heterogeneous.context.EthContext;
import network.nerve.heterogeneous.core.HtgWalletApi;
import network.nerve.heterogeneous.utils.HexUtil;
import network.nerve.heterogeneous.utils.HtgCommonTools;
import network.nerve.heterogeneous.utils.SignValidateUtil;
import org.junit.Before;
import org.junit.Test;
import org.web3j.abi.datatypes.Int;
import org.web3j.abi.datatypes.IntType;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.generated.*;
import org.web3j.ens.EnsResolver;
import org.web3j.protocol.core.methods.response.EthSendTransaction;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import static network.nerve.heterogeneous.constant.Constant.GAS_LIMIT_OF_ERC20;
import static network.nerve.heterogeneous.constant.Constant.GAS_LIMIT_OF_MAIN;

/**
 * @author: Loki
 * @date: 2020/11/19
 */
public class ETHToolTest {

    @Before
    public void before() {
        ETHTool.init("https://ropsten.infura.io/v3/e51e9f10a4f647af81d5f083873f27a5", EthContext.testChainId);
    }

    @Test
    public void testName() throws Exception {
        //HtgWalletApi htgWalletApi = HtgWalletApi.getInstance("ETH", "Ropsten", "https://ropsten.infura.io/v3/e51e9f10a4f647af81d5f083873f27a5");
        HtgWalletApi htgWalletApi = HtgWalletApi.getInstance("ETH", "Ropsten", "https://ropsten.infura.io/v3/e51e9f10a4f647af81d5f083873f27a5");
        System.out.println(htgWalletApi.getERC20TokenName("0x92394cEf50f8a935F856F5F27eF1c4B20491b37B"));
    }

    @Test
    public void syncTest() throws IOException {
        //HtgWalletApi htgWalletApi = HtgWalletApi.getInstance("ETH", "Ropsten", "https://ropsten.infura.io/v3/e51e9f10a4f647af81d5f083873f27a5");
        HtgWalletApi htgWalletApi = HtgWalletApi.getInstance("ETH", "Ropsten", "https://web3.mytokenpocket.vip/");
        //EthSyncing send = htgWalletApi.getWeb3j().ethSyncing().send();
        //System.out.println(send.isSyncing());
        EnsResolver resolver = new EnsResolver(htgWalletApi.getWeb3j());
        String resolve = resolver.resolve("000.eth");
        System.out.println(resolve);
    }

    @Test
    public void auth() throws Exception {
        /*
         fromAddress:0x42129b75a285863d9850feefd11af4a00ebecef8
         privateKey:6ca94429e32fabcf5c9b5377f0ac49dc89da42b00212084e7d64ccb03ff49d33
         多签地址：0x7d759a3330cec9b766aa4c889715535eed3c0484
         合约地址：0xb058887cb5990509a3d0dd2833b2054e4a7e4a55
         */
        String authorization = ETHTool.authorization("0x42129b75a285863d9850feefd11af4a00ebecef8",
                "6ca94429e32fabcf5c9b5377f0ac49dc89da42b00212084e7d64ccb03ff49d33",
                "0x7d759a3330cec9b766aa4c889715535eed3c0484",
                "0xb058887cb5990509a3d0dd2833b2054e4a7e4a55");
        System.out.println(authorization);
    }

    @Test
    public void transferEth() throws Exception {
        for (int i = 0; i < 10; i++) {
            String fromAddress = "0xfa27c84eC062b2fF89EB297C24aaEd366079c684";
            String prikey = "B36097415F57FE0AC1665858E3D007BA066A7C022EC712928D2372B27E8513FF";
            String toAddress = "0xE133cF1CFc4e19c2962137287EB825B441385F04";
            BigDecimal amount = new BigDecimal("0.02");
            BigInteger gasLimit = GAS_LIMIT_OF_MAIN;
            BigInteger gasPrice = ETHTool.getCurrentGasPrice();
            long s = System.currentTimeMillis();
            System.out.println("start:" + s);
            String hash = ETHTool.transferEth(fromAddress, prikey, toAddress, amount, gasLimit, gasPrice);
            System.out.println(hash);
            System.out.println("耗时:" + (System.currentTimeMillis() - s));

        }
    }

    @Test
    public void transferERC20() throws Exception {
        String fromAddress = "0xfa27c84eC062b2fF89EB297C24aaEd366079c684";
        String prikey = "B36097415F57FE0AC1665858E3D007BA066A7C022EC712928D2372B27E8513FF";
        String toAddress = "0xE133cF1CFc4e19c2962137287EB825B441385F04";
        String contractAddress = "0xb058887cb5990509a3d0dd2833b2054e4a7e4a55";//USDX

        String tokenAmount = "10";
        int tokenDecimals = 6;
        BigInteger amount = new BigInteger(tokenAmount).multiply(BigInteger.TEN.pow(tokenDecimals));
        EthSendTransaction rs = ETHTool.transferErc20(
                fromAddress,
                prikey,
                toAddress,
                amount,
                contractAddress,
                GAS_LIMIT_OF_ERC20,
                ETHTool.getCurrentGasPrice()
        );
//        EthSendTransaction rs = ETHTool.transferBEP20(fromAddress, prikey, toAddress, new BigDecimal("12.3"), 6, contractAddress);
        System.out.println(rs.getTransactionHash());
    }

    @Test
    public void getEthBalance() throws Exception {
        BigDecimal ethBalance = ETHTool.getEthBalance("0xfa27c84eC062b2fF89EB297C24aaEd366079c684");
        BigDecimal balance = ethBalance.divide(BigDecimal.TEN.pow(18));
        System.out.println(balance);
    }

    @Test
    public void getErc20Balance() throws Exception {
        BigInteger erc20Balance = ETHTool.getErc20Balance("0xfa27c84eC062b2fF89EB297C24aaEd366079c684", "0xb058887cb5990509a3d0dd2833b2054e4a7e4a55");
        BigDecimal balance = new BigDecimal(erc20Balance).divide(BigDecimal.TEN.pow(6));//USDX 6位
        System.out.println(balance);
    }

    @Test
    public void rechargeEth() throws Exception {
        String fromAddress = "0xfa27c84eC062b2fF89EB297C24aaEd366079c684";
        String privateKey = "B36097415F57FE0AC1665858E3D007BA066A7C022EC712928D2372B27E8513FF";
        String tokenAmount = "0.125";
        int tokenDecimals = 18;
        BigInteger value = new BigDecimal(tokenAmount).multiply(BigDecimal.TEN.pow(tokenDecimals)).toBigInteger();
        String toAddress = "TNVTdTSPEn3kK94RqiMffiKkXTQ2anRwhN1J9";
        String multySignContractAddress = "0x7D759A3330ceC9B766Aa4c889715535eeD3c0484";
        String hash = ETHTool.rechargeEth(fromAddress, privateKey, value, toAddress, multySignContractAddress).getTxHash();
        System.out.println(hash);
    }

    @Test
    public void rechargeErc20() throws Exception {
        String fromAddress = "0xfa27c84eC062b2fF89EB297C24aaEd366079c684";
        String privateKey = "B36097415F57FE0AC1665858E3D007BA066A7C022EC712928D2372B27E8513FF";
        String tokenAmount = "12";
        int tokenDecimals = 6;
        BigInteger value = new BigDecimal(tokenAmount).multiply(BigDecimal.TEN.pow(tokenDecimals)).toBigInteger();
        String toAddress = "TNVTdTSPEn3kK94RqiMffiKkXTQ2anRwhN1J9";
        String multySignContractAddress = "0x7D759A3330ceC9B766Aa4c889715535eeD3c0484";
        String Erc20ContractAddress = "0xb058887cb5990509a3d0dd2833b2054e4a7e4a55";
        boolean authorized = ETHTool.isAuthorized(fromAddress, multySignContractAddress, Erc20ContractAddress);
        if (!authorized) {
            String authHash = ETHTool.authorization(fromAddress, privateKey, multySignContractAddress, Erc20ContractAddress);
            while (ETHTool.getTxReceipt(authHash) == null) {
                System.out.println("等待8秒查询[ERC20授权]交易打包结果");
                TimeUnit.SECONDS.sleep(8);
            }
            System.out.println("已授权？再等8秒发交易");
            TimeUnit.SECONDS.sleep(8);
        }
        String hash = ETHTool.rechargeErc20(fromAddress, privateKey, value, toAddress, multySignContractAddress, Erc20ContractAddress).getTxHash();
        System.out.println(hash);
    }

    @Test
    public void testEthSign() {
        String pubKey = "039e66a8c9371278966124a1e4f5f93b1fc8573b33661145f42936f8346c4c376f";
        //需要签名的数据
        String data = "0x7451c20059c68492a59140a2d56A6E2126d96ff4";
        //签名结果
        String signed = "0x5f82883b030cb89d454876413cad40354084c338170dca11d89bbfb12e08b47668f879db34464b3474b66e94da4536aae9fac39e94616287ffb0adc971c0de511";

        try {
            System.out.println(SignValidateUtil.verifyForETH(pubKey, data, signed));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testBTCAddress() {
        String key = "7ce617815b0e2f570d0c7eb77339d85fbdaf132f389ee5a2d1f9a30c05861b45";
        System.out.println(SignValidateUtil.getBtcAddress(key));
    }

    @Test
    public void testPersonalSign() {
        // pubkey: 0x02c2b4e37fa297879c3ed824d021c0ee4692c6f87fcaf1681d712ccd485784b9bd
        // address: 0x54103606d9fcdb40539d06344c8f8c6367ffc9b8
        String priKey = "8bc0ccc66694555540cd83ea63b4de942197981e135d51b3a8";
        byte[] bytes = HexUtil.decode(priKey);
        // 需要签名的数据
        String data = "0x7451c20059c68492a59140a2d56A6E2126d96ff4";
        String signed = ETHTool.personalSign(priKey, data);
        System.out.println(signed.equals("0x5f82883b030cb89d454876413cad40354084c338170dca11d89bbfb12e08b47668f879db34464b3474b66e94da4536aae9fac39e94616287ffb0adc971c0de511b"));
    }

    @Test
    public void extractEthAddress() {
        String data = "hello world!";
        String sign = "0xcca6c6622569e05f5a31a4e8ac14fb9b780234dcdd19a6cbd941bd8d8eaa2efc3fe9e5c695afdca5acab929f6d5925bdceab4421cf02d033f9ba904e82d290421c";
        System.out.println(HtgCommonTools.verifySignature(data, sign));
    }

    @Test
    public void testEthDataV4() throws IOException {
        String priKey = "???";
        String json = "{\\\"domain\\\":{\\\"chainId\\\":1,\\\"name\\\":\\\"Ether Mail\\\",\\\"verifyingContract\\\":\\\"0xCcCCccccCCCCcCCCCCCcCcCccCcCCCcCcccccccC\\\",\\\"version\\\":\\\"1\\\"},\\\"message\\\":{\\\"contents\\\":\\\"Hello, Bobe!\\\",\\\"from\\\":{\\\"name\\\":\\\"Cow\\\",\\\"wallets\\\":[\\\"0xCD2a3d9F938E13CD947Ec05AbC7FE734Df8DD826\\\",\\\"0xDeaDbeefdEAdbeefdEadbEEFdeadbeEFdEaDbeeF\\\"]},\\\"to\\\":[{\\\"name\\\":\\\"Bob\\\",\\\"wallets\\\":[\\\"0xbBbBBBBbbBBBbbbBbbBbbbbBBbBbbbbBbBbbBBbB\\\",\\\"0xB0BdaBea57B0BDABeA57b0bdABEA57b0BDabEa57\\\",\\\"0xB0B0b0b0b0b0B000000000000000000000000000\\\"]}]},\\\"primaryType\\\":\\\"Mail\\\",\\\"types\\\":{\\\"EIP712Domain\\\":[{\\\"name\\\":\\\"name\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"version\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"chainId\\\",\\\"type\\\":\\\"uint256\\\"},{\\\"name\\\":\\\"verifyingContract\\\",\\\"type\\\":\\\"address\\\"}],\\\"Group\\\":[{\\\"name\\\":\\\"name\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"members\\\",\\\"type\\\":\\\"Person[]\\\"}],\\\"Mail\\\":[{\\\"name\\\":\\\"from\\\",\\\"type\\\":\\\"Person\\\"},{\\\"name\\\":\\\"to\\\",\\\"type\\\":\\\"Person[]\\\"},{\\\"name\\\":\\\"contents\\\",\\\"type\\\":\\\"string\\\"}],\\\"Person\\\":[{\\\"name\\\":\\\"name\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"wallets\\\",\\\"type\\\":\\\"address[]\\\"}]}}";
        String json1 = "{\"domain\":{\"chainId\":1,\"name\":\"Ether Mail\",\"verifyingContract\":\"0xCcCCccccCCCCcCCCCCCcCcCccCcCCCcCcccccccC\",\"version\":\"1\"},\"message\":{\"contents\":\"Hello, Bobe!\",\"from\":{\"name\":\"Cow\",\"wallets\":[\"0xCD2a3d9F938E13CD947Ec05AbC7FE734Df8DD826\",\"0xDeaDbeefdEAdbeefdEadbEEFdeadbeEFdEaDbeeF\"]},\"to\":[{\"name\":\"Bob\",\"wallets\":[\"0xbBbBBBBbbBBBbbbBbbBbbbbBBbBbbbbBbBbbBBbB\",\"0xB0BdaBea57B0BDABeA57b0bdABEA57b0BDabEa57\",\"0xB0B0b0b0b0b0B000000000000000000000000000\"]}]},\"primaryType\":\"Mail\",\"types\":{\"EIP712Domain\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"version\",\"type\":\"string\"},{\"name\":\"chainId\",\"type\":\"uint256\"},{\"name\":\"verifyingContract\",\"type\":\"address\"}],\"Group\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"members\",\"type\":\"Person[]\"}],\"Mail\":[{\"name\":\"from\",\"type\":\"Person\"},{\"name\":\"to\",\"type\":\"Person[]\"},{\"name\":\"contents\",\"type\":\"string\"}],\"Person\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"wallets\",\"type\":\"address[]\"}]}}";

        json = "{\"domain\":{\"name\":\"NerveMultiWallet\",\"version\":\"3\",\"chainId\":97,\"verifyingContract\":\"0x5737A1e0d187f2F58541084533b6bf7b965e2299\"},\"message\":{\"txKey\":\"1b8cde190c01a5fab087117314eb43e4fef4042d534c64d1de8896fafe6eab44\",\"to\":\"0xc11D9943805e56b630A401D4bd9A29550353EFa1\",\"amount\":\"100000000000000000\",\"isERC20\":true,\"ERC20\":\"0xb6d685346106b697e6b2bba09bc343cafc930ca3\"},\"primaryType\":\"Withdraw\",\"types\":{\"EIP712Domain\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"version\",\"type\":\"string\"},{\"name\":\"chainId\",\"type\":\"uint256\"},{\"name\":\"verifyingContract\",\"type\":\"address\"}],\"Withdraw\":[{\"name\":\"txKey\",\"type\":\"string\"},{\"name\":\"to\",\"type\":\"address\"},{\"name\":\"amount\",\"type\":\"uint256\"},{\"name\":\"isERC20\",\"type\":\"bool\"},{\"name\":\"ERC20\",\"type\":\"address\"}]}}";
        System.out.println(json);
        System.out.println(json1);
        String signed = ETHTool.signTypedDataV4(priKey, json);
        System.out.println(signed);
        // 0x18085ce9b2b0df8e9186f094ed3d8c181e91638e252b93692fe37b1324abc6f85b22cba977a162350ce3d226567f5724b42d8338ebda05c2d92682c1345afdb91c
        // 0x18085ce9b2b0df8e9186f094ed3d8c181e91638e252b93692fe37b1324abc6f85b22cba977a162350ce3d226567f5724b42d8338ebda05c2d92682c1345afdb91c
    }

    @Test
    public void testInstanceOf() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        System.out.println(Uint256.DEFAULT instanceof IntType);
        System.out.println(Uint.class.isAssignableFrom(Uint256.class));
        System.out.println(IntType.class.isAssignableFrom(Uint256.class));
        System.out.println(IntType.class.isAssignableFrom(Uint32.class));
        System.out.println(IntType.class.isAssignableFrom(Uint120.class));
        System.out.println(IntType.class.isAssignableFrom(Uint128.class));
        System.out.println(IntType.class.isAssignableFrom(Uint.class));
        System.out.println(IntType.class.isAssignableFrom(Int.class));
        System.out.println(IntType.class.isAssignableFrom(Int16.class));
        System.out.println(IntType.class.isAssignableFrom(Int160.class));
        System.out.println(IntType.class.isAssignableFrom(Int128.class));
        System.out.println(IntType.class.isAssignableFrom(Int256.class));
    }

}
