package com.wengzhoujun.gstation.listener;

import com.wengzhoujun.gstation.cache.UserOnlineCache;
import com.wengzhoujun.gstation.entity.RequestKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created on 2019/6/21.
 *
 * @author WengZhoujun
 */
@WebListener
public class SessionListener implements HttpSessionListener {

    static Logger logger = LoggerFactory.getLogger(SessionListener.class);


    @Autowired
    private UserOnlineCache userOnlineCache;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        logger.info("--------sessionCreated--------");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        logger.info("--------sessionDestroyed begin--------");
        HttpSession session = httpSessionEvent.getSession();
        Long userId = (Long) session.getAttribute(RequestKeys.SESSION_UID);
        if(userOnlineCache.delete(userId)){
            logger.info("userId:" + userId + "login out");
        }else {
            logger.info("userId:" + userId + " can't login out");
        }
    }
}
