package com.stone.sentiment.service;

import com.alibaba.fastjson.JSONObject;
import com.stone.sentiment.model.view.WordCount;

import java.time.LocalDateTime;
import java.util.List;

public interface NewsService {

    List<WordCount> wordCount(LocalDateTime timeFloor, int size);

    List<WordCount> locationCount(LocalDateTime timeFloor, int size);

    List<WordCount> sentimentCount(LocalDateTime timeFloor, int size);

    JSONObject timeSentimentAnalysis(LocalDateTime timeFloor);
}
