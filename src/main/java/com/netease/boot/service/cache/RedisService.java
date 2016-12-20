package com.netease.boot.service.cache;

import java.io.Serializable;
import java.util.List;

/**
 * Redis 通用服务
 * Created by Michael Jiang on 15/8/4.
 */
public interface RedisService {
    /**
     * 向Redis缓存中存值，默认永久有效
     *
     * @param key     key
     * @param content value
     */
    void put(String key, Serializable content);

    /**
     * 向Redis缓存中存值，带超时时间
     *
     * @param key        key
     * @param content    value
     * @param expireTime 超时时间，单位：秒
     */
    void put(String key, Serializable content, Integer expireTime);

    /**
     * 从Redis中获取值
     *
     * @param key key
     * @return 缓存值
     */
    Object get(String key);

    /**
     * 删除指定key值
     *
     * @param key key
     */
    void delete(String key);

    <T extends Serializable> void lpush(String key, T element);

    <T extends Serializable> T lpop(String key, Class<T> clazz);

    <T extends Serializable> List<T> lrange(String key, long start, long end);

    void ltrim(String key, long start, long end);

    long incrAndExpire(String key, int expireTime);
}
