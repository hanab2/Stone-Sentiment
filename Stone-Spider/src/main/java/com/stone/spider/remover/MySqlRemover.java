package com.stone.spider.remover;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stone.spider.mapper.StoreUrlMapper;
import com.stone.spider.model.StoreUrl;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.component.DuplicateRemover;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Component
public class MySqlRemover implements DuplicateRemover {

    @Resource
    private StoreUrlMapper storeUrlMapper;
    @Resource
    Snowflake snowflake;

    @Override
    public boolean isDuplicate(Request request, Task task) {
        if (storeUrlMapper.selectCount(new QueryWrapper<StoreUrl>()
                .eq("url",request.getUrl())) > 0) {
            return true;
        }
        StoreUrl storeUrl = StoreUrl.builder()
                .id(snowflake.nextId())
                .embodyTime(LocalDateTime.now())
                .url(request.getUrl())
                .statusCode(0)
                .build();
        storeUrlMapper.insert(storeUrl);
        return false;
    }

    @Override
    public void resetDuplicateCheck(Task task) {
        storeUrlMapper.delete(new QueryWrapper<StoreUrl>()
                .eq("statusCode", 1));
    }

    @Override
    public int getTotalRequestsCount(Task task) {
        return storeUrlMapper.selectCount(new QueryWrapper<StoreUrl>()
                .eq("statusCode", 0));
    }
}
