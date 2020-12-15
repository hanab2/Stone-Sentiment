package com.stone.spider.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "store_url")
public class StoreURL {

    @TableId(value = "id",type = IdType.INPUT)
    private Long id;
    private String url;
    private LocalDateTime embodyTime;
    @TableLogic(value = "0" , delval = "1")
    private Integer statusCode;
}
