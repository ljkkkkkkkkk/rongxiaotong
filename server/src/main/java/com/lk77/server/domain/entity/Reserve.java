package com.lk77.server.domain.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class Reserve {

    private Integer id;

    private String expertName;

    private String questioner;

    @NotBlank(message = "面积不能为空")
    private String area;

    @NotBlank(message = "地址不能为空")
    private String address;

    @NotBlank(message = "农作物名称不能为空")
    private String plantName;

    @NotBlank(message = "土壤条件不能为空")
    private String soilCondition;

    @NotBlank(message = "农作物条件不能为空")
    private String plantCondition;

    @NotBlank(message = "农作物详细信息不能为空")
    private String plantDetail;

    @NotBlank(message = "电话不能为空")
    private String phone;

    private String message;

    private String answer;

    private Integer status;
}