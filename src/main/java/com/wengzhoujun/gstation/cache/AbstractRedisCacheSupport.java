package com.wengzhoujun.gstation.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2019/6/24.
 *
 * @author WengZhoujun
 */
public abstract class AbstractRedisCacheSupport<K, V> implements CommonCache<K, V> {

    @Autowired
    private RedisTemplate redisTemplate;

    private long timeout = -1;

    private static final Set<Class<?>> PRIMITIVE_CLASSES = new HashSet<>();

    private Type keyType;
    private Type valueType;

    static {

        PRIMITIVE_CLASSES.add(boolean.class);
        PRIMITIVE_CLASSES.add(Boolean.class);

        PRIMITIVE_CLASSES.add(char.class);
        PRIMITIVE_CLASSES.add(Character.class);

        PRIMITIVE_CLASSES.add(byte.class);
        PRIMITIVE_CLASSES.add(Byte.class);

        PRIMITIVE_CLASSES.add(short.class);
        PRIMITIVE_CLASSES.add(Short.class);

        PRIMITIVE_CLASSES.add(int.class);
        PRIMITIVE_CLASSES.add(Integer.class);

        PRIMITIVE_CLASSES.add(long.class);
        PRIMITIVE_CLASSES.add(Long.class);

        PRIMITIVE_CLASSES.add(float.class);
        PRIMITIVE_CLASSES.add(Float.class);

        PRIMITIVE_CLASSES.add(double.class);
        PRIMITIVE_CLASSES.add(Double.class);

        PRIMITIVE_CLASSES.add(BigInteger.class);
        PRIMITIVE_CLASSES.add(BigDecimal.class);

        PRIMITIVE_CLASSES.add(String.class);
    }

    @PostConstruct
    public void init() {
        Type superClass = getClass().getGenericSuperclass();

        keyType = ((ParameterizedType) superClass).getActualTypeArguments()[0];
        valueType = ((ParameterizedType) superClass).getActualTypeArguments()[1];
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    @Override
    public void put(K key, V value) {
        put(key, value, timeout);
    }

    @Override
    public void put(K key, V value, long timeout) {
        String keyStr = getObjectStr(key);
        String valueStr = getObjectStr(value);
        getOps(keyStr).set(valueStr, timeout, TimeUnit.SECONDS);
    }

    private String getObjectStr(Object key) {
        if (key == null) {
            return null;
        }
        return JSON.toJSON(key).toString();
    }

    private BoundValueOperations getOps(String keyStr) {
        return redisTemplate.boundValueOps(getCacheKey(keyStr));
    }

    private String getCacheKey(String keyStr) {
        return getCacheName() + ":" + keyStr;
    }

    protected abstract String getCacheName();

    @Override
    public V get(K key) {
        String keyStr = getObjectStr(key);
        Object valueStr = getOps(keyStr).get();
        if (valueStr == null) {
            return null;
        }
        //如果是基本类型的则直接返回
        if (valueType instanceof Class) {
            Class valueClass = (Class) valueType;
            if (PRIMITIVE_CLASSES.contains(valueClass)) {
                try {
                    Object result = getObjectValue(valueStr.toString(), valueClass);
                    return (V) result;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
        Object value = JSON.parseObject(valueStr.toString(), valueType, Feature.SortFeidFastMatch);
        return (V) value;
    }

    @Override
    public void increment(K key) {
        increment(key, null);
    }

    @Override
    public void increment(K key, Long timeout) {
        String keyStr = getObjectStr(key);
        BoundValueOperations ops = getOps(keyStr);
        ops.increment(1);
        if (timeout != null && timeout > 0) {
            ops.expire(timeout, TimeUnit.SECONDS);
        }
    }

    @Override
    public List<K> getAllKey() {
        Set<String> keys = redisTemplate.keys(getCacheName() + ":*");
        if (ObjectUtils.isEmpty(keys)) {
            return Collections.emptyList();
        }
        return getKeysList(keys);
    }

    private List<K> getKeysList(Set<String> keys) {
        if (ObjectUtils.isEmpty(keys)) {
            return Lists.newArrayList();
        }
        Iterator<String> it = keys.iterator();
        List<K> list = new ArrayList<>();
        while (it.hasNext()) {
            String next = it.next();
            String keyStr = next.split(":", 2)[1];
            //如果是基本类型的则直接返回
            if (keyType instanceof Class) {
                Class keyClass = (Class) keyType;
                if (PRIMITIVE_CLASSES.contains(keyClass)) {
                    try {
                        Object result = getObjectValue(keyStr, keyClass);
                        list.add((K) result);
                        continue;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            Object result = JSON.parseObject(keyStr, keyType, Feature.SortFeidFastMatch);
            list.add((K) result);
        }
        return list;
    }

    private Object getObjectValue(String keyStr, Class keyClass) {
        Method method = null;
        try {
            method = keyClass.getDeclaredMethod("valueOf", Object.class);
        } catch (NoSuchMethodException e) {
            try {
                method = keyClass.getDeclaredMethod("valueOf", String.class);
            } catch (NoSuchMethodException e1) {
                throw new RuntimeException(e1);
            }
        }
        try {
            return method.invoke(null, keyStr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean hasKey(K key) {
        String keyStr = getObjectStr(key);
        return getOps(keyStr).size() > 0;
    }

    @Override
    public void putValues(Map<K, V> map) {
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        Map<String, String> set = new HashMap<>();
        while (it.hasNext()) {
            Map.Entry<K, V> entry = it.next();
            String keyStr = getObjectStr(entry.getKey());
            String valueStr = getObjectStr(entry.getValue());
            set.put(getCacheKey(keyStr), valueStr);
        }
        redisTemplate.opsForValue().multiSet(set);
    }

    @Override
    public Map<K, V> getValues(List<K> keys) {
        List<String> multiGetSet = new ArrayList<>();
        for (K key : keys) {
            String keyStr = getObjectStr(key);
            multiGetSet.add(getCacheKey(keyStr));
        }
        List<String> list = redisTemplate.opsForValue().multiGet(multiGetSet);
        Map<K, V> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            String valueStr = list.get(i);
            K key = keys.get(i);
            if (valueStr == null) {
                map.put(key, null);
            } else {
                //如果是基本类型的则直接返回
                Object result = null;
                if (valueType instanceof Class) {
                    Class valueClass = (Class) valueType;
                    if (PRIMITIVE_CLASSES.contains(valueClass)) {
                        try {
                            result = getObjectValue(valueStr, valueClass);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    result = JSON.parseObject(valueStr, valueType, Feature.SortFeidFastMatch);
                }
                map.put(key, (V) result);
            }
        }
        return map;
    }

    @Override
    public V remove(K key) {
        V V = get(key);
        if (V == null) {
            return null;
        }
        String keyStr = getObjectStr(key);
        redisTemplate.delete(getCacheKey(keyStr));
        return V;
    }
}
