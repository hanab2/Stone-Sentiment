package com.stone.cache.pack;

import java.util.concurrent.TimeUnit;

public class CachePackBuilder {

    private CachePack cachePack;

    public CachePackBuilder key(String key) {
        this.cachePack.setKey(key);
        return this;
    }

    public CachePackBuilder jsonStingValue(String jsonStingValue) {
        this.cachePack.setJsonStingValue(jsonStingValue);
        return this;
    }

    public CachePackBuilder expireDuration(Long expireDuration) {
        this.cachePack.setExpireDuration(expireDuration);
        return this;
    }

    public CachePackBuilder timeUnit(TimeUnit timeUnit) {
        this.cachePack.setTimeUnit(timeUnit);
        return this;
    }

    public CachePack build() {
        return this.cachePack;
    }

    public CachePackBuilder() {
        this.cachePack = new CachePack();
    }

    public CachePackBuilder(CachePack cachePack) {
        this.cachePack = cachePack;
    }

    public static CachePackBuilder builder() {
        return new CachePackBuilder();
    }
}
