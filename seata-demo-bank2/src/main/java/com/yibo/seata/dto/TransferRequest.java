package com.yibo.seata.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {
    private BigDecimal money;
    private Long userId;
}
