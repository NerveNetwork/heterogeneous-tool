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
import org.junit.Before;
import org.junit.Test;
import org.web3j.protocol.core.methods.response.EthSendTransaction;

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
        ETHTool.init("https://ropsten.infura.io/v3/e51e9f10a4f647af81d5f083873f27a5");
    }


    @Test
    public void transferEth() throws Exception {
        for(int i=0;i<10;i++) {
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
}
