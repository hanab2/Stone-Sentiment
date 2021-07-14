package com.stone.sentiment.config;

import com.stone.sentiment.pump.Pump;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

@SpringBootConfiguration
@EnableScheduling
public class PumpScheduleConfig {
//
    private static final Logger log = LoggerFactory.getLogger(PumpScheduleConfig.class);

    @Resource
    Pump pump;

    @Scheduled(cron = "*/5 * * * * ?")
    private void schedulePumpData() {
        log.debug("schedule pump~");
        pump.pump();
    }
}
