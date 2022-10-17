package com.taro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.taro.domain.entity.LoginUser;
import com.taro.domain.entity.User;
import com.taro.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

/**
 * ClassName UserDetailServiceImpl
 * Author taro
 * Date 2022/10/15 9:11
 * Version 1.0
 */

//创建一个 UserDetailsService 的实现类修改为去数据库查询
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    //和 UserService 查的是同一张表，所以直接使用 UserMapper 查询
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>();
        queryWrapper.eq(User::getUserName, username);
        User user = userMapper.selectOne(queryWrapper);

        //未查询到用户，抛出异常
        if(Objects.isNull(user)) throw new UsernameNotFoundException("未注册用户");

        //返回用户信息
        //TODO 查询权限信息封装

        return new LoginUser(user);
    }
}