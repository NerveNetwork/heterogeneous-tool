package network.nerve.heterogeneous.model;

import java.util.List;
import org.web3j.abi.datatypes.Type;

public class MultiCallResult {

    private long blockHeight;

    private List<List<Type>> multiResultList;

    private CallError callError;

    public MultiCallResult() {}

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

    public List<List<Type>> getMultiResultList() {
        return multiResultList;
    }

    public void setMultiResultList(List<List<Type>> multiResultList) {
        this.multiResultList = multiResultList;
    }

    public CallError getCallError() {
        return callError;
    }

    public void setCallError(CallError callError) {
        this.callError = callError;
    }
}
