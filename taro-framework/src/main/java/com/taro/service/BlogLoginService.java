package com.taro.service;

import com.taro.domain.ResponseResult;
import com.taro.domain.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
