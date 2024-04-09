package com.lk77.server.domain.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Shoppingcart {

    private Integer shoppingId;

    private Integer orderId;

    private Integer count;

    private String ownName;

    private Date createTime;

    private Date updateTime;

}