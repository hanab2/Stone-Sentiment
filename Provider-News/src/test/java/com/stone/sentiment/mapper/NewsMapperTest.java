package com.stone.sentiment.mapper;

import com.stone.sentiment.model.view.WordCount;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
public class NewsMapperTest {

    @Resource
    NewsMapper newsMapper;

    @Test
    void testWordCount() {
        long start = System.currentTimeMillis();
        LocalDateTime timestamp = LocalDateTime.now().minusDays(32);
        List<WordCount> wordCountList = newsMapper.wordCount(timestamp, 30);
        System.out.println(wordCountList);
        System.out.println(System.currentTimeMillis() - start);

    }

}
