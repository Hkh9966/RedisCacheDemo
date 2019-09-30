package com.hukaihan.redis.cachedemo.cachedemo.bussiness.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Comment {
    private Integer commentId;

    private String content;

    private Integer topicId;

    private Date createTime;

    private Date updateTime;

}
