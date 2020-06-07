package com.dove.common.redis.service;

import java.util.Map;

/**
 * 对象和数组都以json形式进行存储
 */
public interface RedisService {

/**############################## string ###############################*/
    /**
     * 存储数据
     */
    Boolean set(String key, Object value);

    Boolean set(String key, Object value, long expire);

    /**
     * 是否存在key
     */
    Boolean exist(String key);

    /**
     * 获取数据
     */
    String get(String key);

    Object getObj(String key);

    /**
     * 设置超期时间
     */
    Boolean expire(String key, long expire);

    /**
     * 删除数据
     */
    Boolean del(String key);

    /**
     * 自增操作
     *
     * @param delta 自增步长
     */
    Long increment(String key, long delta);


    /**
     * ############################## hash ###############################
     */
    Object hGet(String key, String hashKey);

    Boolean hSet(String key, String hashKey, Object value, long time);

    Boolean hSet(String key, String hashKey, Object value);

    Map<Object, Object> hGetAll(String key);

    Boolean hSetAll(String key, Map<String, Object> map, long time);

    Boolean hSetAll(String key, Map<String, Object> map);

    Boolean hDel(String key, Object... hashKey);

    Boolean hHasKey(String key, String hashKey);

    Long hIncr(String key, String hashKey, Long delta);

    Long hDecr(String key, String hashKey, Long delta);


}
