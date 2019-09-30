package com.hukaihan.redis.cachedemo.cachedemo.base.annotation;

import java.lang.annotation.*;

/**
 * 自定义缓存注解
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheSingle {
    String tableName();
    Class clazz();
}
