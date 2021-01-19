package com.stone.cache.annotation;

import java.lang.annotation.*;

/**
 * 缓存key后缀
 *
 * @author hana_be@126.com
 * @date 2021-01-06
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheKey {
}
