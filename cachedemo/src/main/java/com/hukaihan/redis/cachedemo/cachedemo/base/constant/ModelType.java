package com.hukaihan.redis.cachedemo.cachedemo.base.constant;

import lombok.AllArgsConstructor;

/**
 * @author hukaihan
 */
@AllArgsConstructor
public enum ModelType {

    TOPIC_MODEL("文章模块"),

    COMMENT_MODEL("评论模块"),

    AUTHOR_MODEL("作者模块");

    public final String name;

}
