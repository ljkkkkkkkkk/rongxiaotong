package com.lk77.server.domain.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Id;
import java.util.Date;

@Data
public class Knowledge {
    @Id
    private Integer knowledgeId;
    @NotBlank(message = "标题不能为空")
    private String title;
        @NotBlank(message = "内容不能为空")
    private String content;
    private String picPath;
    private String ownName;
    private Date createTime;
    private Date updateTime;
}
