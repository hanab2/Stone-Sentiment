package com.stone.sentiment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stone.sentiment.mapper.UserMapper;
import com.stone.sentiment.model.User;
import com.stone.sentiment.service.UserService;
import com.stone.sentiment.utils.mybatisplus.MybatisPlusUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;


    @Override
    public IPage<User> pageSearchByCondition(int currentPageNumber, int pageSize, User userCondition) {
        Page<User> page = new Page<>(currentPageNumber, pageSize);
        QueryWrapper<User> userQueryWrapper = MybatisPlusUtils.getQueryWrapperWithConditionCoverBySecret(userCondition);
        return userMapper.selectPage(page, userQueryWrapper);
    }
}
