package com.taro.service;

import com.taro.domain.ResponseResult;
import com.taro.domain.entity.User;


public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
