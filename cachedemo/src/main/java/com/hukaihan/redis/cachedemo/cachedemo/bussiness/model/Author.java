package com.hukaihan.redis.cachedemo.cachedemo.bussiness.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Author {
    private Integer authorId;

    private String authorName;

    private Integer sex;

    private Date authorBirth;

    private Date createTime;

    private  Date updateTime;
}
