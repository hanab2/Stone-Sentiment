package com.stone.sentiment;

import com.stone.sentiment.model.News;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class MongoTest {

    @Resource
    MongoTemplate mongoTemplate;

    @Test
    void testFindAll(){
        List<News> newsList = mongoTemplate.findAll(News.class);
        newsList.forEach(System.out::println);
    }

    @Test
    void testFindById(){
        News news = mongoTemplate.findById(1357985453684293632L, News.class);
        System.out.println(news);
        System.out.println(news.getTime());
        System.out.println(System.currentTimeMillis());
    }

    @Test
    void testLimit(){
        Query query = new Query();
        query.limit(10);
        List<News> newsList = mongoTemplate.find(query, News.class);
        newsList.forEach(System.out::println);
    }
}
