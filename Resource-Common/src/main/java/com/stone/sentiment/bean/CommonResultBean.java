package com.stone.sentiment.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommonResultBean implements Serializable {
    private static final long serialVersionUID = -998779006096862666L;
    private Integer code;
    private String message;
    private Boolean success;
    private Object data;
}
