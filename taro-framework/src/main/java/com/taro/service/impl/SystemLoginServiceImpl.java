package com.taro.service.impl;

import com.taro.domain.ResponseResult;
import com.taro.domain.entity.LoginUser;
import com.taro.domain.entity.User;
import com.taro.domain.vo.BlogUserLoginVo;
import com.taro.domain.vo.UserInfoVo;
import com.taro.enums.AppHttpCodeEnum;
import com.taro.exception.SystemException;
import com.taro.service.LoginService;
import com.taro.utils.BeanCopyUtil;
import com.taro.utils.JwtUtil;
import com.taro.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName BlogLoginServiceImpl
 * Author taro
 * Date 2022/10/15 8:52
 * Version 1.0
 */

@Service
public class SystemLoginServiceImpl implements LoginService {

    //AuthenticationManager 来完成认证，需要手动注入 ioc 才能装配
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        //AuthenticationManager 调用的是 UserDetailService 中的方法实现验证
        //UserDetailService 默认去内存查询
        //需要重新创建一个 UserDetailService 的实现类，将默认去内存查询更改为数据库查询
        Authentication authenticate = null;
        try {
            authenticate = authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {//捕获认证失败异常
            throw new SystemException(AppHttpCodeEnum.LOGIN_ERROR);
        }

        //获取 userid 生成 token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();//获取认证主体，就是 UserDetailsService 中返回的结果
        String userId = loginUser.getUser().getId().toString();
        //jwt 工具类生成 token
        String jwt = JwtUtil.createJWT(userId);

        //把用户信息存入 redis
        redisCache.setCacheObject("admin:" + userId, loginUser);

        Map<String,String> map = new HashMap<>();
        map.put("token",jwt);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult logout() {
        //获取 token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //token 通过 jwt 解析获取 userId
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();

        //删除 redis 中的用户信息
        redisCache.deleteObject("admin:" + userId);
        return ResponseResult.okResult();
    }
}
