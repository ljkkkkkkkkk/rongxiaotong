package com.lk77.server.domain.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

@Data
public class Discuss {

    private Integer discussId;

    private Integer knowledgeId;

    private String ownName;

    @NotBlank(message = "内容不能为空")
    private String content;

    private Date createTime;
}