package com.stone.nlp.executor;

public interface StoneNlpExecutor {
    String getEmotion(String document);
    String getBrief(String document, int briefMaxLength);
    String getClassification(String document);

}
