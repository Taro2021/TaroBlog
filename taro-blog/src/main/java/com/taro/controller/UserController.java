package com.taro.controller;

import com.taro.domain.ResponseResult;
import com.taro.domain.entity.User;
import com.taro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName UserContriller
 * Author taro
 * Date 2022/10/17 15:47
 * Version 1.0
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    public ResponseResult getUserInfo() {
        return userService.getUserInfo();
    }

    @PutMapping("/userInfo")
    public ResponseResult saveUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }

    @PostMapping("/register")
    public ResponseResult registerUser(@RequestBody User user){
        return userService.registerUser(user);
    }

}
