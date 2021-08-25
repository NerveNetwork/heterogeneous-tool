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
import network.nerve.heterogeneous.utils.JSONUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.web3j.crypto.Hash;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static network.nerve.heterogeneous.utils.HttpClientUtil.getHttpClient;

/**
 * @author: PierreLuo
 * @date: 2021/8/10
 */
public class TrustWalletLogoTest {

    private Map<String, String[]> map = new HashMap<>();
    private Map<String, ChainInfo> keyChainMap = new HashMap<>();
    private Map<String, List<String>> symbolPathMap = new ConcurrentHashMap<>();
    private Set<String> errorSet = new HashSet<>();
    private String genPath = "/Users/pierreluo/Nuls/NERVE/assets-master/gen";
    private String genDuplicatePath = "/Users/pierreluo/Nuls/NERVE/assets-master/gen/duplicate";
    private String genOnlyLogoPath = "/Users/pierreluo/Nuls/NERVE/assets-master/genOnlyLogo";
    private Set<String> allowChain = new HashSet<>();

    @BeforeClass
    public static void beforeClass() {
        ObjectMapper objectMapper = JSONUtils.getInstance();
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Before
    public void before() {
        this.checkDir(genPath);
        this.checkDir(genOnlyLogoPath);
        this.checkDir(genDuplicatePath);
        allowChain.add("ethereum");
        allowChain.add("binance");
        allowChain.add("harmony");
        allowChain.add("nuls");
        allowChain.add("polygon");
        allowChain.add("tron");
        allowChain.stream().forEach(name -> {
            this.checkDir(genPath + "/" + name);
            this.checkDir(genOnlyLogoPath + "/" + name);
        });
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
    private static class ChainInfo {
        public String chainName;
        public String suffix;

        public ChainInfo(String chainName, String suffix) {
            this.chainName = chainName;
            this.suffix = suffix;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("{");
            sb.append("\"chainName\":")
                    .append('\"').append(chainName).append('\"');
            sb.append(",\"suffix\":")
                    .append('\"').append(suffix).append('\"');
            sb.append('}');
            return sb.toString();
        }
    }
    
    protected ChainInfo getChainName(String path) {
        String key = "blockchains";
        int index = path.indexOf(key);
        String result = path.substring(index + key.length() + 1);
        int lastIndexOf = result.lastIndexOf("/");
        index = result.indexOf("/");
        String chainName = result.substring(0, index);
        return new ChainInfo(chainName, result.substring(0, lastIndexOf));
    }

    @Test
    public void chainNameTest() {
        String path = "/Users/pierreluo/Nuls/NERVE/assets-master/blockchains/ethereum/assets/0xF6e35f25f9810807343B1e585fFd6Bfda7c7d455/info.json";
        ChainInfo chainInfo = getChainName(path);
        System.out.println(chainInfo.toString());
    }
    
    @Test
    public void mainTest() throws Exception {
        File dir = new File("/Users/pierreluo/Nuls/NERVE/assets-master/blockchains");
        loadFileKey(dir, 0);
        /*Set<Map.Entry<String, List<String>>> entries = symbolPathMap.entrySet();
        for (Map.Entry<String, List<String>> entry : entries) {
            if (entry.getValue().size() > 1) {
                System.out.println(entry.getKey() + "==>" + Arrays.toString(entry.getValue().toArray()));
            }
        }*/
        System.out.println("缓存建立完成");
        TimeUnit.SECONDS.sleep(1);
        loadFile(dir, 0);
        System.out.println("结束");
        InputStream in = System.in;
        in.read();
    }

    protected void loadFileKey(File file, int deep) throws IOException {
        if (!file.isDirectory()) {
            String path = file.getPath();
            boolean info = path.contains("info.json");
            if (!info) {
                return;
            }
            String key = null;
            ChainInfo chainInfo = null;
            File checkDir = null;
            try {
                chainInfo = this.getChainName(path);
                if (!allowChain.contains(chainInfo.chainName)) {
                    return;
                }
                int indexOf = path.indexOf("info.json");
                key = path.substring(0, indexOf);
                keyChainMap.put(key, chainInfo);
                String s = IoUtils.readCharsToString(new File(path), StandardCharsets.UTF_8.toString());
                String[] parseSymbolArray = this.parseSymbol(s);
                if (parseSymbolArray == null) {
                    errorSet.add(key);
                } else {
                    String symbol = parseSymbolArray[1];
                    if (symbol.contains("/")) {
                        errorSet.add(key);
                        return;
                    }
                    String symbolPathKey = chainInfo.chainName + "_" + symbol;
                    List<String> list = symbolPathMap.getOrDefault(symbolPathKey, new ArrayList<>());
                    list.add(path);
                    symbolPathMap.put(symbolPathKey, list);
                    map.put(key, parseSymbolArray);
                }
            } catch (Exception e) {
                if (chainInfo != null) {
                    this.checkDir(genPath + "/error/" + chainInfo.suffix);
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
            boolean info = path.contains("info.json");
            boolean logo = path.contains("logo.png");
            if (!info && !logo) {
                return;
            }
            ChainInfo chainInfo = null;
            try {
                chainInfo = this.getChainName(path);
                int indexOf = 0;
                if (info) {
                    indexOf = path.indexOf("info.json");
                } else {
                    indexOf = path.indexOf("logo.png");
                }
                String key = path.substring(0, indexOf);
                if (errorSet.contains(key)) {
                    File errorFile = new File(path);
                    this.checkDir(genPath + "/error/" + chainInfo.suffix + "/" + "");
                    IoUtils.writeBytes(new FileOutputStream(genPath + "/error/" + chainInfo.suffix + "/" + errorFile.getName()), IoUtils.readBytes(errorFile));
                    return;
                }
                String[] ss = map.get(key);
                if (ss != null) {
                    String orginText = ss[0];
                    String symbol = ss[1];
                    // {"chainName":"ethereum","suffix":"ethereum/assets/0xF6e35f25f9810807343B1e585fFd6Bfda7c7d455"}
                    /*// 存在重名冲突的symbol, 存储到冲突文件夹中
                    List<String> list = symbolPathMap.get(symbol);
                    if (list.size() > 1) {
                        File errorFile = new File(path);
                        this.checkDir(genDuplicatePath + "/" + symbol + "/" + chainInfo.suffix);
                        IoUtils.writeBytes(new FileOutputStream(genDuplicatePath + "/" + symbol + "/" + chainInfo.suffix + "/" + errorFile.getName()), IoUtils.readBytes(errorFile));
                        return;
                    }*/
                    if (info) {
                        IoUtils.writeString(new File(genPath + "/" + chainInfo.chainName + "/" + symbol + ".json"), orginText, StandardCharsets.UTF_8.name());
                    }
                    if (logo) {
                        byte[] bytes = IoUtils.readBytes(new File(path));
                        IoUtils.writeBytes(new FileOutputStream(genPath + "/" + chainInfo.chainName + "/" + symbol + ".png"), bytes);
                        IoUtils.writeBytes(new FileOutputStream(genOnlyLogoPath + "/" + chainInfo.chainName + "/" + symbol + ".png"), bytes);
                    }
                    return;
                }
            } catch (Exception e) {
                System.err.println("Exception, error path: " + path + ", error: " + e.getMessage());
                if (chainInfo != null) {
                    File errorFile = new File(path);
                    this.checkDir(genPath + "/error/" + chainInfo.suffix);
                    IoUtils.writeBytes(new FileOutputStream(genPath + "/error/" + chainInfo.suffix + "/" + errorFile.getName()), IoUtils.readBytes(errorFile));
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

    public static void main(String[] args) {
        String s = "JNTR/B";
        System.out.println(s.replaceAll("/", "\\\\/"));
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

    protected String cleanQuote(String data) {
        if (data.startsWith("'")) {
            data = data.substring(1, data.length() - 1);
        }
        return data;
    }

    @Test
    public void verifyLogo() throws Exception {
        // update `ns_asset_config` set `icon`='https://nuls-cf.oss-us-west-1.aliyuncs.com/icon/XXX.png' where id = 18508;
        List<String[]> addLogoList = new ArrayList<>();
        List<String> noLogoList = new ArrayList<>();
        List<String> updateSqlList = new ArrayList<>();
        Map<String, Boolean> hasRequestSymbolMap = new HashMap<>();
        String filePath = "/Users/pierreluo/Nuls/NERVE/ns_asset_config.sql";
        List<String> list = IOUtils.readLines(new FileInputStream(filePath), StandardCharsets.UTF_8.name());
        for (String line : list) {
            if (!line.startsWith("INSERT INTO `ns_asset_config` VALUES (")) {
                continue;
            }
            int start = line.indexOf("(");
            int end = line.lastIndexOf(")");
            String data = line.substring(start + 1, end);
            String[] array = data.split(",");
            String logo = array[10];
            if (!this.isEmptyLogo(logo)) {
                continue;
            }
            String id = this.cleanQuote(array[0].trim());
            String symbol = this.cleanQuote(array[7].trim());
            String symbolUpper = symbol.toUpperCase();
            boolean isUpper = symbolUpper.equals(symbol);
            Boolean haveLogo = hasRequestSymbolMap.get(symbol);
            if (haveLogo != null) {
                if (haveLogo) {
                    addLogoList.add(new String[]{id, symbol});
                    continue;
                }
                haveLogo = hasRequestSymbolMap.get(symbolUpper);
                if (haveLogo != null && haveLogo) {
                    addLogoList.add(new String[]{id, symbolUpper});
                    continue;
                }
                noLogoList.add(data);
                continue;
            }
            String logoUrl = String.format("https://nuls-cf.oss-us-west-1.aliyuncs.com/icon/%s.png", symbol);
            String verify = this.timeOutRequestFunction(logoUrl, 0);
            if (verify.contains("The specified key does not exist")) {
                hasRequestSymbolMap.put(symbol, false);
                if (isUpper) {
                    //System.err.println("没有Logo: " + data);
                    noLogoList.add(data);
                    continue;
                }
                logoUrl = String.format("https://nuls-cf.oss-us-west-1.aliyuncs.com/icon/%s.png", symbolUpper);
                verify = this.timeOutRequestFunction(logoUrl, 0);
                if (verify.contains("The specified key does not exist")) {
                    noLogoList.add(data);
                    hasRequestSymbolMap.put(symbolUpper, false);
                    continue;
                }
                addLogoList.add(new String[]{id, symbolUpper});
                hasRequestSymbolMap.put(symbolUpper, true);
            } else if (verify.contains("PNG")){
                addLogoList.add(new String[]{id, symbol});
                hasRequestSymbolMap.put(symbol, true);
            } else {
                noLogoList.add(data);
                hasRequestSymbolMap.put(symbol, false);
                continue;
            }
        }
        for (String[] add : addLogoList) {
            updateSqlList.add(
                    String.format("update `ns_asset_config` set `icon`='https://nuls-cf.oss-us-west-1.aliyuncs.com/icon/%s.png' where id = %s;", add[1], add[0])
            );
        }

        String noLogoFile = "/Users/pierreluo/Nuls/NERVE/noLogoList.txt";
        String updateLogoFile = "/Users/pierreluo/Nuls/NERVE/updateLogoList.txt";
        IOUtils.writeLines(noLogoList, null, new FileOutputStream(noLogoFile), StandardCharsets.UTF_8.name());
        IOUtils.writeLines(updateSqlList, null, new FileOutputStream(updateLogoFile), StandardCharsets.UTF_8.name());
    }



    @Test
    public void requestTest() throws Exception {
        String url = "https://nuls-cf.oss-us-west-1.aliyuncs.com/icon/BNB.png";
        String result = this.timeOutRequestFunction(url, 0);
        System.out.println(result);
    }

    @Test
    public void substring() {
        String s = "INSERT INTO `ns_asset_config` VALUES ('18505', 'NULS', 'NULS', '1', '1', '8', 'NULS', 'NULS', null, '', 'https://nuls-cf.oss-us-west-1.aliyuncs.com/icon/2021_NULS_ICON_Tra-01.png', '2021-08-11 09:43:16', '0.5010', '8', '[{\\\"chainName\\\":\\\"Ethereum\\\",\\\"contractAddress\\\":\\\"0xa2791bdf2d5055cda4d46ec17f9f429568275047\\\",\\\"heterogeneousChainId\\\":101,\\\"heterogeneousChainMultySignAddress\\\":\\\"0x6758d4c4734ac7811358395a8e0c3832ba6ac624\\\",\\\"token\\\":true},{\\\"chainName\\\":\\\"BSC\\\",\\\"contractAddress\\\":\\\"0x8cd6e29d3686d24d3c2018cee54621ea0f89313b\\\",\\\"heterogeneousChainId\\\":102,\\\"heterogeneousChainMultySignAddress\\\":\\\"0x3758aa66cad9f2606f1f501c9cb31b94b713a6d5\\\",\\\"token\\\":true},{\\\"chainName\\\":\\\"Heco\\\",\\\"contractAddress\\\":\\\"0xd938e45680da19ad36646ae8d4c671b2b1270f39\\\",\\\"heterogeneousChainId\\\":103,\\\"heterogeneousChainMultySignAddress\\\":\\\"0x23023c99dcede393d6d18ca7fb08541b3364fa90\\\",\\\"token\\\":true},{\\\"chainName\\\":\\\"OKExChain\\\",\\\"contractAddress\\\":\\\"0x8cd6e29d3686d24d3c2018cee54621ea0f89313b\\\",\\\"heterogeneousChainId\\\":104,\\\"heterogeneousChainMultySignAddress\\\":\\\"0x3758aa66cad9f2606f1f501c9cb31b94b713a6d5\\\",\\\"token\\\":true},{\\\"chainName\\\":\\\"ONE\\\",\\\"contractAddress\\\":\\\"0x8b8e48a8cc52389cd16a162e5d8bd514fabf4ba0\\\",\\\"heterogeneousChainId\\\":105,\\\"heterogeneousChainMultySignAddress\\\":\\\"0x3758aa66cad9f2606f1f501c9cb31b94b713a6d5\\\",\\\"token\\\":true},{\\\"chainName\\\":\\\"MATIC\\\",\\\"contractAddress\\\":\\\"0x8b8e48a8cc52389cd16a162e5d8bd514fabf4ba0\\\",\\\"heterogeneousChainId\\\":106,\\\"heterogeneousChainMultySignAddress\\\":\\\"0x3758aa66cad9f2606f1f501c9cb31b94b713a6d5\\\",\\\"token\\\":true},{\\\"chainName\\\":\\\"KCS\\\",\\\"contractAddress\\\":\\\"0x8b8e48a8cc52389cd16a162e5d8bd514fabf4ba0\\\",\\\"heterogeneousChainId\\\":107,\\\"heterogeneousChainMultySignAddress\\\":\\\"0xf0e406c49c63abf358030a299c0e00118c4c6ba5\\\",\\\"token\\\":true}]', '1', '1', '1', '1');";
        System.out.println(s.startsWith("INSERT INTO `ns_asset_config` VALUES ("));
        int start = s.indexOf("(");
        int end = s.lastIndexOf(")");
        String data = s.substring(start + 1, end);
        String[] array = data.split(",");
        String logo2 = array[10];
        System.out.println(data);
        System.out.println(logo2);

        String id = this.cleanQuote(array[0].trim());
        System.out.println(id);
    }

    protected boolean isEmptyLogo(String data) {
        if (data == null) {
            return true;
        }
        data = data.trim();
        if (data.length() == 0) {
            return true;
        }
        if ("null".equals(data)) {
            return true;
        }
        if ("''".equals(data)) {
            return true;
        }
        return false;
    }

    protected String timeOutRequestFunction(String url, int count) throws Exception {
        System.out.println(String.format("[%s]第%s次请求", url, count + 1));
        int timeOut = 3 * 1000;
        StringBuffer buffer;
        HttpGet httpGet = new HttpGet(url);
        // 配置请求的超时设置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(timeOut)
                .setConnectTimeout(timeOut).setSocketTimeout(timeOut).build();
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        try {
            response = getHttpClient(url).execute(httpGet,
                    HttpClientContext.create());
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
            return result;
        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);

            if (msg.contains("timed out")) {
                return this.timeOutRequestFunction(url, count + 1);
            }
            throw e;
        } finally {
            try {
                if (response != null)
                    response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
