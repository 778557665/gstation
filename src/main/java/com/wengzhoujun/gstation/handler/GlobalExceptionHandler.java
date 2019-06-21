package com.wengzhoujun.gstation.handler;

import com.wengzhoujun.gstation.utils.ResponseUtil;
import com.wengzhoujun.gstation.domain.Result;
import com.wengzhoujun.gstation.domain.exception.CommonException;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result jsonErrorHandler(Exception e) {
        String statusCode;
        String msg;
        if (e instanceof MissingServletRequestParameterException) {
            statusCode = "miss_parameter";
            msg = e.getMessage();
        } else if (e instanceof CommonException) {
            statusCode = ((CommonException) e).getErrorCode();
            if (StringUtils.isBlank(statusCode)) {
                statusCode = "common_exception";
            }
            msg = e.getMessage();
        } else if (e instanceof ConstraintViolationException || e instanceof BindException) {
            statusCode = "validated_error";
            msg = e.getMessage();
        } else {
            statusCode = "system_error";
            msg = "system error!";
        }
        e.printStackTrace();
        return ResponseUtil.createResult(false, statusCode, msg);
    }

}