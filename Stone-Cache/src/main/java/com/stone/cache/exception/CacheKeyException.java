package com.stone.cache.exception;

/**
 * 缓存key异常
 *
 * @author hana_be@126.com
 * @date 2021-01-08
 */
public class CacheKeyException extends RuntimeException{
    private static final long serialVersionUID = -3890039578860354281L;
    /**
     * 缓存key异常
     *
     * @param message 异常信息
     */
    public CacheKeyException(String message) {
        super(message);
    }
}
