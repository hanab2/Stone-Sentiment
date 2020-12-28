package com.stone.nlp.executor.impl;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.classification.classifiers.IClassifier;
import com.stone.nlp.executor.StoneNlpExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class HanExecutor implements StoneNlpExecutor {

    @Resource
    IClassifier emotionClassifier;
    @Resource
    IClassifier classificationClassifier;

    @Override
    public String getEmotion(String document) {
        return emotionClassifier.classify(document);
    }

    @Override
    public String getBrief(String document, int briefMaxLength) {
        return HanLP.getSummary(document, briefMaxLength);
    }

    @Override
    public String getClassification(String document) {
        return classificationClassifier.classify(document);
    }
}
