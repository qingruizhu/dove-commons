package com.dove.common.redis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class RedisServiceImpl implements RedisService {
    Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * ############################## string ###############################
     */
    @Override
    public Boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            LOGGER.debug("【{}】存入redis", key);
            return true;
        } catch (Exception e) {
            LOGGER.error("【{}】存入redis失败", key, e);
            return false;
        }
    }

    @Override
    public Boolean set(String key, Object value, long expire) {
        try {
            redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
            LOGGER.debug("【{}】存入redis", key);
            return true;
        } catch (Exception e) {
            LOGGER.error("【{}】存入redis失败", key, e);
            return false;
        }
    }

    @Override
    public Boolean exist(String key) {
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
    public Boolean expire(String key, long expire) {
        try {
            return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        } catch (Exception e) {
            LOGGER.error("设置redis中【{}】的超时时间失败", key, e);
            return false;
        }

    }

    @Override
    public Boolean del(String key) {
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

    /**
     * ############################## hash ###############################
     */
    @Override
    public Object hGet(String key, String hashKey) {
        try {
        } catch (Exception e) {
            LOGGER.error("redis中【{}】自增失败", key, e);
            return null;
        }
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    @Override
    public Boolean hSet(String key, String hashKey, Object value, long time) {

        try {
        } catch (Exception e) {
            LOGGER.error("redis中【{}】自增失败", key, e);
            return null;
        }

        redisTemplate.opsForHash().put(key, hashKey, value);
        return expire(key, time);
    }

    @Override
    public Boolean hSet(String key, String hashKey, Object value) {
        try {
            redisTemplate.opsForHash().put(key, hashKey, value);
            return true;
        } catch (Exception e) {
            LOGGER.error("insert redis for hash【{}】of hashKey【{}】 error", key, hashKey, e);
            return false;
        }

    }

    @Override
    public Map<Object, Object> hGetAll(String key) {
        try {
            return redisTemplate.opsForHash().entries(key);
        } catch (Exception e) {
            LOGGER.error("get redis for hash【{}】error", key, e);
            return null;
        }
    }

    @Override
    public Boolean hSetAll(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return expire(key, time);
        } catch (Exception e) {
            LOGGER.error("insert all redis for hash【{}】 error", key, e);
            return false;
        }


    }

    @Override
    public Boolean hSetAll(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            LOGGER.error("insert all redis for hash【{}】 error", key, e);
            return false;
        }


    }

    @Override
    public Boolean hDel(String key, Object... hashKey) {
        try {
            redisTemplate.opsForHash().delete(key, hashKey);
            return true;
        } catch (Exception e) {
            LOGGER.error("delete redis for hash【{}】of hashKey【{}】 error", key, hashKey, e);
            return false;
        }


    }

    @Override
    public Boolean hHasKey(String key, String hashKey) {
        try {
            return redisTemplate.opsForHash().hasKey(key, hashKey);
        } catch (Exception e) {
            LOGGER.error("exist redis for hash【{}】of hashKey【{}】 error", key, hashKey, e);
            return false;
        }


    }

    @Override
    public Long hIncr(String key, String hashKey, Long delta) {
        try {
            return redisTemplate.opsForHash().increment(key, hashKey, delta);
        } catch (Exception e) {
            LOGGER.error("incr redis for hash【{}】of hashKey【{}】 error", key, hashKey, e);
            return null;
        }
    }

    @Override
    public Long hDecr(String key, String hashKey, Long delta) {
        try {
            return redisTemplate.opsForHash().increment(key, hashKey, -delta);
        } catch (Exception e) {
            LOGGER.error("decr redis for hash【{}】of hashKey【{}】 error", key, hashKey, e);
            return null;
        }
    }


}
