package com.stone.spider;

import com.stone.spider.model.RawNews;
import com.stone.spider.pipeline.NewsPageModelPipeLine;
import com.stone.spider.pipeline.RawNewsPageModelPipeLine;
import com.stone.spider.pipeline.SpiderUrlPipeline;
import com.stone.spider.processor.SpiderUrlProcessor;
import com.stone.spider.scheduler.MySqlDuplicateRemovedScheduler;
import org.springframework.boot.test.context.SpringBootTest;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.OOSpider;

import javax.annotation.Resource;

@SpringBootTest
public class Test {

    @Resource
    SpiderUrlPipeline spiderURLPipeline;
    @Resource
    SpiderUrlProcessor spiderURLProcessor;

    @org.junit.jupiter.api.Test
    public void test() {
        Spider.create(spiderURLProcessor)
                //http://qc.wa.news.cn/nodeart/list?nid=11138933&pgnum=1&cnt=10&attr=63&tp=1&orderby=1&fullPath=1&callback=jQuery1124032851003006551216_1608020802838&_=1608020802839
                //.addUrl("http://qc.wa.news.cn/nodeart/list?nid=113352&pgnum=3&cnt=10&tp=1&orderby=1?callback=jQuery1124028108969597690714_1608019059587&_=1608019059590")
                .addUrl("http://qc.wa.news.cn/nodeart/list?nid=113352&pgnum=1&cnt=10")
                .addPipeline(spiderURLPipeline)
                .run();
    }


    @Resource
    MySqlDuplicateRemovedScheduler mySqlDuplicateRemovedScheduler;
    @Resource
    NewsPageModelPipeLine newsPageModelPipeLine;

    @org.junit.jupiter.api.Test
    public void test2() {
//        OOSpider.create(Site.me().setRetryTimes(3).setSleepTime(2000), new ConsolePageModelPipeline(), RawNews.class)
//                //.setScheduler(mySqlDuplicateRemovedScheduler)
//                //.addUrl("https://www.thepaper.cn/channel_25950")
//                .addUrl("https://news.sohu.com/")
//                .run();
        OOSpider.create(Site.me().setRetryTimes(3).setSleepTime(2000), newsPageModelPipeLine, RawNews.class)
                //.setScheduler(mySqlDuplicateRemovedScheduler)
                //.addUrl("https://www.thepaper.cn/channel_25950")
                .addUrl("https://news.sohu.com/")
                .run();
    }


    @Resource
    RawNewsPageModelPipeLine rawNewsPageModelPipeLine;
    @org.junit.jupiter.api.Test
    public void test3() {
        OOSpider.create(Site.me().setRetryTimes(3).setSleepTime(2000), rawNewsPageModelPipeLine, RawNews.class)
                .addUrl("https://news.sohu.com/")
                .thread(5)
                .run();
    }
}
