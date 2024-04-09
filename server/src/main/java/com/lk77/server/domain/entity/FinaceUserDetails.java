package com.lk77.server.domain.entity;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class FinaceUserDetails {

    private BigDecimal rate;

    private String phone;

    private String introduce;
    private String bankName;
}
