package com.stone.sentiment.mapper;

import cn.hutool.core.lang.Snowflake;
import com.stone.sentiment.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class TestUserMapper {

    @Resource
    UserMapper userMapper;

    @Resource
    Snowflake snowflake;

    @Test
    public void testAdd(){
        System.out.println(snowflake.nextId()+"=====");
        User user = User.builder()
                .userId(snowflake.nextId())
                .username(snowflake.nextIdStr())
                .password(snowflake.nextIdStr())
                .avatarPath("null")
                .email("null")
                .status(0)
                .build();
        int insert = userMapper.insert(user);
        System.out.println(insert);
    }

    @Test
    public  void testDelete(){
        int delete = userMapper.deleteById(1324620563360124929L);
        System.out.println(delete);
    }
}
