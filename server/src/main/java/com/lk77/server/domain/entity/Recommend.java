package com.lk77.server.domain.entity;

import lombok.Data;

@Data
public class Recommend {
    private String userName;
    private String realName;
    private String phone;
    private String address;

    private String avatar;
    private Integer area;

    private String item;

    private String amount;
    private Integer num;
}
