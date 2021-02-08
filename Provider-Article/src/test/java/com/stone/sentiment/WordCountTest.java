package com.stone.sentiment;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@Slf4j
public class WordCountTest {

    @Resource
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Test
    void wordCountTest(){
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("words")
                .field("body").size(15);
        Query query = new NativeSearchQueryBuilder()
                .addAggregation(aggregation)
                .build();
        SearchHits<RawNews> searchHits = elasticsearchRestTemplate.search(query,
                RawNews.class);
        Aggregations aggregations = searchHits.getAggregations();
        Terms byCompanyAggregation = aggregations.get("words");
        List<? extends Terms.Bucket> elasticBucket = byCompanyAggregation.getBuckets();
        elasticBucket.forEach(el -> {
            log.info("key:" + el.getKeyAsString());
            log.info("doc_count:" + el.getDocCount());

        });
    }
}
