package network.nerve.heterogeneous.model;

import org.web3j.abi.datatypes.Type;

import java.util.List;

public class MultiCallResult {

    private long blockHeight;

    private CallError callError;

    private List<List<Type>> multiResultList;

    public MultiCallResult() {
    }

    public MultiCallResult(long blockHeight, List<List<Type>> multiResultList) {
        this.blockHeight = blockHeight;
        this.multiResultList = multiResultList;
    }

    public long getBlockHeight() {
        return blockHeight;
    }

    public void setBlockHeight(long blockHeight) {
        this.blockHeight = blockHeight;
    }

    public CallError getCallError() {
        return callError;
    }

    public void setCallError(CallError callError) {
        this.callError = callError;
    }

    public List<List<Type>> getMultiResultList() {
        return multiResultList;
    }

    public void setMultiResultList(List<List<Type>> multiResultList) {
        this.multiResultList = multiResultList;
    }
}
