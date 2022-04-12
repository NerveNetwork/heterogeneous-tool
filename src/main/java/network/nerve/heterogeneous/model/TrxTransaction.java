package network.nerve.heterogeneous.model;

import org.tron.trident.proto.Chain;

import java.math.BigInteger;

public class TrxTransaction {

    private Chain.Transaction tx;
    private String hash;
    private String from;
    private String to;
    private BigInteger value;
    private String input;
    private Chain.Transaction.Contract.ContractType type;


    public TrxTransaction() {
    }

    public TrxTransaction(Chain.Transaction tx, String hash, String from, String to, BigInteger value, String input,
                          Chain.Transaction.Contract.ContractType type) {
        this.tx = tx;
        this.hash = hash;
        this.from = from;
        this.to = to;
        this.value = value;
        this.input = input;
        this.type = type;
    }

    public Chain.Transaction getTx() {
        return tx;
    }

    public void setTx(Chain.Transaction tx) {
        this.tx = tx;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public BigInteger getValue() {
        return value;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public Chain.Transaction.Contract.ContractType getType() {
        return type;
    }

    public void setType(Chain.Transaction.Contract.ContractType type) {
        this.type = type;
    }

}
