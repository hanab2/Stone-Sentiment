package com.stone.sentiment.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.stone.sentiment.model.User;

public interface UserService extends IService<User> {

    public IPage<User> pageSearchByCondition(int currentPageNumber, int pageSize, User userCondition);
}
