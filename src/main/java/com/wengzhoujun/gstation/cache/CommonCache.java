package com.wengzhoujun.gstation.cache;

import java.util.List;
import java.util.Map;

/**
 * Created on 2019/6/24.
 *
 * @author WengZhoujun
 */
public interface CommonCache<K, V> {

    /**
     * 设置缓存
     *
     * @param key
     * @param value
     */
    void put(K key, V value);

    /**
     * 设置缓存
     *
     * @param key
     * @param value
     * @param timeout 单位毫秒
     */
    void put(K key, V value, long timeout);

    /**
     * 获取缓存
     *
     * @param key
     * @param
     * @return
     */
    V get(K key);

    void increment(K key);

    void increment(K key, Long timeout);

    List<K> getAllKey();

    /**
     * 是否存在key
     *
     * @param key
     * @return
     */
    boolean hasKey(K key);

    void putValues(Map<K, V> map);

    Map<K, V> getValues(List<K> keys);

    /**
     * 移除缓存
     *
     * @param key
     * @param
     * @return
     */
    V remove(K key);

}
