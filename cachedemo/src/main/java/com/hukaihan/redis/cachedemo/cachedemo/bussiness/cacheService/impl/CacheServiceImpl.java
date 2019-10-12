package com.hukaihan.redis.cachedemo.cachedemo.bussiness.cacheService.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hukaihan.redis.cachedemo.cachedemo.base.annotation.CacheSingle;
import com.hukaihan.redis.cachedemo.cachedemo.base.baseUtil.PageUtils;
import com.hukaihan.redis.cachedemo.cachedemo.base.constant.ModelType;
import com.hukaihan.redis.cachedemo.cachedemo.base.annotation.SysLog;
import com.hukaihan.redis.cachedemo.cachedemo.base.redisUtil.JedisAdapter;
import com.hukaihan.redis.cachedemo.cachedemo.bussiness.cacheDao.CacheDao;
import com.hukaihan.redis.cachedemo.cachedemo.bussiness.cacheService.CacheService;
import com.hukaihan.redis.cachedemo.cachedemo.bussiness.model.Author;
import com.hukaihan.redis.cachedemo.cachedemo.bussiness.model.Comment;
import com.hukaihan.redis.cachedemo.cachedemo.bussiness.model.Topic;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    JedisAdapter jedisAdapter;

    private static final String SPLITER = ":";

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
    @SysLog(type = ModelType.COMMENT_MODEL,detail = "根据id查询评论")
    @Override
    public Comment selectCommentByHash(Integer id) throws ParseException, IllegalAccessException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<Object, Object> map = jedisAdapter.hgetAll("comment" + SPLITER + id);
        Comment comment ;
        if(map!=null&&map.size()>0){
            comment = new Comment();
            comment.setCommentId(Integer.parseInt((String)map.get("commentId")));
            comment.setContent((String)map.get("content"));
            comment.setTopicId(Integer.parseInt((String)map.get("topicId")));
            comment.setCreateTime(simpleDateFormat.parse((String)map.get("createTime")));
        }else{
            comment = cacheDao.selectCommentById(id);
            if(comment!=null){
                Map<String ,String> hash = new HashMap<>();
                hash.put("commentId",comment.getCommentId().toString());
                hash.put("content",comment.getContent());
                hash.put("topicId",comment.getTopicId().toString());
                hash.put("createTime",simpleDateFormat.format(comment.getCreateTime()));
                jedisAdapter.hmset("comment" + SPLITER + id,hash );
            }else{
                Map map1 = new HashMap();
                map1.put("null","null");
                jedisAdapter.hmset("comment" + SPLITER + id,map1,60*1000);
            }
        }
        return comment;
    }

    /**
     * 将Object对象里面的属性和值转化成Map对象
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, String> objectToMap(Object obj) throws IllegalAccessException {
        Map<String, String> map = new HashMap<>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            String value = (String)field.get(obj);
            map.put(fieldName, value);
        }
        return map;
    }
}
