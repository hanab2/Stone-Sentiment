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
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    public List<WordCount> locationCount(LocalDateTime timeFloor, int size) {
        LinkedList<WordCount> wordCountList = new LinkedList<>();
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        NativeSearchQuery query = queryBuilder
                .withPageable(PageRequest.of(0, size))
                .withFilter(QueryBuilders.boolQuery()
                        .must(QueryBuilders.rangeQuery("time").gte(timeFloor.toInstant(ZoneOffset.of("+8")).toEpochMilli()))
                )
                .withSort(SortBuilders.fieldSort("time").order(SortOrder.DESC))
                .addAggregation(
                        AggregationBuilders.terms("location_count").field("location.keyword")
                )
                .build();
        SearchHits<News> search = elasticsearchRestTemplate.search(query, News.class);
        Aggregations aggregations = search.getAggregations();
        if (aggregations == null) {
            return wordCountList;
        }
        Map<String, org.elasticsearch.search.aggregations.Aggregation> map = aggregations.getAsMap();
        ParsedStringTerms locationCount = (ParsedStringTerms) map.get("location_count");
        locationCount.getBuckets().forEach(bucket -> {
            wordCountList.addLast(
                    WordCount.builder()
                            .word(bucket.getKeyAsString())
                            .count((int) bucket.getDocCount())
                            .build());
        });
        return wordCountList;
    }

    public List<WordCount> sentimentCount(LocalDateTime timeFloor, int size) {
        LinkedList<WordCount> wordCountList = new LinkedList<>();
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        NativeSearchQuery query = queryBuilder
                .withPageable(PageRequest.of(0, size))
                .withFilter(QueryBuilders.boolQuery()
                        .must(QueryBuilders.rangeQuery("time").gte(timeFloor.toInstant(ZoneOffset.of("+8")).toEpochMilli()))
                )
                .withSort(SortBuilders.fieldSort("time").order(SortOrder.DESC))
                .addAggregation(
                        AggregationBuilders.terms("sentiment_count").field("sentiment.keyword")
                )
                .build();
        SearchHits<News> search = elasticsearchRestTemplate.search(query, News.class);
        Aggregations aggregations = search.getAggregations();
        if (aggregations == null) {
            return wordCountList;
        }
        Map<String, org.elasticsearch.search.aggregations.Aggregation> map = aggregations.getAsMap();
        ParsedStringTerms locationCount = (ParsedStringTerms) map.get("sentiment_count");
        locationCount.getBuckets().forEach(bucket -> {
            wordCountList.addLast(
                    WordCount.builder()
                            .word(bucket.getKeyAsString())
                            .count((int) bucket.getDocCount())
                            .build());
        });
        return wordCountList;
    }

}
