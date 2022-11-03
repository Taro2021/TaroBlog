package com.taro.controller;

import com.taro.domain.ResponseResult;
import com.taro.domain.entity.LoginUser;
import com.taro.domain.entity.User;
import com.taro.domain.vo.UserInfoVo;
import com.taro.entity.SysMenu;
import com.taro.entity.vo.AdminUserInfoVo;
import com.taro.entity.vo.RoutersVo;
import com.taro.enums.AppHttpCodeEnum;
import com.taro.exception.SystemException;
import com.taro.service.LoginService;
import com.taro.service.SysMenuService;
import com.taro.service.SysRoleService;
import com.taro.utils.BeanCopyUtil;
import com.taro.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName UserLoginController
 * Author taro
 * Date 2022/10/15 8:48
 * Version 1.0
 */

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysRoleService sysRoleService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user) {
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }

    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }

    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户 id 查询权限信息
        List<String> perms = sysMenuService.selectPermsById(loginUser.getUser().getId());
        //根据用户 id 查询角色信息
        List<String> roleKeyList = sysRoleService.selectRoleKeyByUserId(loginUser.getUser().getId());

        UserInfoVo userInfoVo = BeanCopyUtil.copyBean(loginUser.getUser(), UserInfoVo.class);
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms, roleKeyList, userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    @GetMapping("/getRouters")
    public ResponseResult<RoutersVo> getRouters(){
        Long userId = SecurityUtils.getUserId();
        //查询 menu
        List<SysMenu> menus = sysMenuService.selectRouterMenuTreeByUserId(userId);
        return ResponseResult.okResult(new RoutersVo(menus));
    }


}
