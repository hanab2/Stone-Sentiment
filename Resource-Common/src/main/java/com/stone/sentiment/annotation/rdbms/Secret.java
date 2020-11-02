package com.stone.sentiment.annotation.rdbms;

import java.lang.annotation.*;

/**
 * @author 40961
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Secret {

    /**
     * 工具类生成的wrapper将不包含有此注解且值为true的成员变量
     */
    boolean isHidden() default true;

}
