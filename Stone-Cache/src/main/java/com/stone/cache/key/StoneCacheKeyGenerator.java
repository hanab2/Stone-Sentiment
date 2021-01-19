package com.stone.cache.key;


import com.stone.cache.annotation.CacheKey;
import com.stone.cache.exception.CacheKeyException;
import com.stone.cache.annotation.CacheProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;


import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;

/**
 * 默认Cache Key生成器
 * 当且仅当容器没有{@link KeyGenerator}实现类时才加载
 *
 * @author hana_be@126.com
 * @date 2021-01-05
 */
//@ConditionalOnMissingBean(KeyGenerator.class)
@Component
public class StoneCacheKeyGenerator implements KeyGenerator {

    /**
     * 生成key
     * 根据key生成策略来生成
     *
     * @param target 方法调用类
     * @param method 调用方法
     * @param params 调用方法参数列表
     * @return {@link String} Cache Key
     */
    @Override
    public String generateKey(Object target, Method method, Object... params) {
        CacheProperties properties = target.getClass().getAnnotation(CacheProperties.class);
        switch (properties.keyGenerateStrategy()) {
            case DEFAULT:
                return defaultGenerate(target, method, params);
            case CUSTOM:
                return costumeGenerate(target, method, params);
            default:
                return defaultGenerate(target, method, params);
        }
    }


    /**
     * 默认生成key方法
     * 阿里云Redis规范给的例子是：服务名:表名:id
     * 利用{@link CacheProperties}的serviceName、tableName以及方法入参被{@link CacheKey}标注的第一个参数拼接
     * 三者参数之间用{@link CacheProperties}的parametersSeparator分割
     * 例子：sentiment:article:10086
     *
     * @param target 方法调用类
     * @param method 调用方法
     * @param params 调用方法参数列表
     * @return {@link String} Cache Key
     */
    private String defaultGenerate(Object target, Method method, Object... params) {
        CacheProperties properties = target.getClass().getAnnotation(CacheProperties.class);
        Object keySuffix = findKeySuffixFromMethodParameters(method.getParameters(), params);
        return getKeyByKeyParameters(properties.parametersSeparator(), keySuffix, properties.serviceName(), properties.tableName());
    }

    /**
     * 默认生成key方法
     * 阿里云Redis规范给的例子是：服务名:表名:id
     * 利用{@link CacheProperties}的自定义前缀以及方法入参被{@link CacheKey}标注的第一个参数拼接
     * 三者参数之间用{@link CacheProperties}的parametersSeparator分割
     * 前缀是按照{@link String}数组顺序生成
     *
     * @param target 方法调用类
     * @param method 调用方法
     * @param params 调用方法参数列表
     * @return {@link String} Cache Key
     */
    private String costumeGenerate(Object target, Method method, Object... params) {
        CacheProperties properties = target.getClass().getAnnotation(CacheProperties.class);
        Object keySuffix = findKeySuffixFromMethodParameters(method.getParameters(), params);
        return getKeyByKeyParameters(properties.parametersSeparator(), keySuffix, properties.customKeyPrefixParameters());
    }


    /**
     * 在参数列表中找到第一个被{@link CacheKey}标注的参数并返回该参数
     *
     * @param parameters 参数
     * @param params     参数列表
     * @return {@link Object}* @throws CacheKeyException 缓存键例外  第一个被{@link CacheKey}标注的参数
     */
    private Object findKeySuffixFromMethodParameters(Parameter[] parameters, Object[] params) throws CacheKeyException {
        for (int i = 0; i < params.length; i++) {
            if (parameters[i].isAnnotationPresent(CacheKey.class)) {
                return params[i];
            }
        }
        throw new CacheKeyException("没有找到@CacheKey修饰的参数！~");
    }

    /**
     * 验证前缀列表是否有空
     *
     * @param prefix 前缀
     */
    private void validatePrefix(String... prefix) {
        for (String arg : prefix) {
            if (StringUtils.isBlank(arg)) {
                throw new CacheKeyException("请检查@CacheProperties的prefix参数是否为空！");
            }
        }
    }

    /**
     * 验证后缀是否存在
     *
     * @param suffix 后缀
     */
    private void validateSuffix(Object suffix) {
        if (Objects.isNull(suffix)) {
            throw new CacheKeyException("请检查是否有@CacheKey修饰参数！");
        }
    }

    /**
     * 根据前缀、后缀、分隔符生成key
     *
     * @param parametersSeparator 分隔符
     * @param keySuffix           key后缀
     * @param keyPrefix           key前缀
     * @return {@link String}
     */
    private String getKeyByKeyParameters(char parametersSeparator, Object keySuffix, String... keyPrefix) {
        StringBuilder stringBuilder = new StringBuilder();
        validatePrefix(keyPrefix);
        validateSuffix(keySuffix);
        for (Object prefixKeyParam : keyPrefix) {
            stringBuilder.append(prefixKeyParam);
            stringBuilder.append(parametersSeparator);
        }
        stringBuilder.append(keySuffix);
        return stringBuilder.toString();
    }

}
