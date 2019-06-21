package com.wengzhoujun.gstation.domain;

/**
 * Created on 2019/6/20.
 *
 * @author WengZhoujun
 */
public enum ErrorCode {

    USER_NOT_PRESENCE("user_not_presence", "用户不存在"),
    WRONG_PHONE_OR_WRONG_PASSWORD("wrong_phone_or_wrong_password", "手机或密码错误");

    private String code;
    private String msg;

    ErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
