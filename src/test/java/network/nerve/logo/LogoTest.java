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
package network.nerve.logo;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import network.nerve.core.io.IoUtils;
import network.nerve.heterogeneous.context.BnbContext;
import network.nerve.heterogeneous.context.HtContext;
import network.nerve.heterogeneous.core.HtgWalletApi;
import network.nerve.heterogeneous.utils.JSONUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author: PierreLuo
 * @date: 2021/8/10
 */
public class LogoTest {

    private Map<String, String[]> map = new HashMap<>();
    private Map<String, ChainInfo> emptySymbolMap = new HashMap<>();
    private Map<String, String> symbolMap = new ConcurrentHashMap<>();
    private Set<String> errorSet = new HashSet<>();
    private String genPath = "/Users/pierreluo/Nuls/NERVE/tokens-master/gen";
    Executor executor = Executors.newFixedThreadPool(20);

    private static class SymbolFind implements Runnable {

        String key;
        HtgWalletApi bscHtgWalletApi;
        HtgWalletApi hecoHtgWalletApi;
        String chain;
        String address;
        Map<String, String> symbolMap;

        public SymbolFind(String key, HtgWalletApi bscHtgWalletApi, HtgWalletApi hecoHtgWalletApi, String chain, String address, Map<String, String> symbolMap) {
            this.key = key;
            this.bscHtgWalletApi = bscHtgWalletApi;
            this.hecoHtgWalletApi = hecoHtgWalletApi;
            this.chain = chain;
            this.address = address;
            this.symbolMap = symbolMap;
        }

