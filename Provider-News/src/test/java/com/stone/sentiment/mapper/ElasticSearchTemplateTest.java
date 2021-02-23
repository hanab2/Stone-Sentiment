package com.stone.sentiment.mapper;

import com.stone.sentiment.model.News;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@SpringBootTest
public class ElasticSearchTemplateTest {

    @Resource
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Test
    void testFind() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(QueryBuilders.termQuery("id", 1361899194528301000L));
//        CriteriaQuery criteriaQuery = new CriteriaQuery(Criteria.where("time").greaterThanEqual(LocalDateTime.now().minusDays(1)));
//        SearchHit<News> newsSearchHit = elasticsearchRestTemplate.searchOne(queryBuilder.build(), News.class);
//        System.out.println(newsSearchHit);
        SearchHits<News> search = elasticsearchRestTemplate.search(queryBuilder.build(), News.class);
        search.forEach(System.out::println);
    }

    @Test
    void testSearchOne() {
        NativeSearchQuery title = new NativeSearchQueryBuilder().withQuery(QueryBuilders.termsQuery("sentiment", "negative")).build();
        SearchHit<News> newsSearchHit = elasticsearchRestTemplate.searchOne(title, News.class);
        assert newsSearchHit != null;
        System.out.println(newsSearchHit.getContent());
    }

    @Test
    void testSearchById() {
        News news = elasticsearchRestTemplate.get("1361899194528301056", News.class);
        System.out.println(news);
    }

    @Test
    void testMatchSearch() {
        long start = System.currentTimeMillis();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery
//                .filter(QueryBuilders.rangeQuery("time").gte(LocalDateTime.now().minusDays(2)))
                .filter(QueryBuilders.matchQuery("title", "美媒"));
        NativeSearchQuery query = new NativeSearchQuery(boolQuery);
        SearchHits<News> search = elasticsearchRestTemplate.search(query, News.class);
        search.getSearchHits().forEach(newsSearchHit -> {
            System.out.println(newsSearchHit.getContent().getTitle());
        });
        System.out.println("耗时： " + (System.currentTimeMillis() - start));
    }

    @Test
    void testMultiMatch() {
        long start = System.currentTimeMillis();
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        NativeSearchQuery query = queryBuilder.withPageable(PageRequest.of(0, 10))
                .withFilter(QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("title", "美"))
                        .must(QueryBuilders.matchQuery("location", "美"))
                )
                .build();
        SearchHits<News> search = elasticsearchRestTemplate.search(query, News.class);
        search.getSearchHits().forEach(newsSearchHit -> {
            System.out.println(newsSearchHit.getContent().getTitle());
        });
        System.out.println("耗时： " + (System.currentTimeMillis() - start));
    }

    @Test
    void testMultiMatchAndTimeLimit() {
        long start = System.currentTimeMillis();
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        NativeSearchQuery query = queryBuilder.withPageable(PageRequest.of(0, 10))
                .withFilter(QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("title", "美"))
                        .must(QueryBuilders.matchQuery("location", "美"))
                        .must(QueryBuilders.rangeQuery("time").gte(LocalDateTime.now().minusDays(7).toInstant(ZoneOffset.of("+8")).toEpochMilli()))
                )
                .build();
        SearchHits<News> search = elasticsearchRestTemplate.search(query, News.class);
        search.getSearchHits().forEach(newsSearchHit -> {
            System.out.println(newsSearchHit.getContent().getTitle());
        });
        System.out.println("耗时： " + (System.currentTimeMillis() - start));
    }

    @Test
    void testSort() {
        long start = System.currentTimeMillis();
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        NativeSearchQuery query = queryBuilder
                .withPageable(PageRequest.of(0, 10))
                .withFilter(QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("title", "美"))
                        .must(QueryBuilders.matchQuery("location", "美"))
                        .must(QueryBuilders.rangeQuery("time").gte(LocalDateTime.now().minusDays(7).toInstant(ZoneOffset.of("+8")).toEpochMilli()))
                )
                .withSort(SortBuilders.fieldSort("time").order(SortOrder.DESC))
                .build();
        SearchHits<News> search = elasticsearchRestTemplate.search(query, News.class);
        search.getSearchHits().forEach(newsSearchHit -> {
            News news = newsSearchHit.getContent();
            System.out.println(news.getTitle() + "-----" + news.getTime());
        });
        System.out.println("耗时： " + (System.currentTimeMillis() - start));
    }

    @Test
    void testAggregation() {
        long start = System.currentTimeMillis();
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        NativeSearchQuery query = queryBuilder
                .withPageable(PageRequest.of(0, 10))
                .withFilter(QueryBuilders.boolQuery()
//                        .must(QueryBuilders.matchQuery("title", "美"))
//                        .must(QueryBuilders.matchQuery("location", "美"))
                                .must(QueryBuilders.rangeQuery("time").gte(LocalDateTime.now().minusDays(17).toInstant(ZoneOffset.of("+8")).toEpochMilli()))
                )
                .withSort(SortBuilders.fieldSort("time").order(SortOrder.DESC))
                .addAggregation(
                        AggregationBuilders.terms("location_count").field("location.keyword")
                )
                .build();
        SearchHits<News> search = elasticsearchRestTemplate.search(query, News.class);
        System.out.println(search);
        Aggregations aggregations = search.getAggregations();
        Map<String, Aggregation> map = aggregations.getAsMap();
        System.out.println(map);
        ParsedStringTerms location_count = (ParsedStringTerms) map.get("location_count");
        location_count.getBuckets().forEach(bucket -> {
            System.out.println(bucket.getKey() + "----" + bucket.getDocCount());
        });
        System.out.println("耗时： " + (System.currentTimeMillis() - start));
    }

    @Test
    void testLocationAggregation() {
        long start = System.currentTimeMillis();
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        NativeSearchQuery query = queryBuilder
                .withPageable(PageRequest.of(0, 10))
                .withFilter(QueryBuilders.boolQuery()
                        .must(QueryBuilders.rangeQuery("time").gte(LocalDateTime.now().minusDays(17).toInstant(ZoneOffset.of("+8")).toEpochMilli()))
                )
                .withSort(SortBuilders.fieldSort("time").order(SortOrder.DESC))
                .addAggregation(
                        AggregationBuilders.terms("location_count").field("location.keyword")
                )
                .build();
        SearchHits<News> search = elasticsearchRestTemplate.search(query, News.class);
        Map<String, Aggregation> map = Objects.requireNonNull(search.getAggregations()).getAsMap();
        ParsedStringTerms location_count = (ParsedStringTerms) map.get("location_count");
        location_count.getBuckets().forEach(bucket -> {
            System.out.println(bucket.getKey() + "----" + bucket.getDocCount());
        });
        System.out.println("耗时： " + (System.currentTimeMillis() - start));
    }

    public static void main(String[] args) {
        //时间戳获取
        System.out.println(Calendar.getInstance(Locale.CHINA).getTimeInMillis());
        System.out.println(LocalDateTime.now().minusDays(2).toInstant(ZoneOffset.of("+8")).toEpochMilli());
    }
}
