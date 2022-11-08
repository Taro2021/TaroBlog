package com.taro.controller;

import com.taro.domain.ResponseResult;
import com.taro.domain.dto.AddRoleDto;
import com.taro.domain.dto.RoleDto;
import com.taro.domain.dto.SysRoleDto;
import com.taro.domain.dto.UpdateRoleDto;
import com.taro.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PreAuthorize("@ps.hasPermission('system:role:list')")
    @GetMapping("/list")
    public ResponseResult pageRoleList(Integer pageNum, Integer pageSize, SysRoleDto sysRoleDto) {
        return sysRoleService.pageRoleList(pageNum, pageSize, sysRoleDto);
    }

    @PreAuthorize("@ps.hasPermission('system:role:edit')")
    @PutMapping("/changeStatus")
    public ResponseResult changeRoleStatus(@RequestBody RoleDto roleDto) {
        return sysRoleService.changeRoleStatus(roleDto);
    }

    @PreAuthorize("@ps.hasPermission('system:role:add')")
    @PostMapping
    public ResponseResult saveRole(@RequestBody AddRoleDto addRoleDto){
        return sysRoleService.saveSysRole(addRoleDto);
    }

    @PreAuthorize("@ps.hasPermission('system:role:query')")
    @GetMapping("/{id}")
    public ResponseResult getRoleInfoById(@PathVariable("id") Long id) {
        return sysRoleService.getRoleInfoById(id);
    }

    @PreAuthorize("@ps.hasPermission('system:role:edit')")
    @PutMapping
    public ResponseResult updateRoleInfo(@RequestBody UpdateRoleDto updateRoleDto) {
        return sysRoleService.updateRoleInfo(updateRoleDto);
    }

    @PreAuthorize("@ps.hasPermission('system:role:remove')")
    @DeleteMapping("/{id}")
    public ResponseResult deleteRole(@PathVariable("id") Long id) {
        sysRoleService.removeById(id);
        return ResponseResult.okResult();
    }

    @PreAuthorize("@ps.hasPermission('system:role:list')")
    @GetMapping("/listAllRole")
    public ResponseResult listAllRole() {
        return sysRoleService.listAllRole();
    }

}
