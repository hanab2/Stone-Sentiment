package com.stone.sentiment.service;

import com.stone.sentiment.model.Article;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;

public interface BaseElasticSearchService<T,ID> {

    public T getOneById(ID id);

    public T save(T data);

    public void deleteOneById(ID id);

    public SearchHits<T> search(Query query);

    public int getTotal();
}
