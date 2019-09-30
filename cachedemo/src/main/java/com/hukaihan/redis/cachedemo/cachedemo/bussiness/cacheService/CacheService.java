package com.hukaihan.redis.cachedemo.cachedemo.bussiness.cacheService;

import com.hukaihan.redis.cachedemo.cachedemo.bussiness.model.Author;
import com.hukaihan.redis.cachedemo.cachedemo.bussiness.model.Comment;
import com.hukaihan.redis.cachedemo.cachedemo.bussiness.model.Topic;

public interface CacheService {

    Topic selectTopicById(Integer id);
    Comment selectCommentById(Integer id);
    Author selectAuthorById(Integer id);
}
