package com.stone.sentiment.controller;

import com.stone.sentiment.bean.CommonResultBean;
import com.stone.sentiment.model.User;
import com.stone.sentiment.service.ProviderUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {

    @Resource
    ProviderUserService providerUserService;

    @GetMapping("/test")
    CommonResultBean<User> test(){
        return providerUserService.test();
    }
}
