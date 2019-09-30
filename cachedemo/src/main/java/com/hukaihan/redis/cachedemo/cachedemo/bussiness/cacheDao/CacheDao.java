package com.hukaihan.redis.cachedemo.cachedemo.bussiness.cacheDao;

import com.hukaihan.redis.cachedemo.cachedemo.bussiness.model.Author;
import com.hukaihan.redis.cachedemo.cachedemo.bussiness.model.Comment;
import com.hukaihan.redis.cachedemo.cachedemo.bussiness.model.Topic;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

@Repository
public interface CacheDao {

    @Select("select * from topic where topic_id = #{id}")
    Topic selectTopicById(@Param("id") Integer id);

    @Select("select * from comment where comment_id = #{value}")
    Comment selectCommentById(Integer id);

    @Select("select * from author where author_id = #{value}")
    Author selectAuthorById(Integer id);
}
