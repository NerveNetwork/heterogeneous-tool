package network.nerve.heterogeneous.model;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;

/**
 * 批量调用查询接口类
 */
public class MultiCallModel {

    /**
     * 调用查询接口的合约地址（token地址）
     */
    private Address contractAddress;

    private Function callFunction;


}
