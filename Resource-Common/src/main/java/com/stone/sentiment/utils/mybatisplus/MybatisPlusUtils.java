package com.stone.sentiment.utils.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stone.sentiment.annotation.rdbms.Secret;

import java.util.Arrays;

public class MybatisPlusUtils {

    public static <T> QueryWrapper<T> getQueryWrapperWithConditionCoverBySecret(T target) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        Arrays.stream(target.getClass().getFields())
                .forEach(field -> {
                    if (field.isAnnotationPresent(Secret.class)) {
                        if (!field.getAnnotation(Secret.class).isHidden()) {
                            Object fieldValue = null;
                            try {
                                fieldValue = field.get(target);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            //通过反射获取不为null的成员变量并放进wrapper
                            if (fieldValue != null) {
                                queryWrapper.eq(field.getName(), fieldValue);
                                //queryWrapper.select(field.getName());
                            }
                        }
                    }
                });
        return queryWrapper;
    }
    public static <T> QueryWrapper<T> getQueryWrapperCoverBySecret(T target) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        Arrays.stream(target.getClass().getFields())
                .forEach(field -> {
                    if (field.isAnnotationPresent(Secret.class)) {
                        if (!field.getAnnotation(Secret.class).isHidden()) {
                            Object fieldValue = null;
                            try {
                                fieldValue = field.get(target);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            //通过反射获取成员变量名并放进wrapper
                            if (fieldValue != null) {
                                queryWrapper.select(field.getName());
                            }
                        }
                    }
                });
        return queryWrapper;
    }

}
