package com.jeongen.cosmos.config;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CosmosChainConfig {

    public static final int DefaultDecimals = 6;

    private String chainId;

    private String tokenDemon;

    private String prefix;

    private int decimals;

    private BigDecimal fee;

    private long gasLimit;

    private boolean compressed;

    public CosmosChainConfig(String chainId, String tokenDemon, String prefix) {
        this.chainId = chainId;
        this.tokenDemon = tokenDemon;
        this.prefix = prefix;
        this.decimals = DefaultDecimals;
        this.compressed = true;
    }

    public CosmosChainConfig(String chainId, String tokenDemon, String prefix, int decimals) {
        this(chainId, tokenDemon, prefix);
        this.decimals = decimals;
        this.compressed = true;
    }

    public CosmosChainConfig(String chainId, String tokenDemon, String prefix, String fee, long gasLimit) {
        this(chainId, tokenDemon, prefix);
        this.decimals = DefaultDecimals;
        this.fee = new BigDecimal(fee);
        this.gasLimit = gasLimit;
        this.compressed = true;
    }


    public CosmosChainConfig(String chainId, String tokenDemon, String prefix, int decimals, String fee, long gasLimit) {
        this(chainId, tokenDemon, prefix);
        this.decimals = decimals;
        this.fee = new BigDecimal(fee);
        this.gasLimit = gasLimit;
        this.compressed = true;
    }

    public CosmosChainConfig(String chainId, String tokenDemon, String prefix, int decimals, String fee, long gasLimit, boolean compressed) {
        this(chainId, tokenDemon, prefix);
        this.decimals = decimals;
        this.fee = new BigDecimal(fee);
        this.gasLimit = gasLimit;
        this.compressed = compressed;
    }

    public static final CosmosChainConfig COSMOS = new CosmosChainConfig("cosmoshub-4", "uatom", "cosmos");
    public static final CosmosChainConfig KAVA = new CosmosChainConfig("kava_2222-10", "ukava", "kava");
    public static final CosmosChainConfig CRO = new CosmosChainConfig("crypto-org-chain-mainnet-1", "basecro", "cro", 8, "0.0005", 0);
    public static final CosmosChainConfig TERRA = new CosmosChainConfig("columbus-5", "uluna", "terra");
    public static final CosmosChainConfig INJ = new CosmosChainConfig("injective-1", "inj", "inj", 18, "0.0002", 120000, false);

    //kava 测试链配置， 水龙头地址：https://faucet.kava.io/
    public static final CosmosChainConfig kava_test = new CosmosChainConfig("kava_2221-16000", "ukava", "kava");
}
