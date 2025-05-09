package network.nerve;

import network.nerve.heterogeneous.utils.HtgCommonTools;
import network.nerve.heterogeneous.utils.JsonRpcUtil;
import network.nerve.heterogeneous.utils.RpcResult;
import network.nerve.heterogeneous.utils.StringUtils;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.math.ec.ECPoint;
import org.junit.Test;
import org.web3j.crypto.*;
import org.web3j.ens.EnsResolver;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static network.nerve.heterogeneous.constant.Constant.FORWARD_PATH;
import static org.web3j.crypto.Sign.CURVE_PARAMS;

/**
 * Unit test for simple App.
 */
public class AppTest {

    private static final ECDomainParameters CURVE = new ECDomainParameters(CURVE_PARAMS.getCurve(), CURVE_PARAMS.getG(), CURVE_PARAMS.getN(), CURVE_PARAMS.getH());
    @Test
    public void jsonrpcForMetaMaskTest() {
        String requestURL = "http://192.168.1.132:8083/nabox-api/api";
        String url = requestURL;
        if (requestURL.endsWith("/")) {
            url = url + FORWARD_PATH;
        } else {
            url = url + "/" + FORWARD_PATH;
        }
        System.out.println(url);
        String method = "eth_getBlockByNumber";
        RpcResult result = JsonRpcUtil.requestForMetaMask(url, "BSC", method, Arrays.asList("0x1", false));
        System.out.println(result.toString());
    }


    @Test
    public void testEns() {
        String apiUrl = "https://eth-mainnet.public.blastapi.io";
        Web3j web3j = Web3j.build(new HttpService(apiUrl));
        EnsResolver ens = new EnsResolver(web3j, 300);
        System.out.println(ens.resolve("000.eth"));

        //byte[] nameHash = NameHash.nameHashAsBytes("000.eth");
    }

    @Test
    public void testSignDataV4() throws Exception {
        String pri = "b16d78f78ed3341cdcf5e0018cd4677bbddb4cc30fb7ef5f2794375e1cdc2e8c";
        //String json = "{\"types\":{\"Vote\":[{\"name\":\"from\",\"type\":\"address\"},{\"name\":\"space\",\"type\":\"string\"},{\"name\":\"timestamp\",\"type\":\"uint64\"},{\"name\":\"proposal\",\"type\":\"string\"},{\"name\":\"choice\",\"type\":\"uint32\"},{\"name\":\"reason\",\"type\":\"string\"},{\"name\":\"app\",\"type\":\"string\"},{\"name\":\"metadata\",\"type\":\"string\"}],\"EIP712Domain\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"version\",\"type\":\"string\"}]},\"domain\":{\"name\":\"snapshot\",\"version\":\"0.1.4\"},\"primaryType\":\"Vote\",\"message\":{\"from\":\"0x09a5e998668fd4d56fc4c20b9e588d87666d6c94\",\"space\":\"naboxdao.eth\",\"timestamp\":\"1746756338\",\"proposal\":\"0xf6343cb3e8a1cf4374839082e3ea53839a3eeab95df860d0ee6b0fa92e8f497f\",\"choice\":\"5\",\"reason\":\"1000\",\"app\":\"snapshot-v2\",\"metadata\":\"\"}}";
        //String json = "{\"types\":{\"EIP712Domain\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"version\",\"type\":\"string\"},{\"name\":\"chainId\",\"type\":\"uint256\"},{\"name\":\"verifyingContract\",\"type\":\"address\"}],\"Order\":[{\"name\":\"maker\",\"type\":\"address\"},{\"name\":\"tokenId\",\"type\":\"uint256\"},{\"name\":\"price\",\"type\":\"uint256\"}]},\"primaryType\":\"Order\",\"domain\":{\"name\":\"NFTMarket\",\"version\":\"1\",\"chainId\":1,\"verifyingContract\":\"0x1234567890abcdef1234567890abcdef12345678\"},\"message\":{\"maker\":\"0xabcdef1234567890abcdef1234567890abcdef12\",\"tokenId\":1234,\"price\":1000000000000000000}}";
        String json = "{\"types\":{\"EIP712Domain\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"version\",\"type\":\"string\"},{\"name\":\"chainId\",\"type\":\"uint256\"},{\"name\":\"verifyingContract\",\"type\":\"address\"},{\"name\":\"salt\",\"type\":\"bytes32\"}],\"Order\":[{\"name\":\"maker\",\"type\":\"address\"},{\"name\":\"token\",\"type\":\"address\"},{\"name\":\"amount\",\"type\":\"uint256\"},{\"name\":\"price\",\"type\":\"uint256\"}]},\"primaryType\":\"Order\",\"domain\":{\"name\":\"MyDEX\",\"version\":\"1.0\",\"chainId\":1,\"verifyingContract\":\"0x1234567890abcdef1234567890abcdef12345678\",\"salt\":\"0xabcdef1234567890abcdef1234567890abcdef1234567890abcdef1234567890\"},\"message\":{\"maker\":\"0xabcdef1234567890abcdef1234567890abcdef12\",\"token\":\"0x1111111111111111111111111111111111111111\",\"amount\":1000000000000000000,\"price\":500000000000000000}}";
        String signed = HtgCommonTools.signTypedDataV4(pri, json);
        System.out.println(signed);
    }

