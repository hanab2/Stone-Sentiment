package com.stone.cache.exception;

/**
 * 缓存的目标异常
 *
 * @author hana_be@126.com
 * @date 2021-01-13
 */
public class CacheTargetException extends RuntimeException {

    /**
     * 缓存的目标异常
     *
     * @param message 消息
     */
    public CacheTargetException(String message) {
        super(message);
    }

}
