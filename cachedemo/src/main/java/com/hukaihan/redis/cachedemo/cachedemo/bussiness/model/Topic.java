package com.hukaihan.redis.cachedemo.cachedemo.bussiness.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Topic {
    private Integer topicId;

    private String topicName;

    private Integer topicNo;

    private Integer topicAuthorId;

    private Date createTime;

}
