/**
 * MIT License
 * <p>
 * Copyright (c) 2019-2022 nerve.network
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

package network.nerve.heterogeneous.model;


import java.io.IOException;
import java.math.BigInteger;

/**
 * On chain recharge transactionstxdata
 *
 * @author: Loki
 * @date: 2020-02-17
 */
public class UTXOData {

    /**
     * Heterogeneous chain recharge transactionshash / Proposal transactionhash
     */
    private String txid;

    private int vout;

    private BigInteger amount;

    private String preTxHex;

    public UTXOData() {
    }

    public UTXOData(String txid) {
        this.txid = txid;
    }

    public UTXOData(String txid, int vout, BigInteger amount) {
        this.txid = txid;
        this.vout = vout;
        this.amount = amount;
    }

    public UTXOData(String txid, int vout, BigInteger amount, String preTxHex) {
        this.txid = txid;
        this.vout = vout;
        this.amount = amount;
        this.preTxHex = preTxHex;
    }

    public String getPreTxHex() {
        return preTxHex;
    }

    public void setPreTxHex(String preTxHex) {
        this.preTxHex = preTxHex;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public int getVout() {
        return vout;
    }

    public void setVout(int vout) {
        this.vout = vout;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"txid\":")
                .append('\"').append(txid).append('\"');

        sb.append(",\"vout\":")
                .append(vout);

        sb.append(",\"amount\":")
                .append('\"').append(amount).append('\"');

        sb.append('}');
        return sb.toString();
    }
}
