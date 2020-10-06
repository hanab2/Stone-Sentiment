package com.stone.sentiment.utils.elasticsearch;

import com.stone.sentiment.model.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.Query;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ElasticSearchUtils {

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    public <ID, T> T getOneById(ID id, Class<T> clazz) {
        return elasticsearchRestTemplate.get(id.toString(), clazz);
    }

    public <T> T save(T data) {
        return elasticsearchRestTemplate.save(data);
    }

    public <ID, T> void deleteOneById(ID id, Class<T> clazz) {
        elasticsearchRestTemplate.delete(id.toString(), clazz);
    }

    public <T> ElasticPageResult search(Query query, Class<T> calzz) {
        SearchHits<T> searchHits = elasticsearchRestTemplate.search(query, calzz);
        long total = searchHits.getTotalHits();
        //  获取到查询到的对象包装类
        List<T> articleList = searchHits.getSearchHits()
                .stream()
                //  通过java8新特性提取包装类的对象
                .map(SearchHit::getContent)
                //  将对象整合成list
                .collect(Collectors.toList());
        return ElasticPageResult
                .builder()
                .total(total)
                .data(articleList)
                .build();
    }

    public <T> ElasticPageResult searchWithPage(Query query, Class<T> calzz, int pageNum, int pageSize) {
        query.setPageable(PageRequest.of(pageNum, pageSize));
        return search(query, calzz);
    }

    public <T> ElasticPageResult termsSearchWithPageByObjectPortrayal(T data, int pageNum, int pageSize) {
        Class<?> targetClass = data.getClass();
//        for (Field field : targetClass.getFields()) {
//            field.setAccessible(true);
//            Object fieldValue = null;
//            try {
//                fieldValue = field.get(targetClass);
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//            if(fieldValue != null){
//                boolQueryBuilder.must(QueryBuilders.termQuery(field.getName(),fieldValue));
//            }
//        }
        List<TermQueryBuilder> termQueryBuilderList = Arrays.stream(targetClass.getFields())
                //  反射获取对象画像的非空字段值
                .map(field -> {
                    field.setAccessible(true);
                    Object fieldValue = null;
                    try {
                        fieldValue = field.get(targetClass);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    if (fieldValue != null) {
                        return QueryBuilders.termQuery(field.getName(), fieldValue);
                    }
                    return null;
                })
                //  通过过滤器过滤掉空的queryBuilder
                .filter(Objects::nonNull)
                //  收集queryBuilder成list
                .collect(Collectors.toList());
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //  把查询条件加入，设置为全满足查询条件参返回数据
        boolQueryBuilder.must().addAll(termQueryBuilderList);
        NativeSearchQuery searchQuery = new NativeSearchQuery(boolQueryBuilder);
        return searchWithPage(searchQuery, targetClass, pageNum, pageSize);
    }
}
