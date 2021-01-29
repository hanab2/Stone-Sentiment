package com.stone.cache.pipeline;

import com.stone.cache.pack.CachePack;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
//@ConditionalOnMissingBean(CachePipeline.class)
public class DefaultCachePipeline implements CachePipeline {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public void store(CachePack cachePack) {
        stringRedisTemplate.opsForValue()
                .set(cachePack.getKey(),cachePack.getJsonStingValue());
//        stringRedisTemplate.opsForHash()
//                .put(cachePack.getKey(), "targetValue", cachePack.getJsonStingValue());
        if (cachePack.getExpireDuration() > 0){
            stringRedisTemplate.expire(
                    cachePack.getKey(),
                    cachePack.getExpireDuration(),
                    cachePack.getTimeUnit()
            );
        }
    }

    @Override
    public String fetch(String key) {
        if (StringUtils.isBlank(key)) {
            throw new NullPointerException("key为空");
        }
        return stringRedisTemplate.opsForValue()
                .get(key);
    }

    @Override
    public boolean delete(String key) {
        if (StringUtils.isBlank(key)) {
            throw new NullPointerException("key为空");
        }
        Boolean delete = stringRedisTemplate.delete(key);
        return delete == null ? false : delete;
    }

    @Override
    public boolean existKey(String key) {
        if (StringUtils.isBlank(key)) {
            throw new NullPointerException("key为空");
        }
        Boolean hasKey = stringRedisTemplate.hasKey(key);
        return hasKey == null ? false : hasKey;
    }
}
