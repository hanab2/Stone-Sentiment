package com.stone.spider.processor;

import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Json;

@Component
public class SpiderUrlProcessor implements PageProcessor {

    @Override
    public void process(Page page) {
        //System.out.println(page.getHtml().toString());
        Json json = new Json(StrUtil.strip(page.getJson().get(), "(", ")"));
//        json.jsonPath("$.data.list[*].LinkUrl").all()
//                .forEach(System.out::println);
        page.putField("urlList",json.jsonPath("$.data.list[*].LinkUrl").all());
    }

    @Override
    public Site getSite() {
        return Site.me()
                .setSleepTime(300)
                .setRetryTimes(3)
                .setRetrySleepTime(200)
                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.25 Safari/537.36 Core/1.70.3775.400 QQBrowser/10.6.4208.400");
    }
}