        @Override
        public void run() {
            try {
                String resultSymbol = symbolMap.get(key);
                if (resultSymbol != null) {
                    return;
                }
                HtgWalletApi htgWalletApi = null;
                String symbol;
                String chainName;
                String rpcAddress;
                List<Type> symbolResult = null;
                if ("bsc".equals(chain)) {
                    htgWalletApi = bscHtgWalletApi;
                } else if ("heco".equals(chain)) {
                    htgWalletApi = hecoHtgWalletApi;
                } else {
                    throw new Exception("not support network: " + chain);
                }
                symbolResult = htgWalletApi.callViewFunction(address, getSymbolERC20Function());
                if (symbolResult == null || symbolResult.isEmpty()) {
                    throw new Exception("empty symbol from network: " + chain + ", address: " + address);
                }
                resultSymbol = symbolResult.get(0).getValue().toString();
                symbolMap.put(key, resultSymbol);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private static class ChainInfo {
        public String chainName;
        public String address;

        public ChainInfo(String chainName, String address) {
            this.chainName = chainName;
            this.address = address;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("{");
            sb.append("\"chainName\":")
                    .append('\"').append(chainName).append('\"');
            sb.append(",\"address\":")
                    .append('\"').append(address).append('\"');
            sb.append('}');
            return sb.toString();
        }
    }

    @BeforeClass
    public static void beforeClass() {
        ObjectMapper objectMapper = JSONUtils.getInstance();
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Before
    public void before() {
        this.checkDir(genPath);
    }

    protected ChainInfo getChainName(String path) {
        String key = "tokens-master";
        int index = path.indexOf("tokens-master");
        String result = path.substring(index + key.length() + 1);
        if (result.startsWith("0x")) {
            return new ChainInfo("tp", result.substring(0, 42));
        } else {
            index = result.indexOf("/");
            String checkStr = result.substring(index + 1);
            String address;
            if (!checkStr.startsWith("0x") && !checkStr.startsWith("T")) {
                address = "";
            } else {
                address = result.substring(index + 1, index + 43);
            }
            result = result.substring(0, index);
            return new ChainInfo(result, address);
        }
    }

    @Test
    public void chainNameTest() {
        String path = "/Users/pierreluo/Nuls/NERVE/tokens-master/polygon(matic)/0xe68ff461C0392A86DF024B70b66AAeDC2dDe39E7/info.json";
        ChainInfo chainInfo = getChainName(path);
        System.out.println(chainInfo.toString());
    }

    @Test
    public void fileName() {
        String path = "/Users/pierreluo/Nuls/NERVE/tokens-master/bsc/0x762a68697D7c8A0078Bd1d79BD50d0F2ef8A8AaF/info.shab.rtf";
        File file = new File(path);
        System.out.println(file.getName());
    }

    @Test
    public void testBase() throws IOException {
        String path = "/Users/pierreluo/Nuls/NERVE/tokens-master/polygon(matic)/0xe68ff461C0392A86DF024B70b66AAeDC2dDe39E7/info.json";
        String logoPath = "/Users/pierreluo/Nuls/NERVE/tokens-master/polygon(matic)/0xe68ff461C0392A86DF024B70b66AAeDC2dDe39E7/logo.png";
        String s = IoUtils.readCharsToString(new File(path), StandardCharsets.UTF_8.toString());
        Map<String, Object> json2map = JSONUtils.json2map(s);
        String symbol = json2map.get("symbol").toString().toUpperCase();
        json2map.put("symbol", symbol);
        IoUtils.writeString(new File(genPath + "/" + symbol + ".json"), JSONUtils.obj2PrettyJson(json2map), StandardCharsets.UTF_8.name());
        IoUtils.writeBytes(new FileOutputStream(genPath + "/" + symbol + ".png"), IoUtils.readBytes(new File(logoPath)));
    }

    @Test
    public void parseSymbolTest() throws Exception {
        String path = "/Users/pierreluo/Nuls/NERVE/tokens-master/0x935aC027f9FBe97777bC1E1Ca9D5c238774744e4/info.json";
        String s = IoUtils.readCharsToString(new File(path), StandardCharsets.UTF_8.toString());
        System.out.println(s);
        String[] result = this.parseSymbol(s);
        System.out.println(Arrays.toString(result));
    }

    protected String[] parseSymbol(String s) throws Exception {
        try {
            Map<String, Object> json2map = JSONUtils.json2map(s);
            if (!json2map.containsKey("symbol")) {
                return null;
            }
            String symbol = json2map.get("symbol").toString().toUpperCase();
            json2map.put("symbol", symbol);
            return new String[]{JSONUtils.obj2PrettyJson(json2map), symbol};
        } catch (Exception e) {
            if (!(e instanceof JsonMappingException) && !(e instanceof JsonParseException)) {
                throw e;
            }
            // info.json 不是合法的json字符串时，解析symbol那一行
            String temp = s.substring(s.indexOf("symbol"));
            String json = "{\"" + temp.substring(0, temp.indexOf(",")) + "}";
            Map<String, Object> map = JSONUtils.json2map(json);
            String symbol = map.get("symbol").toString();
            String ss = s.replaceAll("\"" + symbol + "\"", "\"" + symbol.toUpperCase() + "\"");
            return new String[]{ss, symbol.toUpperCase()};
        }
    }
    @Test
    public void mainTest() throws Exception {
        File dir = new File("/Users/pierreluo/Nuls/NERVE/tokens-master/");
        loadFileKey(dir, 0);
        System.out.println("缓存建立完成");
        TimeUnit.SECONDS.sleep(30);
        loadFile(dir, 0);
        System.out.println("结束");
        InputStream in = System.in;
        in.read();
    }

    protected void loadFileKey(File file, int deep) throws IOException {
        if (!file.isDirectory()) {
            String path = file.getPath();
            boolean info = path.contains("info");
            if (!info) {
                return;
            }
            String key = null;
            ChainInfo chainInfo = null;
            File checkDir = null;
            try {
                chainInfo = this.getChainName(path);
                this.checkDir(genPath + "/" + chainInfo.chainName);
                int indexOf = path.indexOf("info");
                key = path.substring(0, indexOf);
                String s = IoUtils.readCharsToString(new File(path), StandardCharsets.UTF_8.toString());
                String[] parseSymbolArray = this.parseSymbol(s);
                if (parseSymbolArray == null) {
                    if (chainInfo.chainName.equals("eos")) {
                        throw new Exception("not support eos chain");
                    }
                    emptySymbolMap.put(key, chainInfo);
                    executor.execute(new SymbolFind(key, bscHtgWalletApi, hecoHtgWalletApi, chainInfo.chainName, chainInfo.address, symbolMap));
                } else {
                    map.put(key, parseSymbolArray);
                }
            } catch (Exception e) {
                if (chainInfo != null) {
                    this.checkDir(genPath + "/error/" + chainInfo.chainName + "/" + chainInfo.address);
                }
                if (key != null) {
                    errorSet.add(key);
                }
                e.printStackTrace();
                System.err.println("Exception, error path: " + path);
            }
            return;
        } else {
            File[] files = file.listFiles();
            for (File _file : files) {
                loadFileKey(_file, deep + 1);
            }
        }
    }

    protected void loadFile(File file, int deep) throws IOException {
        if (!file.isDirectory()) {
            String path = file.getPath();
            boolean info = path.contains("info");
            boolean logo = path.contains("logo");
            if (!info && !logo) {
                return;
            }
            ChainInfo chainInfo = null;
            try {
                chainInfo = this.getChainName(path);
                int indexOf = 0;
                if (info) {
                    indexOf = path.indexOf("info");
                } else {
                    indexOf = path.indexOf("logo");
                }
                String key = path.substring(0, indexOf);
                if (errorSet.contains(key)) {
                    File errorFile = new File(path);
                    this.checkDir(genPath + "/error/" + chainInfo.chainName + "/" + chainInfo.address);
                    IoUtils.writeBytes(new FileOutputStream(genPath + "/error/" + chainInfo.chainName + "/" + chainInfo.address + "/" + errorFile.getName()), IoUtils.readBytes(errorFile));
                    return;
                }
                String[] ss = map.get(key);
                if (ss != null) {
                    String orginText = ss[0];
                    String symbol = ss[1];
                    if (info) {
                        IoUtils.writeString(new File(genPath + "/" + chainInfo.chainName + "/" + symbol + ".json"), orginText, StandardCharsets.UTF_8.name());
                    }
                    if (logo) {
                        IoUtils.writeBytes(new FileOutputStream(genPath + "/" + chainInfo.chainName + "/" + symbol + ".png"), IoUtils.readBytes(new File(path)));
                    }
                    return;
                }
                if (emptySymbolMap.containsKey(key)) {
                    String symbol = symbolMap.get(key);
                    if (symbol == null) {
                        symbol = this.findSymbol(chainInfo.chainName, chainInfo.address);
                        symbol = symbol.toUpperCase();
                        if (symbol == null) {
                            File errorFile = new File(path);
                            this.checkDir(genPath + "/error/" + chainInfo.chainName + "/" + chainInfo.address);
                            IoUtils.writeBytes(new FileOutputStream(genPath + "/error/" + chainInfo.chainName + "/" + chainInfo.address + "/" + errorFile.getName()), IoUtils.readBytes(errorFile));
                            return;
                        }
                        symbolMap.put(key, symbol);
                    }
                    if (info) {
                        IoUtils.writeBytes(new FileOutputStream(genPath + "/" + chainInfo.chainName + "/" + symbol + ".json"), IoUtils.readBytes(new File(path)));
                    }
                    if (logo) {
                        IoUtils.writeBytes(new FileOutputStream(genPath + "/" + chainInfo.chainName + "/" + symbol + ".png"), IoUtils.readBytes(new File(path)));
                    }
                    return;
                }

            } catch (Exception e) {
                System.err.println("Exception, error path: " + path + ", error: " + e.getMessage());
                if (chainInfo != null) {
                    File errorFile = new File(path);
                    this.checkDir(genPath + "/error/" + chainInfo.chainName + "/" + chainInfo.address);
                    IoUtils.writeBytes(new FileOutputStream(genPath + "/error/" + chainInfo.chainName + "/" + chainInfo.address + "/" + errorFile.getName()), IoUtils.readBytes(errorFile));
                }
            }
            return;
        } else {
            File[] files = file.listFiles();
            for (File _file : files) {
                loadFile(_file, deep + 1);
            }
        }
    }

    @Test
    public void symbolTest() throws Exception {
        this.findSymbol("bsc", "0x972207A639CC1B374B893cc33Fa251b55CEB7c07");
    }

    private void checkDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    // PUNK
    private String repeat(String str, int count) {
        StringBuilder sb = new StringBuilder(str);
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }

    HtgWalletApi bscHtgWalletApi = HtgWalletApi.getInstance(BnbContext.symbol, BnbContext.chainName, BnbContext.rpcAddress);
    HtgWalletApi hecoHtgWalletApi = HtgWalletApi.getInstance(HtContext.symbol, HtContext.chainName, HtContext.rpcAddress);
    protected String findSymbol(String chain, String address) throws Exception {
        HtgWalletApi htgWalletApi = null;
        String symbol;
        String chainName;
        String rpcAddress;
        List<Type> symbolResult = null;
        if ("bsc".equals(chain)) {
            htgWalletApi = bscHtgWalletApi;
        } else if ("heco".equals(chain)) {
            htgWalletApi = hecoHtgWalletApi;
        } else {
            throw new Exception("not support network: " + chain);
        }
        symbolResult = htgWalletApi.callViewFunction(address, getSymbolERC20Function());
        if (symbolResult == null || symbolResult.isEmpty()) {
            throw new Exception("empty symbol from network: " + chain + ", address: " + address);
        }
        String resultSymbol = symbolResult.get(0).getValue().toString();
        System.out.println(resultSymbol);
        return resultSymbol;
    }

    public static Function getSymbolERC20Function() {
        ArrayList<Type> in = new ArrayList<>();
        ArrayList<TypeReference<?>> out = new ArrayList<>();
        out.add(new TypeReference<Utf8String>() {});
        return new Function(
                "symbol",
                in,
                out);
    }
}
