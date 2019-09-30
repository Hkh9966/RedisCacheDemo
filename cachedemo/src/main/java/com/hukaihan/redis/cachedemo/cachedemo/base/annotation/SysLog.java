package com.hukaihan.redis.cachedemo.cachedemo.base.annotation;

import com.hukaihan.redis.cachedemo.cachedemo.base.constant.ModelType;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public  @interface SysLog {
    ModelType type();//哪个模块
    String detail();//具体哪个接口
}
