package com.stone.spider;

import com.stone.spider.processor.StoneSpiderProcessor;
import org.springframework.boot.test.context.SpringBootTest;
import us.codecraft.webmagic.Spider;

@SpringBootTest
public class Test {

    @org.junit.jupiter.api.Test
    public void test(){
        Spider.create(new StoneSpiderProcessor())
                //http://qc.wa.news.cn/nodeart/list?nid=11138933&pgnum=1&cnt=10&attr=63&tp=1&orderby=1&fullPath=1&callback=jQuery1124032851003006551216_1608020802838&_=1608020802839
                //.addUrl("http://qc.wa.news.cn/nodeart/list?nid=113352&pgnum=3&cnt=10&tp=1&orderby=1?callback=jQuery1124028108969597690714_1608019059587&_=1608019059590")
                .addUrl("http://qc.wa.news.cn/nodeart/list?nid=113352&pgnum=1&cnt=10")
                .run();
    }
}
