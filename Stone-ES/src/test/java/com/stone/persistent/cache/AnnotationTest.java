package com.stone.persistent.cache;

import com.stone.persistent.cache.SerTest;
import com.stone.persistent.cache.annotation.CacheEntry;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class AnnotationTest {
    @Resource
    SerTest serTest;
    @Test
    public void annotationTest(){
        serTest.fun("annotationTest~~~~~");
    }

}
