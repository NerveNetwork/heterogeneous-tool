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

package network.nerve.heterogeneous;

import network.nerve.heterogeneous.context.EthContext;
import network.nerve.heterogeneous.core.ETHWalletApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static network.nerve.heterogeneous.context.EthContext.ETH_GAS_PRICE;

/**
 * ETH Tool
 *
 * @author: Loki
 * @date: 2020/11/18
 */
public class ETHTool {

    private static Logger Log = LoggerFactory.getLogger(ETHTool.class.getName());

    public static BigInteger ETH_GAS_LIMIT_OF_ETH = BigInteger.valueOf(21000L);

    public static BigInteger ETH_GAS_LIMIT_OF_ERC20 = BigInteger.valueOf(60000L);

    private static ETHWalletApi ethWalletApi = ETHWalletApi.getInstance();

    /**
     * 自定义 ETH RPC地址
     *
     * @param rpcAddress
     */
    public static void init(String rpcAddress) {
        EthContext.rpcAddress = rpcAddress;
        ethWalletApi.restartApi();
    }

    /**
     * 在ETH上转账ETH资产
     *
     * @param fromAddress 转出地址
     * @param privateKey  转出地址私钥
     * @param toAddress   接收地址
     * @param value       转出数量
     * @param gasLimit
     * @param gasPrice
     * @return 交易hash
     * @throws Exception
     */
    public static String transferEth(String fromAddress, String privateKey, String toAddress, BigDecimal value, BigInteger gasLimit, BigInteger gasPrice) throws Exception {
        return ethWalletApi.sendETH(fromAddress, privateKey, toAddress, value, gasLimit, gasPrice);
    }


    /**
     * 在ETH上转账ERC20资产
     *
     * @param fromAddress     转出地址
     * @param privateKey      转出地址私钥
     * @param toAddress       接收地址
     * @param value           转出数量(根据小数位数换算后的整数)
     * @param contractAddress ERC20资产合约地址
     * @return
     * @throws Exception
     */
    public static EthSendTransaction transferErc20(String fromAddress, String privateKey, String toAddress, BigInteger value, String contractAddress, BigInteger gasLimit, BigInteger gasPrice) throws Exception {
        return ethWalletApi.transferERC20Token(fromAddress, toAddress, value, privateKey, contractAddress, gasLimit, gasPrice);
    }

    /**
     * 获取ETH资产余额
     *
     * @param address
     * @return
     * @throws Exception
     */
    public static BigDecimal getEthBalance(String address) throws Exception {
        return ethWalletApi.getBalance(address);
    }

    /**
     * 获取ERC20资产余额
     *
     * @param address
     * @param contractAddress
     * @return
     * @throws Exception
     */
    public static BigInteger getErc20Balance(String address, String contractAddress) throws Exception {
        return ethWalletApi.getERC20Balance(address, contractAddress);
    }


    public static BigInteger getEthGasPrice() {
        int time = 0;
        while (ETH_GAS_PRICE == null) {
            time++;
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                //do nothing
            }
            if (time == 3) {
                break;
            }
        }
        if (ETH_GAS_PRICE == null) {
            ETH_GAS_PRICE = BigInteger.valueOf(100L).multiply(BigInteger.TEN.pow(9));
        }
        return ETH_GAS_PRICE;
    }


    /**
     * 充值ETH
     * 以太坊网络向NERVE网络充值ETH
     *
     * @param fromAddress              转出地址
     * @param privateKey               转出地址私钥
     * @param value                    转出数量(根据小数位数换算后的整数)
     * @param toAddress                接收token的NERVE地址
     * @param multySignContractAddress 多签合约地址
     * @return
     * @throws Exception
     */
    public static String rechargeEth(String fromAddress, String privateKey, BigInteger value, String toAddress, String multySignContractAddress) throws Exception {
        return ethWalletApi.rechargeEth(fromAddress, privateKey, value, toAddress, multySignContractAddress);
    }

    /**
     * 充值ERC20资产
     * 以太坊网络向NERVE网络充值ERC20资产
     *
     * @param fromAddress              转出地址
     * @param privateKey               转出地址私钥
     * @param value                    转出数量(根据小数位数换算后的整数)
     * @param toAddress                接收token的NERVE地址
     * @param multySignContractAddress 多签合约地址
     * @param erc20ContractAddress     ERC20 token合约地址
     * @return
     * @throws Exception
     */
    public static String rechargeErc20(String fromAddress, String privateKey, BigInteger value, String toAddress, String multySignContractAddress, String erc20ContractAddress) throws Exception {
        return ethWalletApi.rechargeErc20(fromAddress, privateKey, value, toAddress, multySignContractAddress, erc20ContractAddress);
    }

    /**
     * 授权使用ERC20资产
     *
     * @param fromAddress              转出地址
     * @param privateKey               转出地址私钥
     * @param multySignContractAddress 多签合约地址
     * @param erc20ContractAddress     ERC20 token合约地址
     * @return
     * @throws Exception
     */
    public static String authorization(String fromAddress, String privateKey, String multySignContractAddress, String erc20ContractAddress) throws Exception {
        return ethWalletApi.authorization(fromAddress, privateKey, multySignContractAddress, erc20ContractAddress);
    }

    /**
     * 查询是否已经授权使用ERC20资产
     *
     * @param fromAddress              转出地址
     * @param multySignContractAddress 多签合约地址
     * @param erc20ContractAddress     ERC20 token合约地址
     * @return
     * @throws Exception
     */
    public static boolean isAuthorized(String fromAddress, String multySignContractAddress, String erc20ContractAddress) throws Exception {
        return ethWalletApi.isAuthorized(fromAddress, multySignContractAddress, erc20ContractAddress);
    }

    /**
     * 调用合约的view/constant函数
     * @param contractAddress
     * @param function
     * @return
     * @throws Exception
     */
    public static List<Type> callViewFunction(String contractAddress, Function function) throws Exception {
        return ethWalletApi.callViewFunction(contractAddress, function);
    }

    public static TransactionReceipt getTxReceipt(String txHash) throws Exception {
        return ethWalletApi.getTxReceipt(txHash);
    }
}
