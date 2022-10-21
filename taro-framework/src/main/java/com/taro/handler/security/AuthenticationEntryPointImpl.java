package com.taro.handler.security;

import com.alibaba.fastjson.JSON;
import com.taro.domain.ResponseResult;
import com.taro.enums.AppHttpCodeEnum;
import com.taro.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ClassName AuthenticationEntryPointImpl
 * Author taro
 * 认证失败处理器
 * Date 2022/10/15 15:49
 * Version 1.0
 */

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        e.printStackTrace();

        ResponseResult responseResult = null;
        if(e instanceof BadCredentialsException) {
            responseResult = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(), AppHttpCodeEnum.LOGIN_ERROR.getMsg());
        }else if(e instanceof InsufficientAuthenticationException){
            responseResult = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN.getCode(), AppHttpCodeEnum.NEED_LOGIN.getMsg());
        }else{
            responseResult = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), "认证或授权错误");
        }
        //响应给前端
        WebUtils.renderString(response, JSON.toJSONString(responseResult));
    }
}
