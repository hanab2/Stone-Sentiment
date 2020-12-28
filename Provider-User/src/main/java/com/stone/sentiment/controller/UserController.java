package com.stone.sentiment.controller;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stone.sentiment.bean.CommonResultBean;
import com.stone.sentiment.bean.PageResultBean;
import com.stone.sentiment.model.User;
import com.stone.sentiment.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping(value = {"user"})
public class UserController {

    @Resource
    UserService userService;

    @Resource
    Snowflake snowflake;

    @GetMapping(value = {"/testUser"})
    public User test() {
        return User.builder().userId(2L).username("aafaf").build();
    }

    @GetMapping("/test")
    public CommonResultBean test2() {
        CommonResultBean commonResultBean = new CommonResultBean();
        commonResultBean.setData(User.builder().userId(2L).username("aafaf").build());
        commonResultBean.setCode(200);
        log.info("test被调用");
        return commonResultBean;
    }

    @GetMapping(value = {"/getOneById"})
    public CommonResultBean getOneById(@RequestBody Long userId) {
        User user = userService.getById(userId);
        if (user != null) {
            user.setPassword(null);
        }
        return CommonResultBean.builder()
                .isSuccessful(true)
                .code(200)
                .message("查询用户成功")
                .data(user)
                .build();
    }

    @GetMapping(value = {"/pageSearchByCondition"})
    public CommonResultBean pageSearchByCondition(@RequestParam(defaultValue = "0") int currentPageNumber, @RequestParam(defaultValue = "10") int pageSize, @RequestBody User userCondition) {
        IPage<User> userPage = userService.pageSearchByCondition(currentPageNumber, pageSize, userCondition);
        CommonResultBean commonResultBean = new CommonResultBean();
        PageResultBean pageResultBean = PageResultBean.builder()
                .total(userPage.getTotal())
                .data(userPage.getRecords())
                .build();
        commonResultBean.setData(pageResultBean);
        commonResultBean.setCode(200);
        commonResultBean.setIsSuccessful(true);
        return commonResultBean;
    }

    @PostMapping(value = {"/creatOne"})
    public CommonResultBean creatOne(@RequestBody User user) {

        user.setUserId(snowflake.nextId());
        //此处还没添加密码处理
        boolean flag = userService.save(user);
        if (flag) {
            return CommonResultBean.builder()
                    .code(200)
                    .isSuccessful(true)
                    .message("新增用户成功")
                    .data("新增用户成功")
                    .build();
        }
        return CommonResultBean.builder()
                .code(500)
                .isSuccessful(false)
                .message("新增用户失败")
                .data("新增用户失败")
                .build();
    }

    @DeleteMapping(value = {"/deleteOne"})
    public CommonResultBean deleteOneById(@RequestBody Long userId) {
        boolean flag = userService.removeById(userId);
        if (flag) {
            return CommonResultBean.builder()
                    .code(200)
                    .isSuccessful(true)
                    .message("删除用户成功")
                    .data("删除用户成功")
                    .build();
        }
        return CommonResultBean.builder()
                .code(500)
                .isSuccessful(false)
                .message("删除用户失败")
                .data("删除用户失败")
                .build();
    }

    @PutMapping(value = {"/updateOne"})
    public CommonResultBean updateOne(@RequestBody User user) {
        boolean flag = userService.saveOrUpdate(user);
        if (flag) {
            return CommonResultBean.builder()
                    .code(200)
                    .isSuccessful(true)
                    .message("修改用户成功")
                    .data("修改用户成功")
                    .build();
        }
        return CommonResultBean.builder()
                .code(500)
                .isSuccessful(false)
                .message("修改用户失败")
                .data("修改用户失败")
                .build();
    }

}
