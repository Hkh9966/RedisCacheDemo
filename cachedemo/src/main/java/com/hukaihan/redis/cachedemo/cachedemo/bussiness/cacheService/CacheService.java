package com.hukaihan.redis.cachedemo.cachedemo.bussiness.cacheService;

import com.hukaihan.redis.cachedemo.cachedemo.base.baseUtil.PageUtils;
import com.hukaihan.redis.cachedemo.cachedemo.bussiness.model.Author;
import com.hukaihan.redis.cachedemo.cachedemo.bussiness.model.Comment;
import com.hukaihan.redis.cachedemo.cachedemo.bussiness.model.Topic;

import java.text.ParseException;
import java.util.List;

public interface CacheService {

    Topic selectTopicById(Integer id);
    Comment selectCommentById(Integer id);
    Comment selectCommentByHash(Integer id) throws ParseException, IllegalAccessException;

}
