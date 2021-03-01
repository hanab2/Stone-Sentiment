package com.stone.sentiment.service.impl;

import com.stone.sentiment.bean.CommonResultBean;
import com.stone.sentiment.model.User;
import com.stone.sentiment.service.ProviderUserService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class ProviderUserServiceImpl implements ProviderUserService {
    @Override
    public CommonResultBean test() {
        System.out.println("rpc");
        return new CommonResultBean(4444,"降级",false,null);
    }
}
