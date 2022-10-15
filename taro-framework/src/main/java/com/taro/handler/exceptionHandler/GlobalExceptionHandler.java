package com.taro.handler.exceptionHandler;

import com.taro.domain.ResponseResult;
import com.taro.enums.AppHttpCodeEnum;
import com.taro.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ClassName GlobalExceptionHandler
 * Author taro
 * Date 2022/10/15 16:36
 * Version 1.0
 */

//Controller 的增强
//@ControllerAdvice + @ResponseBody
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public ResponseResult sysExceptionHandler(SystemException e){
        log.error("异常: {}", e.getMsg());
        return ResponseResult.errorResult(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e){
        log.error("异常: ", e);
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), AppHttpCodeEnum.SYSTEM_ERROR.getMsg());
    }
}
