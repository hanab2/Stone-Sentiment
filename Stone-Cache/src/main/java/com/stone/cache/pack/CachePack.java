package com.stone.cache.pack;

import java.util.concurrent.TimeUnit;

public class CachePack {
    private String key;
    private String jsonStingValue;
    private Long expireDuration;
    private TimeUnit timeUnit;

    public CachePack(){
        this.key = "";
        this.jsonStingValue = null;
        this.expireDuration = 1L;
        this.timeUnit = TimeUnit.MINUTES;
    }
    public CachePack(String key, String jsonStingValue, Long expireDuration, TimeUnit timeUnit) {
        this.key = key;
        this.jsonStingValue = jsonStingValue;
        this.expireDuration = expireDuration;
        this.timeUnit = timeUnit;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getJsonStingValue() {
        return jsonStingValue;
    }

    public void setJsonStingValue(String jsonStingValue) {
        this.jsonStingValue = jsonStingValue;
    }

    public Long getExpireDuration() {
        return expireDuration;
    }

    public void setExpireDuration(Long expireDuration) {
        this.expireDuration = expireDuration;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    @Override
    public String toString() {
        return "CachePack{" +
                "key='" + key + '\'' +
                ", jsonStingValue='" + jsonStingValue + '\'' +
                ", expireDuration=" + expireDuration +
                ", timeUnit=" + timeUnit +
                '}';
    }
}
