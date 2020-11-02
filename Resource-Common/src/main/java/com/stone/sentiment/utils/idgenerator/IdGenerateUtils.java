package com.stone.sentiment.utils.idgenerator;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

public class IdGenerateUtils {

    public static Snowflake getSnowFlakeIdGenerator(long workerId, long datacenterId){
        return IdUtil.getSnowflake(workerId, datacenterId);
    }
}
