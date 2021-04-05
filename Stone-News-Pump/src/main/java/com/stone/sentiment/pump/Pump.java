package com.stone.sentiment.pump;


import com.stone.sentiment.model.News;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;


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
                .limit(20);
        List<News> newsList = mongoTemplate.find(query, News.class);
        if (newsList.size() > 0) {
            log.info("pumping!");
            newsList.forEach(news -> {
                NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
                long count = elasticsearchRestTemplate.count(
                        queryBuilder.withQuery(
                                QueryBuilders.termQuery("source", news.getSource())
                        ).build()
                        , News.class
                );
                if (count == 0) {
                    elasticsearchRestTemplate.save(news);
                    news.setStatus(2);
                    mongoTemplate.save(news);
                }else {
                    log.warn("URL重复了！~");
                }
            });
        } else {
            log.info("nothing to pump!~");
        }
        log.debug("end pump");
    }

}
