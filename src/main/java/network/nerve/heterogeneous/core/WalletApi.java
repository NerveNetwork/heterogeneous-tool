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

package network.nerve.heterogeneous.core;

import network.nerve.heterogeneous.model.Block;
import network.nerve.heterogeneous.model.EthSendTransactionPo;
import org.web3j.protocol.core.methods.response.EthSendTransaction;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

/**
 * @author: PierreLuo
 * @date: 2021/8/23
 */
public interface WalletApi {
    EthSendTransactionPo createSendMainAsset(String fromAddress, String privateKey, String toAddress, BigDecimal value, BigInteger gasLimit, BigInteger gasPrice) throws Exception;
    EthSendTransactionPo createTransferERC20Token(String from, String to, BigInteger value, String privateKey, String contractAddress, BigInteger gasLimit, BigInteger gasPrice) throws Exception;
    EthSendTransactionPo createRechargeMainAssetWithGas(String fromAddress, String prikey, BigInteger value, String toAddress, String multySignContractAddress, BigInteger gasLimit, BigInteger gasPrice) throws Exception;
    EthSendTransactionPo createRechargeErc20WithGas(String fromAddress, String prikey, BigInteger value, String toAddress, String multySignContractAddress, String erc20ContractAddress, BigInteger gasLimit, BigInteger gasPrice) throws Exception;

    // 估算链内主资产转账
    BigInteger estimateGasForTransferMainAsset() throws Exception;
    // 估算链内合约资产转账
    BigInteger estimateGasForTransferERC20(String from, String to, BigInteger value, String contractAddress) throws Exception;
    // 估算跨链主资产转账
    BigInteger estimateGasForRechargeMainAsset() throws Exception;
    // 估算跨链合约资产转账
    BigInteger estimateGasForRechargeERC20(String fromAddress, String toAddress, BigInteger value, String multySignContractAddress, String erc20ContractAddress) throws Exception;
    //是否已授权过
    boolean isAuthorized(String fromAddress, String multySignContractAddress, String erc20Address) throws Exception;
}
