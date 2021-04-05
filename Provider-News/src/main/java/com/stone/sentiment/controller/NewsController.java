package com.stone.sentiment.controller;

import com.stone.sentiment.bean.CommonResultBean;

import com.stone.sentiment.manager.TimeManager;
import com.stone.sentiment.service.NewsService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;


@RequestMapping(value = {"/news"})
@RestController
@Slf4j
public class NewsController {

    @Resource
    NewsService newsService;
    @Resource
    TimeManager timeManager;

    @CrossOrigin
    @GetMapping(value = {"/word_count"})
    public CommonResultBean wordCount(@RequestParam(name = "time_floor", defaultValue = "0") long timestamp, @RequestParam(value = "size", defaultValue = "20") int size) {
        return CommonResultBean.builder()
                .code(200)
                .success(true)
                .message("词频统计完毕")
                .data(newsService.wordCount(timeManager.getValidDayFloor(timestamp), size))
                .build();
    }

    @CrossOrigin
    @GetMapping(value = {"/location_count"})
    public CommonResultBean locationCount(@RequestParam(name = "time_floor", defaultValue = "0") long timestamp, @RequestParam(value = "size", defaultValue = "20") int size) {
        return CommonResultBean.builder()
                .code(200)
                .success(true)
                .message("地名统计完毕")
                .data(newsService.locationCount(timeManager.getValidDayFloor(timestamp), size))
                .build();
    }

    @CrossOrigin
    @GetMapping(value = {"/sentiment_count"})
    public CommonResultBean sentimentCount(@RequestParam(name = "time_floor", defaultValue = "0") long timestamp, @RequestParam(value = "size", defaultValue = "20") int size) {

        return CommonResultBean.builder()
                .code(200)
                .success(true)
                .message("地名统计完毕")
                .data(
                        newsService.sentimentCount(timeManager.getValidDayFloor(timestamp), size)
                )
                .build();
    }


    @CrossOrigin
    @GetMapping(value = {"/time_sentiment_analysis"})
    public CommonResultBean timeSentimentAnalysis(@RequestParam(name = "time_floor", defaultValue = "0") long timestamp) {
        return CommonResultBean.builder()
                .code(200)
                .success(true)
                .message("时间情感分析完毕")
                .data(
                        newsService.timeSentimentAnalysis(
                                timeManager.getValidDayFloor(timestamp)
                        )
                )
                .build();
    }

    @CrossOrigin
    @GetMapping(value = {"/similar_text"})
    public CommonResultBean similarTextSearch(@RequestParam(name = "text", defaultValue = "") String text, @RequestParam(name = "timestamp", defaultValue = "0") long timestamp) {
        return CommonResultBean.builder()
                .code(200)
                .success(true)
                .message("相似文本查询完毕")
                .data(
                        newsService.similarTextSearch(text, timeManager.getValidDayFloor(timestamp))
                )
                .build();
    }
}
