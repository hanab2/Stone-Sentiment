package com.stone.sentiment.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "word")
public class Word {
    @MongoId
    private String id;
    private String word;
    private Long time;
}
