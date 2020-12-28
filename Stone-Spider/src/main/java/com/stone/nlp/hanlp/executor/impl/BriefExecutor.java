package com.stone.nlp.hanlp.executor.impl;

import cn.hutool.json.JSONUtil;
import com.stone.nlp.hanlp.annotation.HanApi;
import com.stone.nlp.hanlp.executor.HanExecutor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@HanApi(token = "4225112cdf7e4ddebad67067fe6d9c7d1608100327974token", url = "http://comdo.hanlp.com/hanlp/v1/summary/extract")
public class BriefExecutor implements HanExecutor {
    @Override
    public String execute(Map<String, Object> params) {
        return extractEmotion(sendAndGetResultJsonString(params));
    }
    private String extractEmotion(String rawText){
        return (String) JSONUtil.getByPath(JSONUtil.parse(rawText),"data[0].word");
    }
}
