package com.stone.cache.key;

import com.stone.cache.annotation.CacheProperties;

/**
 * KEY生成策略
 *
 * @author hana_be@126.com
 * @date 2021-01-09
 */
public enum KeyGenerateStrategy {
    /**
     * 默认方式
     * 服务名:表名:id
     */
    DEFAULT,
    /**
     * 自定义。
     * 按照{@link CacheProperties}的 customKeyPrefixParameters 依次生成key
     */
    CUSTOM;

}
