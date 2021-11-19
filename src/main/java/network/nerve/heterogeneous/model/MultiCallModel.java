package network.nerve.heterogeneous.model;

import org.web3j.abi.datatypes.Function;

/**
 * 批量调用查询接口类
 */
public class MultiCallModel {

    /**
     * 调用查询接口的合约地址（token地址）
     */
    private String contractAddress;

    private Function callFunction;

    public MultiCallModel() {}

    public MultiCallModel(String contractAddress, Function callFunction) {
        this.contractAddress = contractAddress;
        this.callFunction = callFunction;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public Function getCallFunction() {
        return callFunction;
    }

    public void setCallFunction(Function callFunction) {
        this.callFunction = callFunction;
    }
}
