package com.stone.cache.target;

import org.springframework.stereotype.Component;

/**
 * 默认目标提取器
 *
 * @author hana_be@126.com
 * @date 2021-01-13
 */
@Component
//@ConditionalOnMissingBean(value = CacheTargetExtractor.class)
public class DefaultTargetExtractor implements CacheTargetExtractor{
}
