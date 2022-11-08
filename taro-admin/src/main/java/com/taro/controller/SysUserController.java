package com.taro.controller;


import com.taro.domain.ResponseResult;
import com.taro.domain.dto.UserDto;
import com.taro.domain.dto.UserListDto;
import com.taro.domain.vo.PageVo;
import com.taro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName SysUserController
 * Author taro
 * Date 2022/11/8 20:15
 * Version 1.0
 */

@RestController
@RequestMapping("/system/user")
public class SysUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResponseResult<PageVo> userPageList(Integer pageNum, Integer pageSize, UserListDto userListDto) {
        return userService.userPageList(pageNum, pageSize, userListDto);
    }

    @PostMapping
    public ResponseResult savaUser(@RequestBody UserDto userDto) {
        return userService.saveUser(userDto);
    }
}
