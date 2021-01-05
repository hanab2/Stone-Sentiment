package com.stone.persistent.cache;

import com.stone.persistent.cache.annotation.CacheEntry;
import org.springframework.stereotype.Service;

@Service
public class SerTest {
    @CacheEntry(serviceName = "service", tableName = "table" ,clazz = String.class)
    public void fun(String parm) {
        System.out.println(parm);
    }
}
