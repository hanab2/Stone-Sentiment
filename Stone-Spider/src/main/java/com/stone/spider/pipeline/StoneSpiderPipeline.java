package com.stone.spider.pipeline;

import com.stone.spider.mapper.StoreURLMapper;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;

@Component
public class StoneSpiderPipeline implements Pipeline {

    @Resource
    StoreURLMapper storeURLMapper;

    @Override
    public void process(ResultItems resultItems, Task task) {

    }
}
