package com.taro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taro.domain.entity.User;
import com.taro.mapper.UserMapper;
import com.taro.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2022-10-14 19:41:05
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}

