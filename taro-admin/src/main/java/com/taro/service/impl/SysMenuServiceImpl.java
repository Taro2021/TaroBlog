package com.taro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taro.constant.SystemConstants;
import com.taro.entity.SysMenu;
import com.taro.mapper.SysMenuMapper;
import com.taro.service.SysMenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(SysMenu)表服务实现类
 *
 * @author makejava
 * @since 2022-11-02 14:21:10
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    //根据 id 查询权限
    @Override
    public List<String> selectPermsById(Long id) {
        // id == 1 为管理员，返回所有权限，否则返回具有的权限
        if(id == 1L) {
            LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
            //菜单，按钮
            queryWrapper.in(SysMenu :: getMenuType, SystemConstants.MENU, SystemConstants.BUTTON);
            //状态正常
            queryWrapper.eq(SysMenu :: getStatus, SystemConstants.STATUS_NORMAL);
            List<SysMenu> list = super.list(queryWrapper);
            List<String> perms = list.stream()
                    .map(SysMenu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        //userId -> roleId -> 根据 roleId 去 sys_menu 中查询权限信息
        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<SysMenu> selectRouterMenuTreeByUserId(Long userId) {
        SysMenuMapper baseMapper = getBaseMapper();
        List<SysMenu> menus = null;
        if(userId == 1L) {
            menus = baseMapper.selectAllRouterMenu();
        }else {
            menus = baseMapper.selectRouterMenuTreeByUserId(userId);
        }

        return buildMenuTree(menus, 0L);
    }

    private List<SysMenu> buildMenuTree(List<SysMenu> menus, Long parentId) {
        return menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
    }

    private List<SysMenu> getChildren(SysMenu menu, List<SysMenu> menus) {
        return menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m -> m.setChildren(getChildren(m, menus)))
                .collect(Collectors.toList());
    }
}

