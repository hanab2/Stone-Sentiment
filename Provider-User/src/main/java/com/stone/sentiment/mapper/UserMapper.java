package com.stone.sentiment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stone.sentiment.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
