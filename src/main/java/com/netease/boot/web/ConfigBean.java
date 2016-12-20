package com.netease.boot.web;

import com.netease.boot.service.cache.RedisPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Michael Jiang on 16-12-20.
 */
@Configuration
public class ConfigBean {

    @Bean
    public RedisPoolConfig redisPoolConfig() {
        RedisPoolConfig redisPoolConfig = new RedisPoolConfig();
        redisPoolConfig.setHost("localhost");
        redisPoolConfig.setPort(6379);
        redisPoolConfig.setMaxTotal(100);
        redisPoolConfig.setMaxIdle(16);
        redisPoolConfig.setMaxWaitTime(3);
        redisPoolConfig.init();
        return redisPoolConfig;
    }
}
