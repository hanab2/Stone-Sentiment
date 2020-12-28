package com.stone.sentiment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment implements Serializable {
    private static final long serialVersionUID = -7873227676541640425L;
    private Long commentId;
    private Long commentatorId;
    private Long articleId;
    private String commentBody;
    private Integer status;
    private Date createTime;
}
