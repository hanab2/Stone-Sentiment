package com.stone.cache.key;


import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;

/**
 * Cache Key生成器
 * 使用时，请在configuration中注入，不注入则使用{@link StoneCacheKeyGenerator}
 *
 * @author hana_be@126.com
 * @date 2021-01-05
 */
public interface KeyGenerator {

    /**
     * 生成Cache Key
     *
     * @param target 调用者
     * @param method 调用方法
     * @param params 调用方法参数
     * @return {@link String}
     */
    default String generateKey(Object target, Method method, Object... params) {
        return String.format("%s:%s:%s", target.getClass().getName(), method.getName(),
                CollectionUtils.arrayToList(params));
    }
}
