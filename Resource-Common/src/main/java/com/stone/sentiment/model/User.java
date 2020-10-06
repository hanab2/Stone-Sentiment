package com.stone.sentiment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 4795828894458200320L;
    private Long userId;
    private String username;
    private String password;
    private String avatarPath;
    private String email;
    private Integer status;
}
