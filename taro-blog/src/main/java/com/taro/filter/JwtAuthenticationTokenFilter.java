package com.taro.filter;

import com.alibaba.fastjson.JSON;
import com.taro.domain.ResponseResult;
import com.taro.domain.entity.LoginUser;
import com.taro.enums.AppHttpCodeEnum;
import com.taro.utils.JwtUtil;
import com.taro.utils.RedisCache;
import com.taro.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * ClassName JwtAuthenticationFilter
 * Author taro
 * Date 2022/10/15 11:35
 * Version 1.0
 */

//配置 jwt 过滤器，协助完成 token 验证
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //从请求头中获取 token
        String token = request.getHeader("token");
        //没有 token 即不需要验证的直接放行
        if(!StringUtils.hasText(token)){
            filterChain.doFilter(request, response);
            return;
        }

        //通过 jwt 解析 token
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            //token 超时/非法
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            //filter 并不归 spring mvc 管理，所以抛出异常返回的结果并不会被前端所处理
            //需要自己将结果转换为 json，来转发
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }

        //从解析数据中获取 userId
        String userId = claims.getSubject();
        //从 redis 中获取信息
        LoginUser loginUser = redisCache.getCacheObject("bloglogin:" + userId);

        if(Objects.isNull(loginUser)) {//登录过期
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }

        //验证通过存入 SecurityContextHolder security 后续认证使用的是该域中的信息
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
