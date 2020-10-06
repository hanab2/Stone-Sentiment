package com.stone.sentiment.service;

import com.stone.sentiment.model.Article;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleServiceImpl {

    @Resource
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    public Article getOneById(Long id) {
        return elasticsearchRestTemplate.get(id.toString(),Article.class);
    }

    public Article save(Article data) {
        return elasticsearchRestTemplate.save(data);
    }

    public void deleteOneById(Long id) {
        elasticsearchRestTemplate.delete(id.toString(),Article.class);
    }

    public SearchHits<Article> search(Query query) {
        SearchHits<Article> searchHits = elasticsearchRestTemplate.search(query, Article.class);
        long total = searchHits.getTotalHits();
        List<Article> articleList = searchHits.getSearchHits()
                .stream()
                .map(item -> item.getContent()).collect(Collectors.toList());

        return elasticsearchRestTemplate.search(query,Article.class);
    }

}
