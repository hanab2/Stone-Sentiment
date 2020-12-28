package com.stone.nlp.hanlp.annotation;

import java.lang.annotation.*;

@Target(value = {ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface HanApi {
    String token();
    String url();
}
