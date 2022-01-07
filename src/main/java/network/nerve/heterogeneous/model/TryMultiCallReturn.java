package network.nerve.heterogeneous.model;


import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.DynamicStruct;

public class TryMultiCallReturn extends DynamicStruct {

    public TryMultiCallReturn(Bool success, DynamicBytes data) {
        super(success, data);
    }
}
