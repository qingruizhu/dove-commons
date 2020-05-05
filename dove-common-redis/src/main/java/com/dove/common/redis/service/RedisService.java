package com.dove.common.redis.service;

/**
 * 对象和数组都以json形式进行存储
 */
public interface RedisService {
    /**
     * 存储数据
     */
    boolean set(String key, Object value);

    boolean set(String key, Object value, long expire);

    /**
     * 是否存在key
     */
    boolean exist(String key);

    /**
     * 获取数据
     */
    String get(String key);

    Object getObj(String key);

    /**
     * 设置超期时间
     */
    boolean expire(String key, long expire);

    /**
     * 删除数据
     */
    boolean del(String key);

    /**
     * 自增操作
     *
     * @param delta 自增步长
     */
    Long increment(String key, long delta);

}
