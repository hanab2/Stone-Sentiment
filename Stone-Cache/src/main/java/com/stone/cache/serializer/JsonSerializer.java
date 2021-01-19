package com.stone.cache.serializer;

import com.alibaba.fastjson.JSON;

/**
 * json序列化器
 *
 * @author hana_be@126.com
 * @date 2021-01-09
 */
public interface JsonSerializer {

    /**
     * 将object转换成json字符串
     *
     * @param object 目标对象
     * @return {@link String}
     */
    default String objectToJsonString(Object object) {
        return JSON.toJSONString(object);
    }

    /**
     * 将json字符串转换成对象
     *
     * @param jsonString json字符串
     * @param clazz      clazz
     * @return {@link T}
     */
    default <T> T jsonStringToObject(String jsonString, Class<T> clazz) {
        return JSON.parseObject(jsonString, clazz);
    }
}
