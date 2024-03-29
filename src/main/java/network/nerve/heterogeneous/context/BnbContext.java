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

package network.nerve.heterogeneous.context;

/**
 * @author: Loki
 * @date: 2020/11/18
 */
public class BnbContext {

    public static final String symbol = "BNB";
    public static final String chainName = "BSC";
    public static final String mainRpcAddress = "https://bsc-dataseed.binance.org/";
    public static final int mainChainId = 56;
    public static final String mainMultiCallAddress = "0x07616A4fb60F854054137A7b9b5C3f8c8dd2dc01"; //批量查询合约地址

    public static final String testRpcAddress = "https://data-seed-prebsc-1-s2.binance.org:8545/";
    public static final int testChainId = 97;
    public static final String testMultiCallAddress = "0x2e31a3FBE1796c1CeC99BD2F3E87c0f085d2afB1";
    public static String rpcAddress = "https://bsc-dataseed.binance.org/";

}
