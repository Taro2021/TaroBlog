package com.taro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taro.domain.entity.SysRoleMenu;
import com.taro.mapper.SysRoleMenuMapper;
import com.taro.service.SysRoleMenuService;
import org.springframework.stereotype.Service;

/**
 * 角色和菜单关联表(SysRoleMenu)表服务实现类
 *
 * @author makejava
 * @since 2022-11-08 18:41:02
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

}

