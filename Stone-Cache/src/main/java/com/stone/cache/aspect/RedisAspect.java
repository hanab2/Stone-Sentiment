package com.stone.cache.aspect;

import com.stone.cache.agent.CacheAgent;
import com.stone.cache.expire.ExpireStrategyExtractor;
import com.stone.cache.key.KeyGenerator;
import com.stone.cache.pack.CachePack;
import com.stone.cache.pack.CachePackBuilder;
import com.stone.cache.serializer.JsonSerializer;
import com.stone.cache.target.CacheTargetExtractor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

@Aspect
@Component
public class RedisAspect {

    private static final Logger logger = LoggerFactory.getLogger(RedisAspect.class);


    @Resource
    KeyGenerator keyGenerator;

    @Resource
    CacheTargetExtractor cacheTargetExtractor;

    @Resource
    JsonSerializer jsonSerializer;

    @Resource
    ExpireStrategyExtractor expireStrategyExtractor;

    @Resource
    CacheAgent cacheAgent;

//    @Pointcut(value = "@annotation(com.stone.persistent.cache.annotation.CacheProperties)")
//    public void cacheAspect() {
//
//    }
//
//    @Around(value = "cacheAspect() && @annotation(cacheProperties)")
//    public Object doBefore(ProceedingJoinPoint proceedingJoinPoint, CacheProperties cacheProperties) {
//
//        logger.warn("before");
////        System.out.println("args:\t" + Arrays.toString(proceedingJoinPoint.getArgs()));
////        System.out.println(cacheEntry.serviceName() + "====" + cacheEntry.tableName());
////
////
////        System.out.println("=============================================");
////        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
////        Method method = signature.getMethod();
////        CacheProperties annotation = method.getAnnotation(CacheProperties.class);
////        System.out.println(annotation.tableName());
////
////
////        System.out.println("======================++========================");
////        System.out.println(new StoneCacheKeyGenerator().generateKey(proceedingJoinPoint.getTarget(), method, proceedingJoinPoint.getArgs()));
//
//
//        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
//        Method method = signature.getMethod();
//        return null;
//    }

    @Pointcut(value = "@annotation(com.stone.cache.annotation.CacheFetch)")
    public void fetchAspect() {
    }

    @Around(value = "fetchAspect()")
    public Object aroundFetch(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        logger.warn("before fetch");
        Object target = getTarget(proceedingJoinPoint);
        Method method = getMethod(proceedingJoinPoint);
        Object[] args = getArgs(proceedingJoinPoint);
        String key = keyGenerator.generateKey(target, method, args);
        String fetch = cacheAgent.fetch(key);
        if (fetch != null) {
            Class<?> returnType = method.getReturnType();
            return jsonSerializer.jsonStringToObject(fetch, returnType);
        }
        Object result = proceedingJoinPoint.proceed(args);
        if (result != null) {
            CachePack cachePack = CachePackBuilder.builder()
                    .key(key)
                    .jsonStingValue(
                            jsonSerializer.objectToJsonString(result)
                    )
                    .expireDuration(
                            expireStrategyExtractor.extractTimeout(target, method, args)
                    )
                    .timeUnit(
                            expireStrategyExtractor.extractTimeUnit(target, method, args)
                    ).build();
            cacheAgent.store(cachePack);

        }
        return result;
//        Object fetch = cacheAgent.fetch(
//                proceedingJoinPoint.getTarget(),
//                method,
//                proceedingJoinPoint.getArgs()
//        );
//        if (fetch != null){
//            logger.info("{}",fetch);
//            return fetch;
//        }
//        cacheAgent.store(
//                proceedingJoinPoint.getTarget(),
//                method,
//                proceedingJoinPoint.getArgs()
//        );
    }

    @Pointcut(value = "@annotation(com.stone.cache.annotation.CachePut)")
    public void putAspect() {
    }

    @Before(value = "putAspect()")
    public void beforePut(JoinPoint joinPoint) {
        logger.info("before put");
        Object target = getTarget(joinPoint);
        Method method = getMethod(joinPoint);
        Object[] args = getArgs(joinPoint);
        String key = keyGenerator.generateKey(target, method, args);
        boolean existKey = cacheAgent.existKey(key);
        if (existKey) {
            logger.info("existKey");
            Object value = cacheTargetExtractor.extractTarget(target, method, args);
            CachePack cachePack = CachePackBuilder.builder()
                    .key(key)
                    .jsonStingValue(
                            jsonSerializer.objectToJsonString(value)
                    )
                    .expireDuration(
                            expireStrategyExtractor.extractTimeout(target, method, args)
                    )
                    .timeUnit(
                            expireStrategyExtractor.extractTimeUnit(target, method, args)
                    ).build();
            cacheAgent.store(cachePack);
        }
    }

    @Pointcut(value = "@annotation(com.stone.cache.annotation.CacheDelete)")
    public void deleteAspect() {
    }

    @Before(value = "deleteAspect()")
    public void beforeDelete(JoinPoint joinPoint) {
        Object target = getTarget(joinPoint);
        Method method = getMethod(joinPoint);
        Object[] args = getArgs(joinPoint);
        String key = keyGenerator.generateKey(target, method, args);
        boolean existKey = cacheAgent.existKey(key);
        if (existKey) {
            cacheAgent.delete(key);
        }
    }

    private Object getTarget(JoinPoint joinPoint) {
        return joinPoint.getTarget();
    }

    private Method getMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }

    private Object[] getArgs(JoinPoint joinPoint) {
        return joinPoint.getArgs();
    }
}
