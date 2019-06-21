package com.wengzhoujun.gstation.domain.exception;

/**
 * Created on 2019/6/19.
 *
 * @author WengZhoujun
 */
public class CommonException extends RuntimeException  {

    private static final long serialVersionUID = -3663227970852022038L;

    private String errorCode;

    public CommonException(String message) {
        super(message);
    }

    public CommonException(String errorCode, String message) {
        this(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
