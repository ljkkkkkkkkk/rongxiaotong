package com.lk77.server.domain;

import lombok.Data;

@Data
public class ShoppingInfo {
    private Integer shoppingId;
    private Integer orderId;
    private String title;
    private String content;
    private String price;
    private String picture;
    private String ownName;
    private Integer count;
}
