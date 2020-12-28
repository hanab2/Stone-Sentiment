package com.stone.spider.scheduler;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stone.spider.mapper.StoreUrlMapper;
import com.stone.spider.model.StoreUrl;
import com.stone.spider.remover.MySqlRemover;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.DuplicateRemovedScheduler;
import us.codecraft.webmagic.scheduler.MonitorableScheduler;
import us.codecraft.webmagic.scheduler.component.DuplicateRemover;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Component
public class MySqlDuplicateRemovedScheduler extends DuplicateRemovedScheduler implements MonitorableScheduler, DuplicateRemover {

    @Resource
    private MySqlRemover mySqlRemover;
    @Resource
    private Snowflake snowflake;
    @Resource
    private StoreUrlMapper storeUrlMapper;

    @Override
    public DuplicateRemover getDuplicateRemover() {
        return super.getDuplicateRemover();
    }

    @Override
    public DuplicateRemovedScheduler setDuplicateRemover(DuplicateRemover duplicatedRemover) {
        return super.setDuplicateRemover(duplicatedRemover);
    }

    @Override
    public void push(Request request, Task task) {
        //super.push(request, task);
        if (shouldReserved(request) || noNeedToRemoveDuplicate(request) || !this.mySqlRemover.isDuplicate(request, task)) {
            pushWhenNoDuplicate(request, task);
        }
    }

    @Override
    public Request poll(Task task) {
        StoreUrl storeUrl = storeUrlMapper.selectOne(new QueryWrapper<StoreUrl>()
                .select("id","url")
                .orderByAsc("embodyTime").last("limit 1"));
        return new Request(storeUrl.getUrl());
    }

    @Override
    protected boolean shouldReserved(Request request) {
        return super.shouldReserved(request);
    }

    @Override
    protected boolean noNeedToRemoveDuplicate(Request request) {
        return super.noNeedToRemoveDuplicate(request);
    }

    @Override
    protected void pushWhenNoDuplicate(Request request, Task task) {
//        try {
//            StoreUrl storeUrl = StoreUrl.builder()
//                    .id(snowflake.nextId())
//                    .embodyTime(LocalDateTime.now())
//                    .url(request.getUrl())
//                    .statusCode(0)
//                    .build();
//            storeUrlMapper.insert(storeUrl);
//        }catch (Throwable e){
//            System.out.println(e.getMessage());
//        }
    }

    @Override
    public int getLeftRequestsCount(Task task) {
        return mySqlRemover.getTotalRequestsCount(task);
    }

    @Override
    public boolean isDuplicate(Request request, Task task) {
        return mySqlRemover.isDuplicate(request, task);
    }

    @Override
    public void resetDuplicateCheck(Task task) {
        mySqlRemover.resetDuplicateCheck(task);
    }

    @Override
    public int getTotalRequestsCount(Task task) {
        return mySqlRemover.getTotalRequestsCount(task);
    }
}
