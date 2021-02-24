package com.stone.sentiment.pump;


import com.stone.sentiment.api.ApiResponse;
import com.stone.sentiment.api.StringResponse;
import com.stone.sentiment.model.News;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Component
public class Pump {

    private static final Logger log = LoggerFactory.getLogger(Pump.class);

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @PostConstruct
    private void init() {
    }

    public void pump() {
        log.debug("start pump");
        Query query = new Query()
                .addCriteria(Criteria.where("status").is(1))
                .limit(100);
        List<News> newsList = mongoTemplate.find(query, News.class);
        elasticsearchRestTemplate.save(newsList);
        newsList.forEach(news -> {
            news.setStatus(2);
            mongoTemplate.save(news);
        });
        log.debug("end pump");
    }

}
