package com.dove.common.redis.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
     *  以 pattern* 的所有key
     */
    Set<String> keysLike(String pattern);

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

    /**
     * ############################## list ###############################
     */
    /**
     * 获取List结构中的属性
     */
    List<Object> lRange(String key, long start, long end);

    /**
     * 获取List结构的长度
     */
    Long lSize(String key);

    /**
     * 根据索引获取List中的属性
     */
    Object lIndex(String key, long index);

    /**
     * 向List结构中添加属性
     */
    Long lPush(String key, Object value);

    /**
     * 向List结构中添加属性
     */
    Long lPush(String key, Object value, long time);

    /**
     * 向List结构中批量添加属性
     */
    Long lPushAll(String key, Object... values);

    /**
     * 向List结构中批量添加属性
     */
    Long lPushAll(String key, Long time, Object... values);


    /**
     * 从list获取元素(默认从左边pop)
     */
    Object lPop(String key);

    /**
     * 删除列表中值为value的元素，总共删除count次；
     * <p>
     * 如原来列表为 【1， 2， 3， 4， 5， 2， 1， 2， 5】
     * 传入参数 value=2, count=1 表示删除一个列表中value为2的元素
     * 则执行后，列表为 【1， 3， 4， 5， 2， 1， 2， 5】
     *
     * @param key
     * @param count
     * @param value
     */
    Long lRemove(String key, long count, Object value);

    /**
     * 删除list首尾，只保留 [start, end] 之间的值
     *
     * @param key
     * @param start
     * @param end
     */
    Boolean trim(String key, Integer start, Integer end);
}
