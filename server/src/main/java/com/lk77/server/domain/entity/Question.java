package com.lk77.server.domain.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class Question {

    private Integer id;

    private String expertName;

    private String questioner;

    @NotBlank(message = "电话不能为空")
    private String phone;

    @NotBlank(message = "农作物名称不能为空")
    private String plantName;

    @NotBlank(message = "标题不能为空")
    private String title;

    private String question;

    private String answer;

    private Integer status;
}