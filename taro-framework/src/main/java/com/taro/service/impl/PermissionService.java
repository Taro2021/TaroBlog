package com.taro.service.impl;

import com.taro.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName PermissionService
 * Author taro
 * Date 2022/11/5 18:08
 * Version 1.0
 */

@Service("ps")
public class PermissionService {

    /**
     * 判断当前用户是否有该权限
     * @param perm
     * @return
     */
    public boolean hasPermission(String perm){
        if(SecurityUtils.isAdmin()) {//超级管理直接返回 true
            return true;
        }
        List<String> permissions = SecurityUtils.getLoginUser().getPermissions();
        return permissions.contains(perm);
    }
}
