package com.wengzhoujun.gstation.cache;

import com.wengzhoujun.gstation.entity.UserOnline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created on 2019/6/21.
 *
 * @author WengZhoujun
 */
@Component
public class UserOnlineCache {

    private ValueOperations<String, UserOnline> userOnlineOperations;

    public UserOnlineCache(RedisTemplate oneRedisTemplate) {
        oneRedisTemplate.afterPropertiesSet();
        userOnlineOperations = oneRedisTemplate.opsForValue();
    }

    public String cacheKey(){
        return "UserOnline:Id:";
    }

    public void set(UserOnline userOnline){
        userOnlineOperations.set(cacheKey() + userOnline.getUserId(), userOnline, 600L, TimeUnit.SECONDS);
    }

    public UserOnline get(Long userId){
        return userOnlineOperations.get(cacheKey() + userId);
    }

    public Boolean delete(Long userId){
        return userOnlineOperations.getOperations().delete(cacheKey() + userId);
    }
}
