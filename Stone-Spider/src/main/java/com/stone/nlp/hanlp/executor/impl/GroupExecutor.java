package com.stone.nlp.hanlp.executor.impl;

import com.stone.nlp.hanlp.annotation.HanApi;
import com.stone.nlp.hanlp.executor.HanExecutor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@HanApi(token = "1e8f6f01f11a47118ef2c0eb25ebfbd31608100940719token", url = "http://comdo.hanlp.com/hanlp/v1/textAnalysis/classification")
public class GroupExecutor implements HanExecutor {
    @Override
    public String execute(Map<String, Object> params) {
        return extractGroup(sendAndGetResultJsonString(params));
    }

    private String extractGroup(String rawText) {
        String pattern = "【\\S+】";
        Pattern compile = Pattern.compile(pattern);
        Matcher matcher = compile.matcher(rawText);
        int start = 0, end = 0;
        while (matcher.find()) {
            start = matcher.start();
            end = matcher.end();
        }
        return rawText.substring(start + 1, end - 1);
    }
}
