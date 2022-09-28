/**
 * MIT License
 * <p>
 * Copyright (c) 2019-2020 nerve.network
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
package network.nerve.trx;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import network.nerve.heterogeneous.core.TrxWalletApi;
import network.nerve.heterogeneous.model.TrxEstimateSun;
import network.nerve.heterogeneous.utils.TrxUtil;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.LoggerFactory;
import org.tron.trident.abi.FunctionEncoder;
import org.tron.trident.abi.datatypes.Function;
import org.tron.trident.core.ApiWrapper;
import org.tron.trident.core.key.KeyPair;
import org.tron.trident.core.transaction.TransactionBuilder;
import org.tron.trident.proto.Chain;
import org.tron.trident.proto.Contract;
import org.tron.trident.proto.Response;

import java.math.BigInteger;
import java.util.List;

import static network.nerve.heterogeneous.constant.TrxConstant.EMPTY_STRING;
import static network.nerve.heterogeneous.constant.TrxConstant.TRX_100;


/**
 * @author: Mimi
 * @date: 2020-03-18
 */
public class Base {

    protected String address = "";
    protected String priKey = "";
    protected String multySignContractAddress = "";
    protected byte VERSION = 3;
    protected List<String> list;
    private ApiWrapper wrapper;
    protected TrxWalletApi walletApi;

    @BeforeClass
    public static void initClass() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger logger = context.getLogger("io.grpc");
        logger.setAdditive(false);
        logger.setLevel(Level.INFO);
    }

    @Before
    public void setUp() throws Exception {
        walletApi = TrxWalletApi.getInstance(EMPTY_STRING);
        wrapper = walletApi.getWrapper();
    }

    protected void setMain() {
        walletApi = TrxWalletApi.getInstance("endpoint:tron.nerve.network");
        //walletApi = TrxWalletApi.getInstance("76f3c2b5-357a-4e6c-aced-9e1c42179717");
        wrapper = walletApi.getWrapper();
    }

    protected String sendTx(String fromAddress, String priKey, Function txFunction, BigInteger value, String contract) throws Exception {
        // 估算feeLimit
        TrxEstimateSun estimateSun = walletApi.estimateSunUsed(fromAddress, contract, txFunction, value);
        if (estimateSun.isReverted()) {
            System.err.println(String.format("交易验证失败，原因: %s", estimateSun.getRevertReason()));
            throw new RuntimeException(estimateSun.getRevertReason());
        }
        BigInteger feeLimit = TRX_100;
        if (estimateSun.getSunUsed() > 0) {
            feeLimit = BigInteger.valueOf(estimateSun.getSunUsed());
        }
        System.out.println(String.format("估算的feeLimit: %s", TrxUtil.convertSunToTrx(feeLimit).toPlainString()));
        value = value == null ? BigInteger.ZERO : value;
        String encodedHex = FunctionEncoder.encode(txFunction);
        Contract.TriggerSmartContract trigger =
                Contract.TriggerSmartContract.newBuilder()
                        .setOwnerAddress(ApiWrapper.parseAddress(fromAddress))
                        .setContractAddress(ApiWrapper.parseAddress(contract))
                        .setData(ApiWrapper.parseHex(encodedHex))
                        .setCallValue(value.longValue())
                        .build();

        Response.TransactionExtention txnExt = wrapper.blockingStub.triggerContract(trigger);
        TransactionBuilder builder = new TransactionBuilder(txnExt.getTransaction());
        builder.setFeeLimit(feeLimit.longValue());

        Chain.Transaction signedTxn = wrapper.signTransaction(builder.build(), new KeyPair(priKey));
        String txHash = TrxUtil.calcTxHash(signedTxn);
        System.out.println("txHash => " + txHash);
        Response.TransactionReturn ret = wrapper.blockingStub.broadcastTransaction(signedTxn);
        System.out.println(String.format("======== Result: %s ", ret.toString()));
        return txHash;
    }
}
