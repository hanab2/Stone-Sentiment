package com.stone.persistent.cache.aspect;

import com.stone.persistent.cache.annotation.CacheEntry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Aspect
@Component
public class RedisAspect {

    @Pointcut(value = "@annotation(com.stone.persistent.cache.annotation.CacheEntry)")
    public void cacheAspect() {

    }

    @Around(value = "cacheAspect() && @annotation(cacheEntry)")
    public Object doBefore(ProceedingJoinPoint proceedingJoinPoint, CacheEntry cacheEntry) {
        Class clazz = cacheEntry.clazz();
        Object cast = clazz.cast(proceedingJoinPoint.getArgs()[0]);
        System.out.println("transferArgs\t" + cast);
        System.out.println("args:\t" + proceedingJoinPoint.getArgs().toString());
        System.out.println(cacheEntry.serviceName() + "====" + cacheEntry.tableName());


        System.out.println("=============================================");
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        CacheEntry annotation = method.getAnnotation(CacheEntry.class);
        System.out.println(annotation.tableName());


        return null;
    }
}
