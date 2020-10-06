package com.stone.sentiment.mapper;

import com.stone.sentiment.model.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleMapper extends ElasticsearchRepository<Article,Long> {
}
