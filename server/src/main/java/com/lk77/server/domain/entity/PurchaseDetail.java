package com.lk77.server.domain.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PurchaseDetail {

    private Integer detailId;

    private Integer purchaseId;

    private Integer orderId;

    private BigDecimal uninPrice;

    private BigDecimal sumPrice;

    private Integer count;
}