package com.stone.sentiment.api;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private Integer code;
    private T data;
}
