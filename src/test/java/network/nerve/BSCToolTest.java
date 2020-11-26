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

import network.nerve.heterogeneous.BSCTool;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.core.methods.response.EthSendTransaction;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

/**
 * @author: Loki
 * @date: 2020/11/19
 */
public class BSCToolTest {

    private static Logger Log =  LoggerFactory.getLogger(BSCToolTest.class.getName());

    @Before
    public void before() {
        BSCTool.init("https://data-seed-prebsc-1-s1.binance.org:8545/");
    }

    @Test
    public void transferBnb() throws Exception {
        String fromAddress = "0xfa27c84eC062b2fF89EB297C24aaEd366079c684";
        String prikey = "B36097415F57FE0AC1665858E3D007BA066A7C022EC712928D2372B27E8513FF";
        String toAddress = "0xE133cF1CFc4e19c2962137287EB825B441385F04";
        BigDecimal amount = new BigDecimal("0.2");
        BigInteger gasLimit = BSCTool.BNB_GAS_LIMIT_OF_BNB;
        BigInteger gasPrice = BSCTool.getBscGasPrice();
        String hash = BSCTool.transferBnb(fromAddress, prikey, toAddress, amount, gasLimit, gasPrice);
        System.out.println(hash);
    }

    @Test
    public void transferBEP20() throws Exception {
        String fromAddress = "0xfa27c84eC062b2fF89EB297C24aaEd366079c684";
        String prikey = "B36097415F57FE0AC1665858E3D007BA066A7C022EC712928D2372B27E8513FF";
        String toAddress = "0xE133cF1CFc4e19c2962137287EB825B441385F04";
        String contractAddress = "0xb6d685346106b697e6b2bba09bc343cafc930ca3";//USDX

        String tokenAmount = "10";
        int tokenDecimals = 6;
        BigInteger amount = new BigInteger(tokenAmount).multiply(BigInteger.TEN.pow(tokenDecimals));
        EthSendTransaction rs = BSCTool.transferBep20(
                fromAddress,
                prikey,
                toAddress,
                amount,
                contractAddress,
                BSCTool.getBscGasPrice(),
                BSCTool.BNB_GAS_LIMIT_OF_BEP20);
        System.out.println(rs.getTransactionHash());
    }

    @Test
    public void getBnbBalance() throws Exception {
        BigDecimal bnbBalance = BSCTool.getBnbBalance("0xfa27c84eC062b2fF89EB297C24aaEd366079c684");
        BigDecimal balance = bnbBalance.divide(BigDecimal.TEN.pow(18));
        System.out.println(balance);
    }

    @Test
    public void getBEP20Balance() throws Exception {
        BigInteger bep20Balance = BSCTool.getBep20Balance("0xfa27c84eC062b2fF89EB297C24aaEd366079c684", "0xb6d685346106b697e6b2bba09bc343cafc930ca3");
        BigDecimal balance = new BigDecimal(bep20Balance).divide(BigDecimal.TEN.pow(6));//USDX 6位
        System.out.println(balance);
    }

    @Test
    public void rechargeBnb() throws Exception {
        String fromAddress = "0xfa27c84eC062b2fF89EB297C24aaEd366079c684";
        String privateKey = "B36097415F57FE0AC1665858E3D007BA066A7C022EC712928D2372B27E8513FF";
        String tokenAmount = "2.125";
        int tokenDecimals = 18;
        BigInteger value = new BigDecimal(tokenAmount).multiply(BigDecimal.TEN.pow(tokenDecimals)).toBigInteger();
        String toAddress = "TNVTdTSPEn3kK94RqiMffiKkXTQ2anRwhN1J9";
        String multySignContractAddress = "0xf7915d4de86b856F3e51b894134816680bf09EEE";
        String hash = BSCTool.rechargeBnb(fromAddress, privateKey, value, toAddress, multySignContractAddress);
        System.out.println(hash);
    }

    @Test
    public void rechargeBep20() throws Exception {
        String fromAddress = "0xfa27c84eC062b2fF89EB297C24aaEd366079c684";
        String privateKey = "B36097415F57FE0AC1665858E3D007BA066A7C022EC712928D2372B27E8513FF";
        String tokenAmount = "12";
        int tokenDecimals = 6;
        BigInteger value = new BigDecimal(tokenAmount).multiply(BigDecimal.TEN.pow(tokenDecimals)).toBigInteger();
        String toAddress = "TNVTdTSPEn3kK94RqiMffiKkXTQ2anRwhN1J9";
        String multySignContractAddress = "0xf7915d4de86b856F3e51b894134816680bf09EEE";
        String bep20ContractAddress = "0xb6d685346106b697e6b2bba09bc343cafc930ca3";
        boolean authorized = BSCTool.isAuthorized(fromAddress, multySignContractAddress, bep20ContractAddress);
        if (!authorized) {
            String authHash = BSCTool.authorization(fromAddress, privateKey, multySignContractAddress, bep20ContractAddress);
            while (BSCTool.getTxReceipt(authHash) == null) {
                System.out.println("等待8秒查询[ERC20授权]交易打包结果");
                TimeUnit.SECONDS.sleep(8);
            }
        }
        String hash = BSCTool.rechargeBep20(fromAddress, privateKey, value, toAddress, multySignContractAddress, bep20ContractAddress);
        System.out.println(hash);
    }


}
