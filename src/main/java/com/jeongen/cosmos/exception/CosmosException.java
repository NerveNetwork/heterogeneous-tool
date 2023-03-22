package com.jeongen.cosmos.exception;

public class CosmosException extends Exception {

    public CosmosException(String msg) {
        super(msg);
    }

    public CosmosException(Exception e) {
        super(e);
    }
}
