package com.stone.spider.pipeline;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.http.HtmlUtil;
import com.overzealous.remark.Remark;
import com.stone.nlp.executor.StoneNlpExecutor;

import com.stone.nlp.executor.impl.HanExecutor;
import com.stone.spider.model.RawNews;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Component
public class RawNewsPageModelPipeLine implements PageModelPipeline<RawNews> {

    @Resource
    Snowflake snowflake;

    @Resource(type = HanExecutor.class)
    StoneNlpExecutor hanExecutor;

    @Resource
    Remark remark;
    @Resource
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public void process(RawNews rawNews, Task task) {
        rawNews.setNewsId(snowflake.nextId());
        rawNews.setTitle(remark.convert(rawNews.getTitle()));
        String document = HtmlUtil.cleanHtmlTag(rawNews.getBody());
        String markdown = remark.convert(rawNews.getBody());
        rawNews.setBody(markdown);
        CompletableFuture<Void> groupFuture = CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return hanExecutor.getClassification(document);
                    } catch (Throwable e) {
                        return null;
                    }
                }
        ).thenAccept(rawNews::setTag);
        CompletableFuture<Void> briefFuture = CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return hanExecutor.getBrief(document, 50);
                    } catch (Throwable e) {
                        return null;
                    }
                }
        ).thenAccept(rawNews::setBrief);
        CompletableFuture<Void> emotionFuture = CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return hanExecutor.getEmotion(document);
                    } catch (Throwable e) {
                        return null;
                    }
                }
        ).thenAccept(rawNews::setEmotion);
        CompletableFuture<Void> all = CompletableFuture.allOf(briefFuture, emotionFuture, groupFuture);
//        while (!all.isDone()) {
//            //自旋
//            try {
//                TimeUnit.MILLISECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        all.thenRun(() -> {
            System.out.println(rawNews);
            elasticsearchRestTemplate.save(rawNews);
        });
//        elasticsearchRestTemplate.save(rawNews);
//        System.out.println(rawNews);
    }
}
