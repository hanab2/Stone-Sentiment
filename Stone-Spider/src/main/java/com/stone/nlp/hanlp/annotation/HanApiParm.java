package com.stone.nlp.hanlp.annotation;

import java.lang.annotation.*;

@Target(value = {ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface HanApiParm {
    String parmName();
    String parmValue();
}
