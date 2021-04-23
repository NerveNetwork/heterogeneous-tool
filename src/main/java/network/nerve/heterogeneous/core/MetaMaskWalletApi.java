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

import network.nerve.heterogeneous.model.EthSendTransactionPo;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthEstimateGas;

import java.math.BigInteger;

/**
 * @author: PierreLuo
 * @date: 2021/4/21
 */
public interface MetaMaskWalletApi {

    EthSendTransactionPo sendRawTransaction(String privateKey, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to, BigInteger value, String data) throws Exception;

    EthCall validateRawTransaction(String from, String to, String data, BigInteger value) throws Exception;

    EthCall ethCall(String from, String to, BigInteger gasLimit, BigInteger gasPrice, BigInteger value, String data, boolean latest) throws Exception;

    EthEstimateGas ethEstimateGas(String from, String to, BigInteger gasLimit, BigInteger gasPrice, BigInteger value, String data) throws Exception;
}
