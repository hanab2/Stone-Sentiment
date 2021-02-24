package com.stone.sentiment.controller;

import com.stone.sentiment.bean.CommonResultBean;

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

    @CrossOrigin
    @GetMapping(value = {"/word_count"})
    public CommonResultBean wordCount(@RequestParam("time_floor") Long timestamp, @RequestParam(value = "size", defaultValue = "20") Integer size) {
        LocalDateTime localDateTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        localDateTime = localDateTime.minusDays(30);
        return CommonResultBean.builder()
                .code(200)
                .success(true)
                .message("词频统计完毕")
                .data(newsService.wordCount(localDateTime, size))
                .build();
    }

    @CrossOrigin
    @GetMapping(value = {"/location_count"})
    public CommonResultBean locationCount(@RequestParam("time_floor") Long timestamp, @RequestParam(value = "size", defaultValue = "20") Integer size) {
        LocalDateTime localDateTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        localDateTime = localDateTime.minusDays(30);
        return CommonResultBean.builder()
                .code(200)
                .success(true)
                .message("地名统计完毕")
                .data(newsService.locationCount(localDateTime, size))
                .build();
    }

    @CrossOrigin
    @GetMapping(value = {"/sentiment_count"})
    public CommonResultBean sentimentCount(@RequestParam("time_floor") Long timestamp, @RequestParam(value = "size", defaultValue = "20") Integer size) {
        LocalDateTime localDateTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        localDateTime = localDateTime.minusDays(30);
        return CommonResultBean.builder()
                .code(200)
                .success(true)
                .message("地名统计完毕")
                .data(newsService.sentimentCount(localDateTime, size))
                .build();
    }


}
