package com.stone.cache.example;

import com.stone.cache.annotation.*;
import com.stone.cache.expire.ExpireStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@CacheProperties(serviceName = "service", tableName = "table", expireStrategy = ExpireStrategy.RANDOM_TIMEOUT)
@Service
public class ExampleService {

    private static final Logger log = LoggerFactory.getLogger(ExampleService.class);

    public void fun(String parm) {
        System.out.println(parm);
    }

    @CacheFetch
    public Object fetch(@CacheKey Long id) {
        ExampleClass exampleClass = new ExampleClass();
        exampleClass.setBirth(LocalDateTime.now());
        exampleClass.setId(id);
        exampleClass.setName("name");
        log.info("{},{}", id, exampleClass);
        return exampleClass;
    }

    @CachePut
    public boolean put(@CacheKey Object id, @CacheTarget Object object) {
        log.info("key:  {}  value:  {}  has been put", id,object);
        return true;
    }

    @CacheDelete
    public boolean delete(@CacheKey Long id) {
        log.info("key:  {}  has been deleted",id);
        return true;
    }
}
