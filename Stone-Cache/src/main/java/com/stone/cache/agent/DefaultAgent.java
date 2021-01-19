package com.stone.cache.agent;

import com.stone.cache.pack.CachePack;
import com.stone.cache.pipeline.CachePipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
//@ConditionalOnMissingBean(CacheAgent.class)
public class DefaultAgent implements CacheAgent {

    private final static Logger logger = LoggerFactory.getLogger(DefaultAgent.class);

    @Resource
    CachePipeline cachePipeline;

    @Override
    public void store(CachePack cachePack) {
        cachePipeline.store(cachePack);
    }

    @Override
    public String fetch(String key) {
        return cachePipeline.fetch(key);
    }

    @Override
    public boolean delete(String key) {
        return cachePipeline.delete(key);
    }

    @Override
    public boolean existKey(String key) {
        return cachePipeline.existKey(key);
    }

//    private CachePack pack(Object target, Method method, Object... params) {
//        return CachePackBuilder.builder()
//                .key(keyGenerator.generateKey(target, method, params))
//                .jsonStingValue(
//                        jsonSerializer.valueToJsonString(
//                                cacheTargetExtractor.extractTarget(target, method, params)
//                        )
//                ).expireDuration(expireStrategyExtractor.extractTimeout(target, method, params))
//                .timeUnit(expireStrategyExtractor.extractTimeUnit(target, method, params))
//                .build();
//    }

}
