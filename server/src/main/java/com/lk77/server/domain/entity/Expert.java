package com.lk77.server.domain.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class Expert {
    private String userName;
    @NotBlank(message = "真实姓名不能为空")
    private String realName;
    @NotBlank(message = "电话不能为空")
    private String phone;
    @NotBlank(message = "从事专业不能为空")
    private String profession;
    @NotBlank(message = "职位不能为空")
    private String position;
    @NotBlank(message = "所属单位不能为空")
    private String belong;
}