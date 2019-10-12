package com.hukaihan.redis.cachedemo.cachedemo.bussiness.controller;

import com.hukaihan.redis.cachedemo.cachedemo.base.annotation.CacheSingle;
import com.hukaihan.redis.cachedemo.cachedemo.base.baseUtil.PageUtils;
import com.hukaihan.redis.cachedemo.cachedemo.base.redisUtil.JedisAdapter;
import com.hukaihan.redis.cachedemo.cachedemo.base.vo.Message;
import com.hukaihan.redis.cachedemo.cachedemo.bussiness.cacheService.CacheService;
import com.hukaihan.redis.cachedemo.cachedemo.bussiness.model.Author;
import com.hukaihan.redis.cachedemo.cachedemo.bussiness.model.Comment;
import com.hukaihan.redis.cachedemo.cachedemo.bussiness.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/testCache/")
public class CacheController {

    @Autowired
    CacheService cacheServiceImpl;

    @Autowired
    JedisAdapter jedisAdapter;

    @RequestMapping("selectTopicById/{id}")
    public Message selectTopicById(@PathVariable("id")Integer id){
        if(cacheServiceImpl.selectTopicById(id)==null){
            return new Message().ok(200,"不存在数据");
        }
        return new Message().ok(200,"成功").addData("topic",cacheServiceImpl.selectTopicById(id));
    }
    @RequestMapping("selectCommentById/{id}")
    public Comment selectCommentById(@PathVariable("id")Integer id){
        return cacheServiceImpl.selectCommentById(id);
    }
    //用hash方式缓存查询
    @RequestMapping("selectCommentByHash/{id}")
    public Message selectCommentByHash(@PathVariable("id")Integer id) throws ParseException, IllegalAccessException {
        return new Message().ok(200,"成功").addData("comment",cacheServiceImpl.selectCommentByHash(id));
    }


}
