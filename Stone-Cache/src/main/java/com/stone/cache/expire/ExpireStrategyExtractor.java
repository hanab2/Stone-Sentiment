package com.stone.cache.expire;


import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 过期策略提取器
 *
 * @author hana_be@126.com
 * @date 2021-01-12
 */
public interface ExpireStrategyExtractor {

    /**
     * 提取超时
     *
     * @param target 目标
     * @param method 方法
     * @param params 参数个数
     * @return {@link Long}
     */
    Long extractTimeout(Object target, Method method, Object... params);

    /**
     * 提取时间单位
     *
     * @param target 目标
     * @param method 方法
     * @param params 参数个数
     * @return {@link TimeUnit}
     */
    TimeUnit extractTimeUnit(Object target, Method method, Object... params);
}
