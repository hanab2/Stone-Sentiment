package com.stone.sentiment.service.impl;

import com.stone.sentiment.mapper.NewsMapper;
import com.stone.sentiment.model.view.WordCount;
import com.stone.sentiment.service.NewsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    @Resource
    private NewsMapper newsMapper;

    @Override
    public List<WordCount> wordCount(LocalDateTime timeFloor, int size) {
        return newsMapper.wordCount(timeFloor, size);
    }
}
