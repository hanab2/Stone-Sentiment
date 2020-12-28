package com.stone.sentiment.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResultBean implements Serializable {
    private Long total;
    private Object data;
    private static final long serialVersionUID = 2138646522537952942L;
}
