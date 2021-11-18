package network.nerve.heterogeneous.model;

import java.util.List;

public class MultiCallResult {

    private long blockHeight;

    private List<List> multiResultList;

    public MultiCallResult() {}

    public MultiCallResult(long blockHeight, List<List> multiResultList) {
        this.blockHeight = blockHeight;
        this.multiResultList = multiResultList;
    }

    public long getBlockHeight() {
        return blockHeight;
    }

    public void setBlockHeight(long blockHeight) {
        this.blockHeight = blockHeight;
    }

    public List<List> getMultiResultList() {
        return multiResultList;
    }

    public void setMultiResultList(List<List> multiResultList) {
        this.multiResultList = multiResultList;
    }
}
