package com.taro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taro.domain.entity.SysUserRole;
import com.taro.mapper.SysUserRoleMapper;
import com.taro.service.SysUserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户和角色关联表(SysUserRole)表服务实现类
 *
 * @author makejava
 * @since 2022-11-09 15:47:03
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

}

