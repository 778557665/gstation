package com.wengzhoujun.gstation.cache;

import org.springframework.stereotype.Component;

/**
 * Created on 2019/6/24.
 *
 * @author WengZhoujun
 */
@Component
public class UserSessionExpiredCache extends AbstractRedisCacheSupport<String, String> {

    @Override
    protected String getCacheName() {
        return "UserSessionExpiredCache";
    }
}
