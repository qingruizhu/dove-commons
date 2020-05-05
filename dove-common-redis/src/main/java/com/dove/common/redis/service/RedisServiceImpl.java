package com.dove.common.redis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


public class RedisServiceImpl implements RedisService {
    Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            LOGGER.error("【{}】存入redis失败", key, e);
            return false;
        }
    }

    @Override
    public boolean set(String key, Object value, long expire) {
        try {
            redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            LOGGER.error("【{}】存入redis失败", key, e);
            return false;
        }

    }

    @Override
    public boolean exist(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            LOGGER.error("判断redis中是否存在【{}】失败", key, e);
            return false;
        }

    }

    @Override
    public String get(String key) {
        try {
            return (String) this.getObj(key);
        } catch (Exception e) {
            LOGGER.error("从redis中获取【{}】失败", key, e);
            return null;
        }

    }

    @Override
    public Object getObj(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            LOGGER.error("从redis中获取【{}】失败", key, e);
            return null;
        }

    }

    @Override
    public boolean expire(String key, long expire) {
        try {
            return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        } catch (Exception e) {
            LOGGER.error("设置redis中【{}】的超时时间失败", key, e);
            return false;
        }

    }

    @Override
    public boolean del(String key) {
        try {
            return redisTemplate.delete(key);
        } catch (Exception e) {
            LOGGER.error("从redis中移除【{}】失败", key, e);
            return false;
        }

    }

    @Override
    public Long increment(String key, long delta) {
        try {
            return redisTemplate.opsForValue().increment(key, delta);
        } catch (Exception e) {
            LOGGER.error("redis中【{}】自增失败", key, e);
            return null;
        }

    }
}
