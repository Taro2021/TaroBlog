package com.taro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taro.domain.entity.SysMenu;

import java.util.List;


/**
 * 菜单权限表(SysMenu)表服务接口
 *
 * @author makejava
 * @since 2022-11-02 14:21:10
 */
public interface SysMenuService extends IService<SysMenu> {

    List<String> selectPermsById(Long id);

    List<SysMenu> selectRouterMenuTreeByUserId(Long userId);
}
