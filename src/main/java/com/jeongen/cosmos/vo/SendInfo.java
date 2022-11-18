package com.jeongen.cosmos.vo;

import com.jeongen.cosmos.crypro.CosmosCredentials;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SendInfo {
    //转账人账户信息
    CosmosCredentials credentials;
    //接收人地址
    String toAddress;
    //转账金额
    BigDecimal amount;
    //转账token
    String demon;
}
