package com.stone.sentiment.mapper;

import com.alibaba.fastjson.JSON;
import com.stone.sentiment.model.Article;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;


public class ArticleElasticMapper {

    private static final String INDEX = "STONE-SENTIMENT-ARTICLE";

    @Resource
    RestHighLevelClient restHighLevelClient;

    public boolean insertOne(Article article) {
        article.setUpdateTime(article.getCreateTime());
        IndexRequest request = new IndexRequest(INDEX)
                .id(article.getArticleId().toString())
                .source(JSON.toJSONString(article), XContentType.JSON);
        boolean isSuccessful = false;
        try {
            IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            if (response.getShardInfo().getFailed() == 0) {
                isSuccessful = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccessful;
    }

    public boolean updateOne(Article article) {
        article.setUpdateTime(new Date());
        IndexRequest request = new IndexRequest(INDEX)
                .id(article.getArticleId().toString())
                .source(JSON.toJSONString(article), XContentType.JSON);
        boolean isSuccessful = false;
        try {
            IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            if (response.getShardInfo().getFailed() == 0) {
                isSuccessful = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccessful;
    }

    public Article getOneById(Long id) {
        GetRequest request = new GetRequest(INDEX, id.toString());
        Article article = null;
        try {
            GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
            if (response.isExists()) {
                article = JSON.parseObject(response.getSourceAsString(), Article.class);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return article;
    }

    public List<Article> getArticleListWithPaging(int pageNumber, int pageSize) {
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("status", 0);
        SearchRequest request = new SearchRequest(INDEX)
                .source(
                        new SearchSourceBuilder()
                                .query(termQueryBuilder)
                                .from(pageNumber)
                                .size(pageSize)
                                .sort("updateTime", SortOrder.DESC)
                );
        try {
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteOneById(Long id) {
        DeleteRequest deleteRequest = new DeleteRequest(INDEX, id.toString());
        boolean isSuccessful = false;
        try {
            DeleteResponse response = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
            if (response.getShardInfo().getFailed() == 0) {
                isSuccessful = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccessful;
    }


}
