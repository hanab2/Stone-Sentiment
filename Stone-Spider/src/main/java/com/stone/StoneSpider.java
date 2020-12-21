package com.stone;

import com.stone.spider.processor.SpiderUrlProcessor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import us.codecraft.webmagic.Spider;

@MapperScan(value = "com.stone.spider.mapper")
@SpringBootApplication
public class StoneSpider {
    public static void main(String[] args) {
        SpringApplication.run(StoneSpider.class,args);
        System.out.println("start");
        Spider.create(new SpiderUrlProcessor())
                .addUrl("https://m.sohu.com/ch/8")
                .run();
    }
}
