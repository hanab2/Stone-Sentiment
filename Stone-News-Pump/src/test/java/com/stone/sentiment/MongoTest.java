package com.stone.sentiment;

import com.stone.sentiment.model.News;
import com.stone.sentiment.model.Word;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class MongoTest {

    @Resource
    MongoTemplate mongoTemplate;

    @Test
    void testFindAll() {
        List<News> newsList = mongoTemplate.findAll(News.class);
        newsList.forEach(System.out::println);
    }

    @Test
    void testFindById() {
        News news = mongoTemplate.findById(1357985453684293632L, News.class);
        System.out.println(news);
        assert news != null;
        System.out.println(news.getTime());
        System.out.println(System.currentTimeMillis());
    }

    @Test
    void testLimit() {
        Query query = new Query();
        query.limit(10);
        List<News> newsList = mongoTemplate.find(query, News.class);
        newsList.forEach(System.out::println);
    }

    @Test
    void testAggregation() {
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(23);
        System.out.println(localDateTime);
        TypedAggregation<Word> agg = Aggregation.newAggregation(Word.class,
                Aggregation.match(Criteria.where("time").gte(localDateTime)),
                Aggregation.group("word").count().as("count"),
                Aggregation.sort(Sort.by(Sort.Direction.DESC, "count")),
                Aggregation.limit(10)
        );
        AggregationResults<Document> result = mongoTemplate.aggregate(agg, Document.class);
        result.getMappedResults().forEach(System.out::println);
    }
}
