package com.stone.cache.pipeline;

import com.stone.cache.pack.CachePack;

/**
 * 传输层
 *
 * @author hana_be@126.com
 * @date 2021-01-11
 */
public interface CachePipeline {

    /**
     * 传输到缓存中
     *
     * @param cachePack 缓存包
     */
    void store(CachePack cachePack);


    /**
     * 获取
     *
     * @param key key
     * @return {@link String}
     */
    String fetch(String key);


    /**
     * 删除
     *
     * @param key key
     * @return boolean
     */
    boolean delete(String key);

    /**
     * 存在key
     *
     * @param key key
     * @return boolean
     */
    boolean existKey(String key);
}
