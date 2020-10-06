package com.stone.sentiment.controller;

import com.stone.sentiment.bean.CommonResultBean;
import com.stone.sentiment.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    @GetMapping(value = {"/testUser"})
    public User test(){
        return User.builder().userId(2L).username("aafaf").build();
    }

    @GetMapping("/test")
    public CommonResultBean<User> test2(){
        CommonResultBean<User> commonResultBean = new CommonResultBean<>();
        commonResultBean.setData(User.builder().userId(2L).username("aafaf").build());
        commonResultBean.setCode(200);
        log.info("test被调用");
        return commonResultBean;
    }
}
