package com.stone.sentiment.config;

import cn.hutool.core.lang.Snowflake;
import com.stone.sentiment.utils.idgenerator.IdGenerateUtils;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
@ConfigurationProperties(prefix = "snowflake")
public class IdGeneratorConfig {

    private long workerId;
    private long datacenterId;

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }

    public void setDatacenterId(long datacenterId) {
        this.datacenterId = datacenterId;
    }

    @Bean
    public Snowflake snowflake(){
        return IdGenerateUtils.getSnowFlakeIdGenerator(workerId, datacenterId);
    }
}
