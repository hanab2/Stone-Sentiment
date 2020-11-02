package com.stone.sentiment.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.stone.sentiment.annotation.rdbms.Secret;
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
    @TableId(type = IdType.INPUT)
    private Long userId;
    private String username;
    @Secret
    private String password;
    private String avatarPath;
    private String email;
    @TableLogic(value = "0",delval = "1")
    private Integer status;
}
