package com.stone.cache.annotation;

import java.lang.annotation.*;

/**
 * 缓存的目标对象
 *
 * @author hana_be@126.com
 * @date 2021-01-13
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheTarget {
}
