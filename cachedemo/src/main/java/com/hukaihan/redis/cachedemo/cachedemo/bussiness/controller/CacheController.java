package com.hukaihan.redis.cachedemo.cachedemo.bussiness.controller;

import com.hukaihan.redis.cachedemo.cachedemo.base.redisUtil.JedisAdapter;
import com.hukaihan.redis.cachedemo.cachedemo.bussiness.cacheService.CacheService;
import com.hukaihan.redis.cachedemo.cachedemo.bussiness.model.Author;
import com.hukaihan.redis.cachedemo.cachedemo.bussiness.model.Comment;
import com.hukaihan.redis.cachedemo.cachedemo.bussiness.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testCache/")
public class CacheController {

    @Autowired
    CacheService cacheServiceImpl;

    @Autowired
    JedisAdapter jedisAdapter;

    @RequestMapping("selectTopicById/{id}")
    public Topic selectTopicById(@PathVariable("id")Integer id){
        return cacheServiceImpl.selectTopicById(id);
    }
    @RequestMapping("selectCommentById/{id}")
    public Comment selectCommentById(@PathVariable("id")Integer id){
        return cacheServiceImpl.selectCommentById(id);
    }
    @RequestMapping("selectAuthorById/{id}")
    public Author selectAuthorById(@PathVariable("id")Integer id){
        return cacheServiceImpl.selectAuthorById(id);
    }

    @RequestMapping("redis/set/{key}/{value}")
    public void setKey(@PathVariable("key")String key,@PathVariable("value")String value){
        jedisAdapter.set(key,value);
    }
    @RequestMapping("redis/get/{key}")
    public String get(@PathVariable("key")String key){
        return jedisAdapter.get(key);
    }
}
