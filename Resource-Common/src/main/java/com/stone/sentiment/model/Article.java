package com.stone.sentiment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Article implements Serializable {
    private static final long serialVersionUID = 4280152393858198831L;
    private Long articleId;
    private Long authorId;
    private String title;
    private String body;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    private List<String> tagList;
}
