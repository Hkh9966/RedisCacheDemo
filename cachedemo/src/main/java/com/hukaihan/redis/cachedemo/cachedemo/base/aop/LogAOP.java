package com.hukaihan.redis.cachedemo.cachedemo.base.aop;

import com.hukaihan.redis.cachedemo.cachedemo.base.annotation.SysLog;
import com.hukaihan.redis.cachedemo.cachedemo.base.baseUtil.HttpContextUtils;
import com.hukaihan.redis.cachedemo.cachedemo.bussiness.cacheService.impl.CacheServiceImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Component
@Aspect
public class LogAOP {

    private static final Logger log = LoggerFactory.getLogger(LogAOP.class);

    @Pointcut("@annotation(com.hukaihan.redis.cachedemo.cachedemo.base.annotation.SysLog)")
    public void pointCut(){
    }

    @Before("pointCut()")
    public void sysLog(JoinPoint joinPoint){
        MethodSignature signature =(MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLog annotation = method.getAnnotation(SysLog.class);
        if(annotation!=null){
            HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
            log.info("请求路径：{}---->模块：{}--->操作细节：{}",request.getRequestURI(),annotation.type(),annotation.detail());
        }
    }

}
