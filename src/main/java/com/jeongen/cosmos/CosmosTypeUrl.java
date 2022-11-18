package com.jeongen.cosmos;


public enum CosmosTypeUrl {


    //质押
    DELEGATE("/cosmos.staking.v1beta1.MsgDelegate"),
    //取消质押
    UNDELEGATE("/cosmos.staking.v1beta1.MsgUndelegate"),
    ;

    private String type;

    CosmosTypeUrl(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
