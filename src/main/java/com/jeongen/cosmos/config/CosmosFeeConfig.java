package com.jeongen.cosmos.config;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CosmosFeeConfig {

    private BigDecimal low;

    private BigDecimal average;

    private BigDecimal high;

    public CosmosFeeConfig(String low, String average, String high) {
        this.low = new BigDecimal(low);
        this.average = new BigDecimal(average);
        this.high = new BigDecimal(high);
    }

    public static final CosmosFeeConfig CRO_FEE = new CosmosFeeConfig("0.025", "0.03", "0.04");
}
