package com.wengzhoujun.gstation.domain;

/**
 * Created on 2019/6/19.
 *
 * @author WengZhoujun
 */
public class Result<T> extends BaseDomain {

    private static final long serialVersionUID = 2144260646753508634L;

    private boolean success;
    private String message;
    private String code;
    private long timestamp = System.currentTimeMillis();
    private T result;

    public Result() {
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public T getResult() {
        return this.result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
