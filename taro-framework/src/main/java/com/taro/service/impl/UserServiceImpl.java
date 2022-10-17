package com.taro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taro.domain.ResponseResult;
import com.taro.domain.entity.LoginUser;
import com.taro.domain.entity.User;
import com.taro.domain.vo.UserInfoVo;
import com.taro.mapper.UserMapper;
import com.taro.service.UserService;
import com.taro.utils.BeanCopyUtil;
import com.taro.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2022-10-14 19:41:05
 */
@Service("userService")
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public ResponseResult getUserInfo() {
        //根据 token 获取 userId
        Long userId = SecurityUtils.getUserId();
        //根据 id 查询信息
        User user = super.getById(userId);
        UserInfoVo userInfoVo = BeanCopyUtil.copyBean(user, UserInfoVo.class);
        log.info("user: {}\n-------------------------", userInfoVo);
        return ResponseResult.okResult(userInfoVo);
    }
}

