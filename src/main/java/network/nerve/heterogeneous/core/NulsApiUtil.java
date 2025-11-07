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


import network.nerve.heterogeneous.model.ContractViewCallForm;
import network.nerve.heterogeneous.utils.*;
import network.nerve.kit.util.ListUtil;

import java.util.List;

/**
 * @author: PierreLuo
 * @date: 2019-08-18
 */
public class NulsApiUtil {

    private static final String ID = "id";
    private static final String JSONRPC = "jsonrpc";
    private static final String METHOD = "method";
    private static final String PARAMS = "params";
    private static final String JSONRPC_VERSION = "2.0";

    public static RpcResult invokeViewJsonrpc(int chainId, String url, ContractViewCallForm form) throws InterruptedException {
        return jsonRpcRequest(url, "invokeView", ListUtil.of(
                chainId,
                form.getContractAddress(),
                form.getMethodName(),
                form.getMethodDesc(),
                form.getArgs()
        ));
    }
    public static RpcResult jsonRpcRequest(String url, String method, List<Object> params, int retryTimes) throws InterruptedException {
        RpcResult rpcResult = JsonRpcUtil.request(url, method, params);
        int count = 0;
        while (isTimedOut(rpcResult)) {
            //logger.warn("JsonRpcRequest - url [{}], request [{}] timed out!", url, method);
            rpcResult = JsonRpcUtil.request(url, method, params);
            if(retryTimes != -1 && ++count >= retryTimes) {
                break;
            }
            if(count % 3 == 0) {
                HttpClientUtil.resetHttpClient(url);
            }
            Thread.sleep(500);
        }
        return rpcResult;
    }

    public static RpcResult jsonRpcRequest(String url, String method, List<Object> params) throws InterruptedException {
        return jsonRpcRequest(url, method, params, 3);
    }

    private static boolean isTimedOut(RpcResult result) {
        RpcResultError error = result.getError();
        if(error == null) {
            return false;
        }
        String errorMsg = error.getMessage();
        if(StringUtils.isBlank(errorMsg)) {
            return false;
        }
        return errorMsg.contains("timed out");
    }




}
