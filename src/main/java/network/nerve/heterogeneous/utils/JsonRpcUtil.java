package network.nerve.heterogeneous.utils;

import org.apache.http.message.BasicHeader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JSON-RPC 请求工具
 * @author: PierreLuo
 * @date: 2019-07-01
 */
public class JsonRpcUtil {

    private static final String ID = "id";
    private static final String JSONRPC = "jsonrpc";
    private static final String METHOD = "method";
    private static final String PARAMS = "params";
    private static final String DEFAULT_ID = "1";
    private static final String JSONRPC_VERSION = "2.0";
    private static final String CHAIN = "chain";
    private static final String ARGS = "args";

    public static RpcResult request(String requestURL, String method, List<Object> params) {
        return request(requestURL, method, params, null);
    }
    public static RpcResult request(String requestURL, String method, List<Object> params, List<BasicHeader> headers) {
        RpcResult rpcResult;
        try {
            Map<String, Object> map = new HashMap<>(8);
            map.put(ID, DEFAULT_ID);
            map.put(JSONRPC, JSONRPC_VERSION);
            map.put(METHOD, method);
            map.put(PARAMS, params);
            String resultStr = HttpClientUtil.post(requestURL, map, headers);
            rpcResult = JSONUtils.json2pojo(resultStr, RpcResult.class);
        } catch (Exception e) {
            rpcResult = RpcResult.failed(new RpcResultError(RpcErrorCode.SYS_UNKNOWN_EXCEPTION.getCode(), e.getMessage(), null));
        }
        return rpcResult;
    }

    public static RpcResult requestForMetaMask(String requestURL, String chain, String method, List<Object> params) {
        RpcResult rpcResult;
        try {
            Map<String, Object> map = new HashMap<>(8);
            map.put(CHAIN, chain);
            map.put(METHOD, method);
            map.put(ARGS, params);
            String resultStr = HttpClientUtil.post(requestURL, map);
            rpcResult = JSONUtils.json2pojo(resultStr, RpcResult.class);
        } catch (Exception e) {
            rpcResult = RpcResult.failed(new RpcResultError(RpcErrorCode.SYS_UNKNOWN_EXCEPTION.getCode(), e.getMessage(), null));
        }
        return rpcResult;
    }

}
