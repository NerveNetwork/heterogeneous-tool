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
import network.nerve.heterogeneous.model.MultiCallModel;
import network.nerve.heterogeneous.model.MultiCallResult;
import network.nerve.heterogeneous.utils.RpcResult;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthEstimateGas;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author: PierreLuo
 * @date: 2021/4/21
 */
public interface MetaMaskWalletApi {

    EthSendTransactionPo sendRawTransaction(String privateKey, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to, BigInteger value, String data) throws Exception;

    String sendRawTransactionWithoutBroadcast(String privateKey, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to, BigInteger value, String data) throws Exception;

    EthCall validateRawTransaction(String from, String to, String data, BigInteger value) throws Exception;

    EthCall ethCall(String from, String to, BigInteger gasLimit, BigInteger gasPrice, BigInteger value, String data, boolean latest) throws Exception;

    EthEstimateGas ethEstimateGas(String from, String to, BigInteger gasLimit, BigInteger gasPrice, BigInteger value, String data) throws Exception;

    RpcResult request(String requestURL, String method, List<Object> params);

    String ethSign(String priKey, String dataHex);

    String personalSign(String priKey, String data);

    String signTypedDataV4(String priKey, String json) throws IOException;

    long getBlockHeight() throws Exception;

    /**
     * 批量调用查询接口
     * @param multiCallAddress 批量调用的合约地址
     * @param multiCallModelList
     */
    MultiCallResult multiCall(String multiCallAddress, List<MultiCallModel> multiCallModelList) throws Exception;

    EthSendTransactionPo createTransferERC721Token(String contractAddress, String from, String to, BigInteger tokenId, String data, String privateKey, BigInteger gasLimit, BigInteger gasPrice) throws Exception;

    EthSendTransactionPo createTransferERC1155Token(String contractAddress, String from, String to, List<Uint256> tokenIdList, List<Uint256> values, String data, String privateKey, BigInteger gasLimit, BigInteger gasPrice) throws Exception;

    //估算erc721转账gas
    BigInteger estimateGasForTransferERC721(String contractAddress, String from, String to, BigInteger tokenId, String data) throws Exception;

    //估算erc1155
    BigInteger estimateGasForTransferERC1155(String contractAddress, String from, String to, List<Uint256> tokenIdList, List<Uint256> values, String data) throws Exception;

    BigInteger getERC1155Balance(String address, String contractAddress, BigInteger tokenId) throws Exception;
    //获取totalSupply
    BigInteger getTotalSupply(String contractAddress) throws Exception;

}
