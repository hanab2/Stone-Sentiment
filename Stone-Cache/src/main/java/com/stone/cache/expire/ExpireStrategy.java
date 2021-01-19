package com.stone.cache.expire;

/**
 * 过期策略
 *
 * @author hana_be@126.com
 * @date 2021-01-08
 */
public enum ExpireStrategy {
    /**
     * 不过期
     */
    NEVER,
    /**
     * 随机分钟过期
     */
    RANDOM_TIMEOUT,
    /**
     * 自定义时间过期
     */
    CUSTOM;
}
