package com.taro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taro.domain.entity.SysRole;
import com.taro.mapper.SysRoleMapper;
import com.taro.service.SysRoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(SysRole)表服务实现类
 *
 * @author makejava
 * @since 2022-11-02 14:23:58
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //管理员，直接返回 admin
        if(id == 1L) {
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }

        return getBaseMapper().selectRoleKeyByUserId(id);
    }
}

