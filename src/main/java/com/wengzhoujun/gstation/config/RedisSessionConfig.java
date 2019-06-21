package com.wengzhoujun.gstation.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Created on 2019/6/21.
 *
 * @author WengZhoujun
 */
@Configuration
//maxInactiveIntervalInSeconds 默认是1800秒过期，这里测试修改为60秒
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60, redisNamespace = "gstation")
public class RedisSessionConfig{

}
