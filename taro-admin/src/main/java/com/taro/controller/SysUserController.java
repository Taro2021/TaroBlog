package com.taro.controller;


import com.taro.domain.ResponseResult;
import com.taro.domain.dto.UpdateUserDto;
import com.taro.domain.dto.UserDto;
import com.taro.domain.dto.UserListDto;
import com.taro.domain.vo.PageVo;
import com.taro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("@ps.hasPermission('system:role:query')")
    @GetMapping("/list")
    public ResponseResult<PageVo> userPageList(Integer pageNum, Integer pageSize, UserListDto userListDto) {
        return userService.userPageList(pageNum, pageSize, userListDto);
    }

    @PreAuthorize("@ps.hasPermission('system:role:add')")
    @PostMapping
    public ResponseResult savaUser(@RequestBody UserDto userDto) {
        return userService.saveUser(userDto);
    }

    @PreAuthorize("@ps.hasPermission('system:role:remove')")
    @DeleteMapping("/{id}")
    public ResponseResult deleteUser(@PathVariable("id") Long id) {
        userService.removeById(id);
        return ResponseResult.okResult();
    }

    @PreAuthorize("@ps.hasPermission('system:role:query')")
    @GetMapping("/{id}")
    public ResponseResult getUserInfoById(@PathVariable("id") Long id) {
        return userService.getUserInfoById(id);
    }

    @PreAuthorize("@ps.hasPermission('system:role:edit')")
    @PutMapping()
    public ResponseResult updateUser(@RequestBody UpdateUserDto updateUserDto) {
        return userService.updateUser(updateUserDto);
    }
}
