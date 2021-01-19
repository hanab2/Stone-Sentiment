package com.stone.cache.annotation;

import com.stone.cache.expire.ExpireStrategy;
import com.stone.cache.key.KeyGenerateStrategy;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 缓存属性
 *
 *
 * @author hana_be@126.com
 * @date 2021-01-08
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheProperties {
    String serviceName() default "";
    String tableName() default "";
    String[] customKeyPrefixParameters() default {};
    KeyGenerateStrategy keyGenerateStrategy() default KeyGenerateStrategy.DEFAULT;
    char parametersSeparator() default ':';
    ExpireStrategy expireStrategy() default ExpireStrategy.NEVER;
    long expireDuration() default 10L;
    TimeUnit expireTimeUnit() default TimeUnit.MINUTES;

}
