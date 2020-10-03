package com.stone.sentiment.service;

import com.stone.sentiment.bean.CommonResultBean;
import com.stone.sentiment.model.User;
import com.stone.sentiment.service.impl.ProviderUserServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "Stone-Sentiment-Provider-User",fallback = ProviderUserServiceImpl.class)
public interface ProviderUserService {
    @GetMapping("/test")
    CommonResultBean<User> test();
}
