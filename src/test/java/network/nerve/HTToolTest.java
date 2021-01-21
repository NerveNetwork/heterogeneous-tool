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

import network.nerve.heterogeneous.HTTool;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

/**
 * @author: Mimi
 * @date: 2021-01-21
 */
public class HTToolTest {

    @Before
    public void before() {
        HTTool.init("https://http-testnet.hecochain.com");
    }

    @Test
    public void getHtBalance() throws Exception {
        BigDecimal ethBalance = HTTool.getHtBalance("0x9484b26cba3c52161d2d320395da94e336e8a3cd");
        BigDecimal balance = ethBalance.divide(BigDecimal.TEN.pow(18));
        System.out.println(balance);
    }

    @Test
    public void getErc20Balance() throws Exception {
        BigInteger erc20Balance = HTTool.getErc20Balance("0x9484b26cba3c52161d2d320395da94e336e8a3cd", "0x02e1afeef2a25eabd0362c4ba2dc6d20ca638151");
        BigDecimal balance = new BigDecimal(erc20Balance).divide(BigDecimal.TEN.pow(6));//USDT 6位
        System.out.println(balance);
    }

    @Test
    public void rechargeHt() throws Exception {
        String fromAddress = "0x9484b26cba3c52161d2d320395da94e336e8a3cd";
        String privateKey = "17c50c6f7f18e7afd37d39f92c1d48054b6b3aa2373a70ecf2d6663eace2a7d6";
        String tokenAmount = "0.011";
        int tokenDecimals = 18;
        BigInteger value = new BigDecimal(tokenAmount).multiply(BigDecimal.TEN.pow(tokenDecimals)).toBigInteger();
        String toAddress = "TNVTdTSPNEpLq2wnbsBcD8UDTVMsArtkfxWgz";
        String multySignContractAddress = "0xb339211438Dcbf3D00d7999ad009637472FC72b3";
        String hash = HTTool.rechargeHt(fromAddress, privateKey, value, toAddress, multySignContractAddress);
        System.out.println(hash);
    }

    @Test
    public void rechargeErc20() throws Exception {
        String fromAddress = "0x9484b26cba3c52161d2d320395da94e336e8a3cd";
        String privateKey = "17c50c6f7f18e7afd37d39f92c1d48054b6b3aa2373a70ecf2d6663eace2a7d6";
        String tokenAmount = "0.022";
        int tokenDecimals = 6;
        BigInteger value = new BigDecimal(tokenAmount).multiply(BigDecimal.TEN.pow(tokenDecimals)).toBigInteger();
        String toAddress = "TNVTdTSPNEpLq2wnbsBcD8UDTVMsArtkfxWgz";
        String multySignContractAddress = "0xb339211438Dcbf3D00d7999ad009637472FC72b3";
        String Erc20ContractAddress = "0x02e1afeef2a25eabd0362c4ba2dc6d20ca638151";
        boolean authorized = HTTool.isAuthorized(fromAddress, multySignContractAddress, Erc20ContractAddress);
        if (!authorized) {
            String authHash = HTTool.authorization(fromAddress, privateKey, multySignContractAddress, Erc20ContractAddress);
            while (HTTool.getTxReceipt(authHash) == null) {
                System.out.println("等待8秒查询[ERC20授权]交易打包结果");
                TimeUnit.SECONDS.sleep(8);
            }
            System.out.println("已授权？再等8秒发交易");
            TimeUnit.SECONDS.sleep(8);
        }
        String hash = HTTool.rechargeErc20(fromAddress, privateKey, value, toAddress, multySignContractAddress, Erc20ContractAddress);
        System.out.println(hash);
    }
}
