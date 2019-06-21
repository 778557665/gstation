package com.wengzhoujun.gstation.utils;

import com.alibaba.fastjson.JSON;
import com.wengzhoujun.gstation.domain.ErrorCode;
import com.wengzhoujun.gstation.domain.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletResponse;
import java.io.PrintWriter;

/**
 * Created on 2019/6/19.
 *
 * @author WengZhoujun
 */
public class ResponseUtil {

    public ResponseUtil() {
    }

    public static void out(ServletResponse response, Result result) {
        Logger log = LoggerFactory.getLogger(ResponseUtil.class);
        PrintWriter out = null;

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out = response.getWriter();
            out.println(JSON.toJSON(result));
        } catch (Exception var8) {
            log.error(var8 + "输出JSON出错");
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }

        }

    }

    public static <T> Result<T> createResult(boolean flag, String code, String msg, T data) {
        Result<T> result = new Result();
        result.setSuccess(flag);
        result.setCode(code);
        result.setMessage(msg);
        result.setTimestamp(System.currentTimeMillis());
        result.setResult(data);
        return result;
    }

    public static <T> Result<T> createResult(boolean flag, String code, String msg) {
        return createResult(flag, code, msg, (T) null);
    }

    public static <T> Result<T> createResult(boolean flag, String msg) {
        return flag ? createResult(true, "success", msg, (T) null) : createResult(false, "failed", msg, (T) null);
    }

    public static <T> Result<T> ok(String code, T data) {
        return createResult(true, code, "success", data);
    }

    public static <T> Result<T> ok(String code) {
        return createResult(true, code, "success");
    }

    public static <T> Result<T> ok(T data) {
        return createResult(true, "success", "success", data);
    }

    public static <T> Result<T> ok() {
        return createResult(true, "success", "success", (T) null);
    }

    public static <T> Result<T> error(String msg) {
        return createResult(false, "failed", msg);
    }

    public static <T> Result<T> error(String code, String msg) {
        return createResult(false, code, msg);
    }

    public static <T> Result<T> error(ErrorCode errorCode) {
        return createResult(false, errorCode.getCode(), errorCode.getMsg());
    }

}
