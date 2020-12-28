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
public class AccessLog implements Serializable {
    private static final long serialVersionUID = -768630625195233832L;
    private Long accessLogId;
    private Long visitorId;
    private String visitorAgent;
    private String visitorOperationSystem;
    private String requestUrl;
    private String requestParameterJsonString;
    private Date accessTime;
}
