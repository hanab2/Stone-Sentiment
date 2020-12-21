package com.stone.nlp.hanlp.executor.impl;

import com.stone.nlp.hanlp.annotation.HanApi;
import com.stone.nlp.hanlp.executor.HanExecutor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@HanApi(token = "4c4fab07a9b44a609fdbd470a695a0231608100794351token",url = "http://comdo.hanlp.com/hanlp/v1/textAnalysis/sentimentAnalysis")
public class EmotionExecutor implements HanExecutor {
    @Override
    public String execute(Map<String, Object> params) {
        return extractEmotion(sendAndGetResultJsonString(params));
    }
    private String extractEmotion(String rawText) {
        String pattern = "【\\S+】";
        Pattern compile = Pattern.compile(pattern);
        Matcher matcher = compile.matcher(rawText);
        int start = 0, end = 0;
        while (matcher.find()) {
            start = matcher.start();
            end = matcher.end();
        }
//        System.out.println("+++++++++");
//        System.out.println(rawText);
//        System.out.println("/////////");
//        System.out.println(rawText.substring(start , end ));
        return rawText.substring(start + 1, end - 1);
    }
}
