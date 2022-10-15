package com.taro.service.impl;

import com.taro.domain.ResponseResult;
import com.taro.domain.entity.LoginUser;
import com.taro.domain.entity.User;
import com.taro.domain.vo.BlogUserLoginVo;
import com.taro.domain.vo.UserInfoVo;
import com.taro.service.BlogLoginService;
import com.taro.utils.BeanCopyUtil;
import com.taro.utils.JwtUtil;
import com.taro.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * ClassName BlogLoginServiceImpl
 * Author taro
 * Date 2022/10/15 8:52
 * Version 1.0
 */

@Service
public class BlogLoginServiceImpl implements BlogLoginService {

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
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }

        //获取 userid 生成 token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();//获取认证主体，就是 UserDetailsService 中返回的结果
        Long userId = loginUser.getUser().getId();
        //jwt 工具类生成 token
        String jwt = JwtUtil.createJWT(userId.toString());

        //把用户信息存入 redis
        redisCache.setCacheObject("bolglogin:"+userId, loginUser);

        //把 token 和 userinfo 封装返回
        UserInfoVo userInfoVo = BeanCopyUtil.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo vo = new BlogUserLoginVo(jwt, userInfoVo);
        return ResponseResult.okResult(vo);
    }
}
