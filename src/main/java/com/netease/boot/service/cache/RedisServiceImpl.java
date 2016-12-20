package com.netease.boot.service.cache;


import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Redis通用服务实现类
 * Created by Michael Jiang on 15/8/4.
 */
@Service
public class RedisServiceImpl implements RedisService {
    @Resource
    private RedisPoolConfig redisPoolConfig;

    private static Logger LOG = LoggerFactory.getLogger(RedisService.class);

    private final static String ENCODING = "UTF-8";

    @Override
    public void put(String key, Serializable content) throws RedisException {
        Jedis jedis = null;
        try {
            jedis = redisPoolConfig.getJedis();
            byte[] contentBytes = SerializationUtils.serialize(content);
            jedis.set(key.getBytes(ENCODING), contentBytes);
        } catch (Exception e) {
            LOG.error("Put error:{}.", e.getMessage(), e);
            throw new RedisException(e);
        } finally {
            if (jedis != null) {
                redisPoolConfig.releaseJedis(jedis);
            }
        }
    }

    @Override
    public void put(String key, Serializable content, Integer expireTime) throws RedisException {
        Jedis jedis = null;
        try {
            jedis = redisPoolConfig.getJedis();
            byte[] contentBytes = SerializationUtils.serialize(content);
            jedis.psetex(key.getBytes(ENCODING), expireTime * 1000, contentBytes);
        } catch (Exception e) {
            LOG.error("Put error:{}.", e.getMessage(), e);
            throw new RedisException(e);
        } finally {
            if (jedis != null) {
                redisPoolConfig.releaseJedis(jedis);
            }
        }
    }

    @Override
    public Object get(String key) throws RedisException {
        Jedis jedis = null;
        try {
            jedis = redisPoolConfig.getJedis();
            byte[] valueBytes = jedis.get(key.getBytes(ENCODING));
            if (valueBytes == null || valueBytes.length == 0) {
                return null;
            }
            Object o = SerializationUtils.deserialize(valueBytes);
            return o;
        } catch (Exception e) {
            LOG.error("Get error:{}.", e.getMessage(), e);
            throw new RedisException(e);
        } finally {
            if (jedis != null) {
                redisPoolConfig.releaseJedis(jedis);
            }
        }
    }

    @Override
    public <T extends Serializable> void lpush(String key, T element) throws RedisException {
        Jedis jedis = null;
        try {
            jedis = redisPoolConfig.getJedis();
            byte[] contentBytes = SerializationUtils.serialize(element);
            jedis.lpush(key.getBytes(ENCODING), contentBytes);
        } catch (Exception e) {
            LOG.error("lpush error:{}.", e.getMessage(), e);
            throw new RedisException(e);
        } finally {
            if (jedis != null) {
                redisPoolConfig.releaseJedis(jedis);
            }
        }
    }

    @Override
    public <T extends Serializable> T lpop(String key, Class<T> clazz) throws RedisException {
        Jedis jedis = null;
        try {
            jedis = redisPoolConfig.getJedis();
            byte[] valueBytes = jedis.lpop(key.getBytes(ENCODING));
            if (valueBytes == null || valueBytes.length == 0) {
                return null;
            }
            return SerializationUtils.deserialize(valueBytes);
        } catch (Exception e) {
            LOG.error("lpop error:{}.", e.getMessage(), e);
            throw new RedisException(e);
        } finally {
            if (jedis != null) {
                redisPoolConfig.releaseJedis(jedis);
            }
        }
    }

    @Override
    public <T extends Serializable> List<T> lrange(String key, long start, long end) throws RedisException {
        Jedis jedis = null;
        try {
            jedis = redisPoolConfig.getJedis();
            List<byte[]> elementValues = jedis.lrange(key.getBytes(ENCODING), start, end);
            List<T> elements = new ArrayList<>();
            for (byte[] elementBytes : elementValues) {
                T element = SerializationUtils.deserialize(elementBytes);
                elements.add(element);
            }
            return elements;
        } catch (Exception e) {
            LOG.error("lrange error:{}.", e.getMessage(), e);
            throw new RedisException(e);
        } finally {
            if (jedis != null) {
                redisPoolConfig.releaseJedis(jedis);
            }
        }
    }

    @Override
    public void ltrim(String key, long start, long end) throws RedisException {
        Jedis jedis = null;
        try {
            jedis = redisPoolConfig.getJedis();
            jedis.ltrim(key.getBytes(ENCODING), start, end);
        } catch (Exception e) {
            LOG.error("ltrim error:{}.", e.getMessage(), e);
            throw new RedisException(e);
        } finally {
            if (jedis != null) {
                redisPoolConfig.releaseJedis(jedis);
            }
        }
    }

    @Override
    public long incrAndExpire(String key, int expireTime) {
        Jedis jedis = null;
        Transaction tx;
        try {
            jedis = redisPoolConfig.getJedis();
            String result = jedis.get(key);
            if (result != null) {
                return jedis.incr(key);
            } else {
                jedis.watch(key);
                tx = jedis.multi();
                tx.incr(key);
                tx.expire(key, expireTime);
                List<Object> execResults = tx.exec();
                if (execResults != null && execResults.size() > 0) {
                    return (long) execResults.get(0);
                }
                throw new RuntimeException("incrAndExpire error");
            }
        } catch (Exception e) {
            LOG.error("incrAndExpire error:{}.", e.getMessage(), e);
            throw new RedisException(e);
        } finally {
            if (jedis != null) {
                redisPoolConfig.releaseJedis(jedis);
            }
        }
    }


    @Override
    public void delete(String key) throws RedisException {
        Jedis jedis = null;
        try {
            jedis = redisPoolConfig.getJedis();
            jedis.del(key);
        } catch (Exception e) {
            LOG.error("Get error:{}.", e.getMessage(), e);
            throw new RedisException(e);
        } finally {
            if (jedis != null) {
                redisPoolConfig.releaseJedis(jedis);
            }
        }
    }
}
