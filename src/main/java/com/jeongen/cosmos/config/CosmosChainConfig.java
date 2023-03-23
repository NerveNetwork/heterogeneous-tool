package com.jeongen.cosmos.config;

import lombok.Data;

@Data
public class CosmosChainConfig {

    private String chainId;

    private String url;

    private String tokenDemon;

    private String prefix;


    public CosmosChainConfig(String chainId, String url, String tokenDemon, String prefix) {
        this.chainId = chainId;
        this.url = url;
        this.tokenDemon = tokenDemon;
        this.prefix = prefix;
    }

    public static final CosmosChainConfig cosmos = new CosmosChainConfig("cosmoshub-4", "https://cosmos1.maiziqianbao.net", "uatom", "cosmos");
    public static final CosmosChainConfig kava = new CosmosChainConfig("kava_2222-10", "https://api.data.kava.io/", "ukava", "kava");
    public static final CosmosChainConfig cro = new CosmosChainConfig("crypto-org-chain-mainnet-1", "https://rest.mainnet.crypto.org", "basecro", "cro");
    public static final CosmosChainConfig terra_c = new CosmosChainConfig("columbus-5", "https://lcd.terrarebels.net", "uluna", "terra");
    //kava 测试链配置， 水龙头地址：https://faucet.kava.io/
    public static final CosmosChainConfig kava_test = new CosmosChainConfig("kava_2221-16000", "https://api.testnet.kava.io", "ukava", "kava");
}
