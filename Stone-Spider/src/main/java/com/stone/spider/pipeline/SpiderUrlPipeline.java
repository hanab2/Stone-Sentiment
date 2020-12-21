package com.stone.spider.pipeline;

import cn.hutool.core.lang.Snowflake;
import com.stone.spider.mapper.StoreUrlMapper;
import com.stone.spider.model.StoreUrl;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class SpiderUrlPipeline implements Pipeline {

    @Resource
    StoreUrlMapper storeURLMapper;
    @Resource
    Snowflake snowflake;

    @Override
    public void process(ResultItems resultItems, Task task) {
        // 爬取目标的url
        System.out.println(resultItems.getRequest().getUrl());
        List<String> urlList = resultItems.get("urlList");
        urlList.parallelStream()
                .forEach(
                        url -> {
                            try {
                                StoreUrl storeUrl = StoreUrl.builder()
                                        .id(snowflake.nextId())
                                        .embodyTime(LocalDateTime.now())
                                        .url(url)
                                        .statusCode(0)
                                        .build();
                                storeURLMapper.insert(storeUrl);
                            } catch (Throwable e) {
                                System.out.println(e.getMessage());
                            }
                        }
                );

    }
}
