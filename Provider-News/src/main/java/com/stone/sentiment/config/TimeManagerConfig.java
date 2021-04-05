package com.stone.sentiment.config;

import com.stone.sentiment.manager.TimeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@SpringBootConfiguration
@EnableScheduling
public class TimeManagerConfig {
    private static final Logger log = LoggerFactory.getLogger(TimeManagerConfig.class);

    @Resource
    TimeManager timeManager;

    @Scheduled(cron = "0 0 0 * * ?")
    private void changeTimeFloor() {
        log.info("change timeFloor");
        timeManager.updateMinimumDayFloor(LocalDateTime.now().minusDays(30).toInstant(ZoneOffset.of("+8")).toEpochMilli());
    }
}
