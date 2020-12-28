package com.stone.sentiment;

import cn.hutool.core.lang.Snowflake;
import com.stone.sentiment.model.Article;
import com.stone.sentiment.utils.elasticsearch.ElasticPageResult;
import com.stone.sentiment.utils.elasticsearch.ElasticSearchUtils;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@SpringBootTest
public class Test {

    @Resource
    ElasticSearchUtils elasticSearchUtils;
    @Resource
    Snowflake snowflake;

    @org.junit.jupiter.api.Test
    void test() {
        Article article = Article.builder()
                .articleId(snowflake.nextId())
                .authorId(snowflake.nextId())
                .body("测试展示")
                .createTime(new Date())
                .updateTime(new Date())
                .status( 0)
                .title("测试第n个")
                .tagList(new ArrayList<String>(Arrays.asList("体育","中国")))
                .build();
        Article save = elasticSearchUtils.save(article);
        System.out.println(save);
    }

    @org.junit.jupiter.api.Test
    void test2() {
        Article article = Article.builder()
                .articleId(1L)
                .build();
        ElasticPageResult elasticPageResult = elasticSearchUtils.termsSearchWithPageByObjectPortrayal(article, 0, 1);
        System.out.println(elasticPageResult);
    }
}
