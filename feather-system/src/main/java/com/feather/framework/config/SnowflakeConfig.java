package com.feather.framework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.feather.common.config.UidWorker;
import com.feather.framework.util.Snowflake;

@Configuration
public class SnowflakeConfig {

    // 最多5位
    @Value("${feather.snowflake.datacenterId}")
    private long datacenterId;

    // 最多5位
    @Value("${feather.snowflake.workerId}")
    private long workerId;

    /**
     * 使用Snowflake实现UidWorker
     */
    @Bean
    public UidWorker getUidWorker() {
        return new UidWorker() {
            Snowflake snowflake = new Snowflake(workerId, datacenterId);

            @Override
            public long getNextId() {
                return snowflake.nextId();
            }
        };
    }
}
