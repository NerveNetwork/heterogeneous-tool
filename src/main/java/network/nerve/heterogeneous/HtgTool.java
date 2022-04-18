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

import network.nerve.heterogeneous.core.HtgWalletApi;
import network.nerve.heterogeneous.core.MetaMaskWalletApi;
import network.nerve.heterogeneous.core.WalletApi;
import network.nerve.heterogeneous.model.HtgConfig;
import network.nerve.heterogeneous.model.MultiCallModel;
import network.nerve.heterogeneous.model.TryMultiCallReturn;
import network.nerve.heterogeneous.utils.HexUtil;
import network.nerve.heterogeneous.utils.ListUtil;
import org.bouncycastle.util.encoders.Hex;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: PierreLuo
 * @date: 2021/10/19
 */
public class HtgTool {
    private static Map<Integer, HtgWalletApi> toolMap = new HashMap<>();

    public static void initOne(HtgConfig htgConfig) {
        toolMap.put(htgConfig.getChainId(), HtgWalletApi.getInstance(htgConfig.getSymbol(), htgConfig.getChainName(), htgConfig.getRpcAddress(), htgConfig.getChainId()));
    }

    public static void initCollection(List<HtgConfig> configs) {
        for (HtgConfig htgConfig : configs) {
            toolMap.put(htgConfig.getChainId(), HtgWalletApi.getInstance(htgConfig.getSymbol(), htgConfig.getChainName(), htgConfig.getRpcAddress(), htgConfig.getChainId()));
        }
    }

    public static WalletApi getWalletApi(Integer chainId) {
        return toolMap.get(chainId);
    }

    public static MetaMaskWalletApi getMetaMaskWalletApi(Integer chainId) {
        return toolMap.get(chainId);
    }

    public static Function createAggregateFunction(List<MultiCallModel> multiCallModelList) {
        if (multiCallModelList == null || multiCallModelList.isEmpty()) {
            return null;
        }

        List<DynamicStruct> dynamicStructList = new ArrayList<>();
        for (int i = 0; i < multiCallModelList.size(); i++) {
            MultiCallModel callModel = multiCallModelList.get(i);
            Address tokenAddress = new Address(callModel.getContractAddress());
            String encodeFunction = FunctionEncoder.encode(callModel.getCallFunction());
            DynamicStruct dynamicStruct = new DynamicStruct(tokenAddress, new DynamicBytes(Hex.decode(encodeFunction.substring(2).getBytes())));
            dynamicStructList.add(dynamicStruct);

        }
        Function aggregateFunction = new Function("aggregate", ListUtil.of(
                new DynamicArray(DynamicStruct.class, dynamicStructList)),
                ListUtil.of(new TypeReference<Uint256>() {
                }, new TypeReference<DynamicArray<DynamicBytes>>() {
                })
        );

        return aggregateFunction;
    }

    public static Function createTryAggregateFunction(List<MultiCallModel> multiCallModelList) {
        if (multiCallModelList == null || multiCallModelList.isEmpty()) {
            return null;
        }

        List<DynamicStruct> dynamicStructList = new ArrayList<>();
        for (int i = 0; i < multiCallModelList.size(); i++) {
            MultiCallModel callModel = multiCallModelList.get(i);
            Address tokenAddress = new Address(callModel.getContractAddress());
            String encodeFunction = FunctionEncoder.encode(callModel.getCallFunction());
            DynamicStruct dynamicStruct = new DynamicStruct(tokenAddress, new DynamicBytes(Hex.decode(encodeFunction.substring(2).getBytes())));
            dynamicStructList.add(dynamicStruct);

        }

        Function aggregateFunction = new Function("tryAggregate", ListUtil.of(
                new Bool(false),
                new DynamicArray(DynamicStruct.class, dynamicStructList)),
                ListUtil.of(
                        new TypeReference<DynamicArray<TryMultiCallReturn>>() {
                        }));

        return aggregateFunction;
    }

    public static List<List<Type>> processMultiCallResult(List<DynamicBytes> dynamicBytesList, List<MultiCallModel> multiCallModelList) {
        List<List<Type>> multiResultList = new ArrayList<>();
        for (int i = 0; i < dynamicBytesList.size(); i++) {
            DynamicBytes dynamicBytes = dynamicBytesList.get(i);
            MultiCallModel callModel = multiCallModelList.get(i);

            Function callFunction = callModel.getCallFunction();
            String value = HexUtil.encode(dynamicBytes.getValue());
            List<Type> resultType = FunctionReturnDecoder.decode(value, callFunction.getOutputParameters());

            multiResultList.add(resultType);
        }
        return multiResultList;
    }

    public static List<List<Type>> processTryMultiCallResult(List<TryMultiCallReturn> returnList, List<MultiCallModel> multiCallModelList) {
        List<List<Type>> multiResultList = new ArrayList<>();
        for (int i = 0; i < returnList.size(); i++) {
            TryMultiCallReturn callReturn = returnList.get(i);
            Bool bool = (Bool) callReturn.getValue().get(0);
            if (bool.getValue()) {
                DynamicBytes dynamicBytes = (DynamicBytes) callReturn.getValue().get(1);
                MultiCallModel callModel = multiCallModelList.get(i);
                Function callFunction = callModel.getCallFunction();
                String value = HexUtil.encode(dynamicBytes.getValue());
                List<Type> resultType = FunctionReturnDecoder.decode(value, callFunction.getOutputParameters());
                resultType.add(0, bool);
                multiResultList.add(resultType);
            } else {
                List<Type> resultType = new ArrayList<>();
                resultType.add(bool);
                multiResultList.add(resultType);
            }
        }
        return multiResultList;
    }
}
