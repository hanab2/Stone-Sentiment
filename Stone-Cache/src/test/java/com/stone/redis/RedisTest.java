package com.stone.redis;

import com.alibaba.fastjson.JSON;
import com.stone.cache.example.ExampleClass;
import com.stone.cache.example.ExampleService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@SpringBootTest
public class RedisTest {

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @Test
    void testRedis() {
        redisTemplate.opsForHash().put("key2", "field1", "value1");
        redisTemplate.opsForHash().put("key2", "field2", "value2");
        System.out.println(redisTemplate.opsForHash().values("key2"));
        System.out.println("done");

//        TestClass testClass = new TestClass();
//        testClass.setBirth(LocalDateTime.now());
//        testClass.setId(2123141L);
//        testClass.setName("name");
//        redisTemplate.opsForList().leftPush(testClass.getId().toString(),testClass);
//        System.out.println(redisTemplate.opsForList().leftPop(testClass.getId().toString()));
    }

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Test
    void stringRedisTemplateTest() {

        ExampleClass exampleClass = new ExampleClass();
        exampleClass.setBirth(LocalDateTime.now());
        exampleClass.setId(2123141L);
        exampleClass.setName("name");

        stringRedisTemplate.opsForValue().set(exampleClass.getName(), JSON.toJSONString(exampleClass));
    }


    @Resource
    ExampleService exampleService;

    @Test
    void putTest() {
        ExampleClass exampleClass = new ExampleClass();
        exampleClass.setBirth(LocalDateTime.now());
        exampleClass.setId(211L);
        exampleClass.setName("name");

        boolean put = exampleService.put(exampleClass.getId(), exampleClass);
        System.out.println(put);
    }

    @Test
    void fetchTest(){
        Object fetch = exampleService.fetch(233L);
        System.out.println(fetch);
    }

    @Test
    void deleteTest(){
        boolean delete = exampleService.delete(233L);
        System.out.println(delete);
    }
}
