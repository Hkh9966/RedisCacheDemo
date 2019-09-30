package com.hukaihan.redis.cachedemo.cachedemo.bussiness.cacheService.impl;


import com.hukaihan.redis.cachedemo.cachedemo.base.annotation.CacheSingle;
import com.hukaihan.redis.cachedemo.cachedemo.base.constant.ModelType;
import com.hukaihan.redis.cachedemo.cachedemo.base.annotation.SysLog;
import com.hukaihan.redis.cachedemo.cachedemo.bussiness.cacheDao.CacheDao;
import com.hukaihan.redis.cachedemo.cachedemo.bussiness.cacheService.CacheService;
import com.hukaihan.redis.cachedemo.cachedemo.bussiness.model.Author;
import com.hukaihan.redis.cachedemo.cachedemo.bussiness.model.Comment;
import com.hukaihan.redis.cachedemo.cachedemo.bussiness.model.Topic;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    CacheDao cacheDao;

    @SysLog(type = ModelType.TOPIC_MODEL,detail = "根据文章id查询")
    @Cacheable(value = "topic",key = "#id")
    @CacheSingle(tableName = "topic",clazz = Topic.class)
    @Override
    public Topic selectTopicById(Integer id) {
        return cacheDao.selectTopicById(id);
    }
    @CacheSingle(tableName = "comment",clazz = Comment.class)
    @SysLog(type = ModelType.COMMENT_MODEL,detail = "根据id查询评论")
    @Override
    public Comment selectCommentById(Integer id) {
        return cacheDao.selectCommentById(id);
    }
    @SysLog(type = ModelType.AUTHOR_MODEL,detail = "根据id查询作者")
    @CacheSingle(tableName = "author",clazz = Author.class)
    @Override
    public Author selectAuthorById(Integer id) {
        return cacheDao.selectAuthorById(id);
    }
}
