package com.taro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taro.domain.ResponseResult;
import com.taro.domain.dto.SysRoleDto;
import com.taro.domain.entity.SysRole;

import java.util.List;


/**
 * 角色信息表(SysRole)表服务接口
 *
 * @author makejava
 * @since 2022-11-02 14:23:58
 */
public interface SysRoleService extends IService<SysRole> {

    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult pageRoleList(Integer pageNum, Integer pageSize, SysRoleDto sysRoleDto);
}
