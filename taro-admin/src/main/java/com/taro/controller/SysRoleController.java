package com.taro.controller;

import com.taro.domain.ResponseResult;
import com.taro.domain.dto.SysRoleDto;
import com.taro.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName SysRoleController
 * Author taro
 * Date 2022/11/7 17:34
 * Version 1.0
 */

@RestController
@RequestMapping("/system/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping("/list")
    public ResponseResult pageRoleList(Integer pageNum, Integer pageSize, SysRoleDto sysRoleDto) {
        return sysRoleService.pageRoleList(pageNum, pageSize, sysRoleDto);
    }
}
