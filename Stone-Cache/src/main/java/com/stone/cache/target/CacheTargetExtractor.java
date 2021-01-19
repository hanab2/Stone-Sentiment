package com.stone.cache.target;


import com.stone.cache.annotation.CacheTarget;
import com.stone.cache.exception.CacheTargetException;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 目标缓存器
 *
 * @author hana_be@126.com
 * @date 2021-01-13
 */
public interface CacheTargetExtractor {

    /**
     * 提取目标
     *
     * @param target 目标
     * @param method 方法
     * @param params 参数个数
     * @return {@link Object}* @throws CacheTargetException 缓存的目标异常
     */
    default Object extractTarget(Object target, Method method, Object... params) throws CacheTargetException {
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < params.length; i++) {
            if (parameters[i].isAnnotationPresent(CacheTarget.class)){
                return params[i];
            }
        }
        throw new CacheTargetException("没有找到被@CacheTarget修饰的参数！~");
    }
}
