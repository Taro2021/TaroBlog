package com.taro.controller;


import com.taro.domain.ResponseResult;
import com.taro.domain.dto.SysMenuDto;
import com.taro.domain.entity.SysMenu;
import com.taro.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName SystemMenuController
 * Author taro
 * Date 2022/11/5 21:09
 * Version 1.0
 */

@RestController
@RequestMapping(("/system/menu"))
public class SystemMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @PreAuthorize("@ps.hasPermission('system:menu:query')")
    @GetMapping("/list")
    public ResponseResult getMenuList(SysMenuDto sysMenuDto){
        return sysMenuService.listByDto(sysMenuDto);
    }

    @PreAuthorize("@ps.hasPermission('system:menu:add')")
    @PostMapping
    public ResponseResult saveMenu(@RequestBody SysMenu sysMenu){
        sysMenuService.save(sysMenu);
        return ResponseResult.okResult();
    }

    @PreAuthorize("@ps.hasPermission('system:menu:query')")
    @GetMapping("/{id}")
    public ResponseResult getMenuById(@PathVariable("id") Long id) {
        return sysMenuService.getMenuVoById(id);
    }

    @PreAuthorize("@ps.hasPermission('system:menu:edit')")
    @PutMapping
    public ResponseResult updateMenu(@RequestBody SysMenu sysMenu){
        return sysMenuService.updateSysMenu(sysMenu);
    }

    @PreAuthorize("@ps.hasPermission('system:menu:remove')")
    @DeleteMapping("/{id}")
    public ResponseResult deleteMenu(@PathVariable("id") Long id) {
        return sysMenuService.deleteSysMenu(id);
    }

    @PreAuthorize("@ps.hasPermission('system:menu:query')")
    @GetMapping("/treeselect")
    public ResponseResult treeSelect() {
        return sysMenuService.treeSelect();
    }

    @PreAuthorize("@ps.hasPermission('system:menu:query')")
    @GetMapping("/roleMenuTreeselect/{id}")
    public ResponseResult roleMenuTreeSelect(@PathVariable("id") Long id) {
        return sysMenuService.roleMenuTreeSelect(id);
    }
}
