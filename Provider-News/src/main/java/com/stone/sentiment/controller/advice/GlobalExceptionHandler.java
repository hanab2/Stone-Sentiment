package com.stone.sentiment.controller.advice;

import com.stone.sentiment.bean.CommonResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({Throwable.class})
    public CommonResultBean exceptionHandle(Throwable exception) {
        log.warn("{}", exception.getMessage());
        return CommonResultBean.builder()
                .code(500)
                .success(false)
                .message("服务器内部错误")
                .data("服务器内部错误")
                .build();

    }
}
