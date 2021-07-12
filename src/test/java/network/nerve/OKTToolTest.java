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

import network.nerve.heterogeneous.OKTTool;
import network.nerve.heterogeneous.context.OktContext;
import org.junit.Before;
import org.junit.Test;
import org.web3j.protocol.core.methods.response.EthSendTransaction;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import static network.nerve.heterogeneous.constant.Constant.GAS_LIMIT_OF_ERC20;

/**
 * @author: Loki
 * @date: 2020/11/19
 */
public class OKTToolTest {

    @Before
    public void before() {
        OKTTool.init("https://exchaintestrpc.okex.org/", OktContext.testChainId);
    }


    @Test
    public void transferOkt() throws Exception {
        for (int i = 0; i < 1; i++) {
            String fromAddress = "0xfa27c84eC062b2fF89EB297C24aaEd366079c684";
            String prikey = "B36097415F57FE0AC1665858E3D007BA066A7C022EC712928D2372B27E8513FF";
            String toAddress = "0xE133cF1CFc4e19c2962137287EB825B441385F04";
            BigDecimal amount = new BigDecimal("0.02");
//            BigInteger gasLimit = GAS_LIMIT_OF_MAIN;
//            BigInteger gasPrice = OKTTool.getCurrentGasPrice();
            BigInteger gasLimit = new BigInteger("35000");
            BigInteger gasPrice = new BigInteger("1000000000");
            long s = System.currentTimeMillis();
            System.out.println("start:" + s);
            String hash = OKTTool.transferOkt(fromAddress, prikey, toAddress, amount, gasLimit, gasPrice);
            System.out.println(hash);
            System.out.println("耗时:" + (System.currentTimeMillis() - s));
        }
    }

    @Test
    public void transferERC20() throws Exception {
        String fromAddress = "0xfa27c84eC062b2fF89EB297C24aaEd366079c684";
        String prikey = "B36097415F57FE0AC1665858E3D007BA066A7C022EC712928D2372B27E8513FF";
        String toAddress = "0xE133cF1CFc4e19c2962137287EB825B441385F04";
        String contractAddress = "0x2ee066bdf26939df84d6e8b8225eb7c5a1cf0a89";//USDX

        String tokenAmount = "1";
        int tokenDecimals = 6;
        BigInteger amount = new BigInteger(tokenAmount).multiply(BigInteger.TEN.pow(tokenDecimals));
        EthSendTransaction rs = OKTTool.transferKip20(
                fromAddress,
                prikey,
                toAddress,
                amount,
                contractAddress,
                GAS_LIMIT_OF_ERC20,
                OKTTool.getCurrentGasPrice()
        );
//        EthSendTransaction rs = OKTTool.transferBEP20(fromAddress, prikey, toAddress, new BigDecimal("12.3"), 6, contractAddress);
        System.out.println(rs.getTransactionHash());
    }

    @Test
    public void getOktBalance() throws Exception {
        BigDecimal ethBalance = OKTTool.getOktBalance("0xfa27c84eC062b2fF89EB297C24aaEd366079c684");
        BigDecimal balance = ethBalance.divide(BigDecimal.TEN.pow(18));
        System.out.println(balance);
    }

    @Test
    public void getKip20Balance() throws Exception {
        BigInteger erc20Balance = OKTTool.getKip20Balance("0xfa27c84eC062b2fF89EB297C24aaEd366079c684", "0xb058887cb5990509a3d0dd2833b2054e4a7e4a55");
        BigDecimal balance = new BigDecimal(erc20Balance).divide(BigDecimal.TEN.pow(6));//USDX 6位
        System.out.println(balance);
    }

    @Test
    public void rechargeOkt() throws Exception {
        String fromAddress = "0xfa27c84eC062b2fF89EB297C24aaEd366079c684";
        String privateKey = "B36097415F57FE0AC1665858E3D007BA066A7C022EC712928D2372B27E8513FF";
        String tokenAmount = "0.125";
        int tokenDecimals = 18;
        BigInteger value = new BigDecimal(tokenAmount).multiply(BigDecimal.TEN.pow(tokenDecimals)).toBigInteger();
        String toAddress = "TNVTdTSPEn3kK94RqiMffiKkXTQ2anRwhN1J9";
        String multySignContractAddress = "0x7D759A3330ceC9B766Aa4c889715535eeD3c0484";
        String hash = OKTTool.rechargeOkt(fromAddress, privateKey, value, toAddress, multySignContractAddress).getTxHash();
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
        String Kip20ContractAddress = "0xb058887cb5990509a3d0dd2833b2054e4a7e4a55";
        boolean authorized = OKTTool.isAuthorized(fromAddress, multySignContractAddress, Kip20ContractAddress);
        if (!authorized) {
            String authHash = OKTTool.authorization(fromAddress, privateKey, multySignContractAddress, Kip20ContractAddress);
            while (OKTTool.getTxReceipt(authHash) == null) {
                System.out.println("等待8秒查询[ERC20授权]交易打包结果");
                TimeUnit.SECONDS.sleep(8);
            }
            System.out.println("已授权？再等8秒发交易");
            TimeUnit.SECONDS.sleep(8);
        }
        String hash = OKTTool.rechargeKip20(fromAddress, privateKey, value, toAddress, multySignContractAddress, Kip20ContractAddress).getTxHash();
        System.out.println(hash);
    }
}
