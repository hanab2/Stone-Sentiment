package com.stone.sentiment.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Data
@Document(collection = "news")
public class News {

    @MongoId
    private Long id;
    private String title;
    private String body;
    private String source;
    @Field(value = "time")
    private Integer time;
    private String tag;

}
