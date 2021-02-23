package com.stone.sentiment.mapper;

import com.stone.sentiment.model.News;
import com.stone.sentiment.model.Word;
import com.stone.sentiment.model.view.WordCount;
import org.bson.Document;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Repository
public class NewsMapper {
    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Resource
    private MongoTemplate mongoTemplate;

    public List<WordCount> wordCount(LocalDateTime timeFloor, int size) {
        TypedAggregation<Word> aggregation = Aggregation.newAggregation(Word.class,
                Aggregation.match(Criteria.where("time").gte(timeFloor)),
                Aggregation.group("word").count().as("count"),
                Aggregation.sort(Sort.by(Sort.Direction.DESC, "count")),
                Aggregation.limit(size)
        );
        AggregationResults<Document> result = mongoTemplate.aggregate(aggregation, Document.class);
        List<Document> documentList = result.getMappedResults();
        LinkedList<WordCount> wordCountList = new LinkedList<>();
        documentList.forEach(document -> {
            wordCountList.addLast(
                    WordCount.builder()
                            .word(document.getString("_id"))
                            .count(document.getInteger("count"))
                            .build());
        });
        return wordCountList;
    }

    public void locationCount() {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
//        nativeSearchQueryBuilder
//                .withFilter(QueryBuilders.rangeQuery("time").gte("now-30m"))
//                .addAggregation(
//                        AggregationBuilders.terms("location").field("location")
//                );
        SearchHits<News> search = elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), News.class);
        search.getSearchHits().forEach(System.out::println);
    }

}
