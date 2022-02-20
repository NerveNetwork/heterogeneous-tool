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
public class EthContext {

    public static final String symbol = "ETH";
    public static final String chainName = "Ethereum";
    public static final String mainRpcAddress = "http://geth.nerve.network";
    public static final int mainChainId = 1;
    public static final String mainMultiCallAddress = "0xf5b4224Fae4f3900417e73Ea626f86476D2181f3";

    public static final String testRpcAddress = "https://ropsten.infura.io/v3/7e086d9f3bdc48e4996a3997b33b032f";
    public static final int testChainId = 3;
    public static final String testMultiCallAddress = "0x47c323E4F1845476A133D75e59163F8f11E1Ef74";
    public static String rpcAddress = "http://geth.nerve.network";

}
