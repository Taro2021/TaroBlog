package com.taro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.taro.constant.SystemConstants;
import com.taro.domain.entity.LoginUser;
import com.taro.domain.entity.User;
import com.taro.enums.AppHttpCodeEnum;
import com.taro.exception.SystemException;
import com.taro.mapper.SysMenuMapper;
import com.taro.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws SystemException {
        //根据用户名查询用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>();
        queryWrapper.eq(User::getUserName, username);
        User user = userMapper.selectOne(queryWrapper);

        //未查询到用户，抛出异常
        if(Objects.isNull(user)) throw new SystemException(AppHttpCodeEnum.LOGIN_ERROR);

        //返回用户信息
        //TODO 查询权限信息封装，后台用户需要做权限查询
        if(SystemConstants.SYSTEM_ADMIN.equals(user.getType())) {
            List<String> perms = sysMenuMapper.selectPermsByUserId(user.getId());
            return new LoginUser(user, perms);
        }

        return new LoginUser(user, null);
    }
}
