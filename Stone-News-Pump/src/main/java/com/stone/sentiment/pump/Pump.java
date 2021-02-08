package com.stone.sentiment.pump;


import com.stone.sentiment.api.ApiResponse;
import com.stone.sentiment.api.StringResponse;
import com.stone.sentiment.model.News;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
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
    RestTemplate restTemplate;



    @PostConstruct
    private void init() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(1000);
        requestFactory.setReadTimeout(1000);
        restTemplate.setRequestFactory(requestFactory);
    }

    public void pump() {
        log.debug("start pump");
        Query query = new Query();
        query.limit(10);
        List<News> newsList = mongoTemplate.find(query, News.class);
        newsList.parallelStream()
                .forEach(
                        news -> {
                            CompletableFuture<Void> tagTask = CompletableFuture.supplyAsync(() -> {
                                log.debug("异步获取文本分类");
                                /**
                                 * List<User> list = restTemplate.getForObject(
                                 * 						 "http://localhost:8081/user/userList/{page}/{pageSize}",
                                 * 						 List.class, "1", "4");
                                 */
                                StringResponse response = restTemplate.getForObject("http://localhost:5000/{text}", StringResponse.class, news.getTitle());
                                assert response != null;
                                return response.getData();
                            }).thenAcceptAsync(news::setTag);

                            CompletableFuture<Void> sentimentTask = CompletableFuture.supplyAsync(() -> {
                                log.debug("异步获取情感分析");
                                return "";
                            }).thenAcceptAsync(news::setTag);

                            CompletableFuture<Void> locationTask = CompletableFuture.supplyAsync(() -> {
                                log.debug("异步获取地名");
                                return "";
                            }).thenAcceptAsync(news::setTag);
                        }
                );
    }

}
