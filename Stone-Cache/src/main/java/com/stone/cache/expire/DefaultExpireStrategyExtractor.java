package com.stone.cache.expire;

import com.stone.cache.annotation.CacheProperties;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 默认过期策略提取器
 * 提取{@link CacheProperties}注解中{@link ExpireStrategy}和{@link TimeUnit}的值生成过期时间；
 * 如果是随机过期，时间控制在10-2880分钟内过期；
 * 如果是自定义，则按照自定义参数过期；
 *
 * @author hana_be@126.com
 * @date 2021-01-13
 */
@Component
//@ConditionalOnMissingBean(ExpireStrategyExtractor.class)
public class DefaultExpireStrategyExtractor implements ExpireStrategyExtractor {

    private final Random random = new Random();

    @Override
    public Long extractTimeout(Object target, Method method, Object... params) {
        CacheProperties cacheProperties = target.getClass().getAnnotation(CacheProperties.class);
        switch (cacheProperties.expireStrategy()) {
            case RANDOM_TIMEOUT:
                long timeout = random.nextLong() % 2870;
                return 10L + timeout;
            case CUSTOM:
                return cacheProperties.expireDuration();
            default:
                return -1L;
        }
    }

    @Override
    public TimeUnit extractTimeUnit(Object target, Method method, Object... params) {
        CacheProperties cacheProperties = target.getClass().getAnnotation(CacheProperties.class);
        return cacheProperties.expireTimeUnit();
    }

}