    @Test
    public void getTxAndPublicKeyTest() throws Exception {
        Web3j web3j = Web3j.build(new HttpService("https://geth.nerve.network"));
        String txHash = "0xb16d78f78ed3341cdcf5e0018cd4677bbddb4cc30fb7ef5f2794375e1cdc2e8c";
        Transaction tx = web3j.ethGetTransactionByHash(txHash).send().getTransaction().get();
        converPublicKey(tx);
    }

    private void converPublicKey(Transaction tx) {
        ECDSASignature signature = new ECDSASignature(
                Numeric.decodeQuantity(Numeric.prependHexPrefix(tx.getR())),
                Numeric.decodeQuantity(Numeric.prependHexPrefix(tx.getS())));
        byte[] hashBytes = getRawTxHashBytes(tx);
        boolean exist = false;
        boolean success = false;
        List<String> errorList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            BigInteger recover = Sign.recoverFromSignature(i, signature, hashBytes);
            if (recover != null) {
                exist = true;
                String address = "0x" + Keys.getAddress(recover);
                String pubKeyHex = Numeric.toHexStringWithPrefix(recover);
                //System.out.println(String.format("公钥: %s，地址: %s", pubKeyHex, address));
                if (tx.getFrom().toLowerCase().equals(address.toLowerCase())) {
                    success = true;
                    byte[] _pubKey = Numeric.hexStringToByteArray(pubKeyHex);
                    byte[] pubKey;
                    if (_pubKey.length == 65) {
                        _pubKey[0] = 4;
                        pubKey = _pubKey;
                    } else if (_pubKey.length == 64) {
                        pubKey = new byte[_pubKey.length + 1];
                        pubKey[0] = 4;
                        System.arraycopy(_pubKey, 0, pubKey, 1, _pubKey.length);
                    } else {
                        throw new RuntimeException("Error public key from prikey");
                    }
                    ECPoint ecPoint = CURVE.getCurve().decodePoint(pubKey);
                    byte[] encoded = ecPoint.getEncoded(true);
                    System.out.println(String.format("地址: %s", address));
                    System.out.println(String.format("非压缩公钥: %s", Numeric.toHexStringNoPrefix(pubKey)));
                    System.out.println(String.format("压缩公钥: %s", Numeric.toHexStringNoPrefix(encoded)));
                    System.out.println("成功: " + i + ": " + address + String.format(", tx hash: %s, tx index: %s, tx data length: %s, tx chainId: %s", tx.getHash(), tx.getTransactionIndexRaw() != null ? tx.getTransactionIndex() : null, tx.getInput().length(), tx.getChainId()));
                    break;
                } else {
                    errorList.add(address);
                }
            }
        }
        if (!exist) {
            System.err.println(String.format("异常: error, tx hash: %s, tx index: %s, tx data length: %s, tx chainId: %s", tx.getHash(), tx.getTransactionIndexRaw() != null ? tx.getTransactionIndex() : null, tx.getInput().length(), tx.getChainId()));
        }
        if (!success) {
            System.err.println(String.format("失败: tx from: %s, parse from: %s, tx hash: %s, tx index: %s, tx data length: %s, tx chainId: %s", tx.getFrom(), errorList, tx.getHash(), tx.getTransactionIndexRaw() != null ? tx.getTransactionIndex() : null, tx.getInput().length(), tx.getChainId()));
        }
    }

    private byte[] getRawTxHashBytes(Transaction tx) {
        String data = "";
        if (StringUtils.isNotBlank(tx.getInput()) && !"0x".equals(tx.getInput().toLowerCase())) {
            data = tx.getInput();
        }
        RawTransaction rawTx;
        byte[] rawTxEncode;
        if (tx.getMaxPriorityFeePerGas() != null) {
            rawTx = RawTransaction.createTransaction(
                    1,
                    tx.getNonce(),
                    tx.getGas(),
                    tx.getTo(),
                    tx.getValue(),
                    data,
                    new BigInteger(Numeric.cleanHexPrefix(tx.getMaxPriorityFeePerGas()), 16),
                    new BigInteger(Numeric.cleanHexPrefix(tx.getMaxFeePerGas()), 16));
            rawTxEncode = TransactionEncoder.encode(rawTx);
        } else {
            rawTx = RawTransaction.createTransaction(
                    tx.getNonce(),
                    tx.getGasPrice(),
                    tx.getGas(),
                    tx.getTo(),
                    tx.getValue(),
                    data);
            if (tx.getChainId() != null) {
                rawTxEncode = TransactionEncoder.encode(rawTx, tx.getChainId());
            } else {
                rawTxEncode = TransactionEncoder.encode(rawTx);
            }
        }
        byte[] hashBytes = Hash.sha3(rawTxEncode);
        return hashBytes;
    }
}
