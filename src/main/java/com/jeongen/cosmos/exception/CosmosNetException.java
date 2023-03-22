package com.jeongen.cosmos.exception;

public class CosmosNetException extends Exception{

    public CosmosNetException(String msg) {
        super(msg);
    }

    public CosmosNetException(Exception e) {
        super(e);
    }
}
