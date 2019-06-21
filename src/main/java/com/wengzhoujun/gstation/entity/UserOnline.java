package com.wengzhoujun.gstation.entity;

import java.io.Serializable;

/**
 * Created on 2019/6/21.
 *
 * @author WengZhoujun
 */
public class UserOnline implements Serializable {

    private static final long serialVersionUID = 8186693854183938572L;

    private Long userId;

    private String sessionId;

    private Long createTime;

    public UserOnline(Long userId, String sessionId, Long createTime) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.createTime = createTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
