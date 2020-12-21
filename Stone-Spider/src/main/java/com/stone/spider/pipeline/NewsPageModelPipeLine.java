package com.stone.spider.pipeline;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.http.HtmlUtil;
import com.stone.nlp.hanlp.executor.HanExecutor;
import com.stone.nlp.hanlp.executor.impl.BriefExecutor;
import com.stone.nlp.hanlp.executor.impl.EmotionExecutor;
import com.stone.nlp.hanlp.executor.impl.GroupExecutor;
import com.stone.spider.model.RawNews;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    @Override
    public void process(RawNews rawNews, Task task) {
        rawNews.setNewsId(snowflake.nextId());
        Map<String, Object> map = new HashMap<>(2);
        map.put("text", HtmlUtil.cleanHtmlTag(rawNews.getBody()));
        map.put("size","1");

        rawNews.setTag(groupExecutor.execute(map));

        rawNews.setSummary(briefExecutor.execute(map));

        rawNews.setEmotion(emotionExecutor.execute(map));

        System.out.println(rawNews);
        System.out.println("==========================================");
        //rawNews.setSummary();
    }
}
