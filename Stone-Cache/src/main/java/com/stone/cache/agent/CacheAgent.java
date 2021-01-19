package com.stone.cache.agent;

import com.stone.cache.pack.CachePack;

public interface CacheAgent {

    void store(CachePack cachePack);

    String fetch(String key);

    boolean delete(String key);

    boolean existKey(String key);
}
