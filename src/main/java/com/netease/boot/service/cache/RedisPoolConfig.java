package com.netease.boot.service.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.TimeUnit;

/**
 * Redis Pool Config
 * Created by jiangwenkang on 15/8/4.
 */
public class RedisPoolConfig implements ApplicationListener<ContextClosedEvent> {
    private JedisPool jedisPool;
    private String host;
    private Integer port;
    private String password;
    private int maxTotal;
    private int maxIdle;
    private int maxWaitTime;

    private static Logger logger = LoggerFactory.getLogger(RedisPoolConfig.class);
    private final static Integer maxConnectTime = 3000;

    public void init() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);    //设置最大连接数为128
        config.setMaxIdle(maxIdle);  //设置最大空闲连接数为16
        config.setMaxWaitMillis(TimeUnit.SECONDS.toMillis(maxWaitTime));     //设置最大等待时间为10S
        jedisPool = new JedisPool(config, host, port, maxConnectTime, password);
        logger.info("Redis pool initialize completed.");
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public void setMaxWaitTime(int maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    public Jedis getJedis() {
        return jedisPool.getResource();
    }

    public void releaseJedis(Jedis jedis) {
        jedisPool.returnResourceObject(jedis);
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        if (jedisPool != null) {
            jedisPool.destroy();
        }
    }
}
