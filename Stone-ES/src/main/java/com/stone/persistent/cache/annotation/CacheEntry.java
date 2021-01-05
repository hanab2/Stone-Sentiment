package com.stone.persistent.cache.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CacheEntry {
    String serviceName();
    String tableName();
    Class clazz();
}
