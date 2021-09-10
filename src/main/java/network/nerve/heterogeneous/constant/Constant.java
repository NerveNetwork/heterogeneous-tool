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

package network.nerve.heterogeneous.constant;

import java.math.BigInteger;

/**
 * @author: Loki
 * @date: 2020/11/18
 */
public interface Constant {

    String METHOD_CROSS_OUT = "crossOut";

    BigInteger ESTIMATE_GAS = BigInteger.valueOf(1000000L);

    String ZERO_ADDRESS = "0x0000000000000000000000000000000000000000";

    BigInteger GAS_LIMIT_OF_MAIN = BigInteger.valueOf(21000L);
    BigInteger GAS_LIMIT_OF_RECHARGE_MAIN = BigInteger.valueOf(35000L);

    BigInteger GAS_LIMIT_OF_ERC20 = BigInteger.valueOf(60000L);

    String FORWARD_PATH = "ethCall";
}
