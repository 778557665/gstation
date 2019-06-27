package com.wengzhoujun.gstation.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created on 2019/6/21.
 *
 * @author WengZhoujun
 */
@Data
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
}
