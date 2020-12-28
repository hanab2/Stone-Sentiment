package com.stone.spider.pipeline;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.http.HtmlUtil;
import com.overzealous.remark.Remark;
import com.stone.nlp.executor.StoneNlpExecutor;
import com.stone.nlp.hanlp.executor.HanExecutor;
import com.stone.nlp.hanlp.executor.impl.BriefExecutor;
import com.stone.nlp.hanlp.executor.impl.EmotionExecutor;
import com.stone.nlp.hanlp.executor.impl.GroupExecutor;
import com.stone.spider.model.RawNews;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class NewsPageModelPipeLine implements PageModelPipeline<RawNews> {

    @Resource
    Snowflake snowflake;
    @Resource(type = BriefExecutor.class)
    HanExecutor briefExecutor;
    @Resource(type = GroupExecutor.class)
    HanExecutor groupExecutor;
    @Resource(type = EmotionExecutor.class)
    HanExecutor emotionExecutor;


    @Resource(type = com.stone.nlp.executor.impl.HanExecutor.class)
    StoneNlpExecutor hanExecutor;

    @Resource
    Remark remark;

    @Override
    public void process(RawNews rawNews, Task task) {
        rawNews.setNewsId(snowflake.nextId());
        Map<String, Object> map = new HashMap<>(2);
        map.put("text", HtmlUtil.cleanHtmlTag(rawNews.getBody()));
        map.put("size", "1");

        CompletableFuture<Void> groupFuture = CompletableFuture.supplyAsync(
                () -> {
                    try {

                        return groupExecutor.execute(map);
                    } catch (Throwable e) {
                        return null;
                    }
                }
        ).thenAccept(rawNews::setTag);

        CompletableFuture<Void> briefFuture = CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return briefExecutor.execute(map);
                    } catch (Throwable e) {
                        return null;
                    }
                }
        ).thenAccept(rawNews::setBrief);

        CompletableFuture<Void> emotionFuture = CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return emotionExecutor.execute(map);
                    } catch (Throwable e) {
                        return null;
                    }
                }
        ).thenAccept(rawNews::setEmotion);
//        while (!briefFuture.isDone() && !emotionFuture.isDone() && !groupFuture.isDone()){
//
//        }
        while (!CompletableFuture.allOf(briefFuture, emotionFuture, groupFuture).isDone()) {
            //自旋
        }
        System.out.println(rawNews);
    }
}
