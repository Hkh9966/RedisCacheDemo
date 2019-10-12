package com.hukaihan.redis.cachedemo.cachedemo.base.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.hukaihan.redis.cachedemo.cachedemo.base.annotation.CacheSingle;
import com.hukaihan.redis.cachedemo.cachedemo.base.annotation.SysLog;
import com.hukaihan.redis.cachedemo.cachedemo.base.baseUtil.HttpContextUtils;
import com.hukaihan.redis.cachedemo.cachedemo.base.redisUtil.JedisAdapter;
import com.hukaihan.redis.cachedemo.cachedemo.base.vo.Message;
import com.hukaihan.redis.cachedemo.cachedemo.bussiness.model.Topic;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
@Component
@Aspect
public class CacheAOP {

    @Autowired
    JedisAdapter jedisAdapter;

    private static final String SPLITER = ":";

    @Pointcut("@annotation(com.hukaihan.redis.cachedemo.cachedemo.base.annotation.CacheSingle)")
    public void pointCut(){
    }

    @Around("pointCut()")
    public Object redisCache(ProceedingJoinPoint joinPoint) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        MethodSignature signature =(MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String keyName = null;
        //如果需要缓存
        if(method.isAnnotationPresent(CacheSingle.class)){
            CacheSingle annotation= method.getAnnotation(CacheSingle.class);
            Object[] args = joinPoint.getArgs();
            if ((args != null && args.length == 1 )&&( args[0].getClass() == Integer.class
            || args[0].getClass() == String.class)) {
                keyName = annotation.tableName() + SPLITER + args[0];
                String json = jedisAdapter.get(keyName);
                if(jedisAdapter.isExsist(keyName)){
                    if(json.equals("null")){
                        return null;
                    }
                    return  JSON.parseObject(json, annotation.clazz());
                }else{
                    try {
                        Object proceed = joinPoint.proceed();
                        //防止缓存击穿
                        if(proceed!=null){
                            jedisAdapter.set(annotation.tableName()+SPLITER+args[0],JSON.toJSONString(proceed));
                        }else{
                            jedisAdapter.set(annotation.tableName()+SPLITER+args[0],"null",1000*60);
                            proceed = null;
                        }
                        return proceed;
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            }
        }
        return null;
    }
}
