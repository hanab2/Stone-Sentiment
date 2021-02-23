package com.stone.sentiment.service;

import com.stone.sentiment.model.view.WordCount;

import java.time.LocalDateTime;
import java.util.List;

public interface NewsService {

    List<WordCount> wordCount(LocalDateTime timeFloor, int size);
}
