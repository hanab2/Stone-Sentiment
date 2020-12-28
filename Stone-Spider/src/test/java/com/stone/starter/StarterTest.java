package com.stone.starter;

import com.stone.nlp.executor.StoneNlpExecutor;
import com.stone.nlp.executor.impl.HanExecutor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class StarterTest {
    @Resource(type = HanExecutor.class)
    StoneNlpExecutor hanExecutor;

    @Test
    void test() {
        System.out.println("文本分类    " + hanExecutor.getClassification("英国造航母耗时8年仍未服役 被中国速度远远甩在身后"));
        System.out.println("情感分析    " + hanExecutor.getEmotion("结果大失所望，灯光昏暗，空间极其狭小，床垫质量恶劣，房间还伴着一股霉味。"));
    }
}
