package com.taro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taro.constant.SystemConstants;
import com.taro.domain.ResponseResult;
import com.taro.domain.dto.RoleDto;
import com.taro.domain.dto.SysRoleDto;
import com.taro.domain.entity.SysRole;
import com.taro.domain.vo.PageVo;
import com.taro.domain.vo.SysRoleListVo;
import com.taro.enums.AppHttpCodeEnum;
import com.taro.exception.SystemException;
import com.taro.mapper.SysRoleMapper;
import com.taro.service.SysRoleService;
import com.taro.utils.BeanCopyUtil;
import com.taro.utils.SecurityUtils;
import io.jsonwebtoken.lang.Strings;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

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

    /**
     * 分页查询角色信息
     * @param pageNum
     * @param pageSize
     * @param sysRoleDto
     * @return
     */
    @Override
    public ResponseResult pageRoleList(Integer pageNum, Integer pageSize, SysRoleDto sysRoleDto) {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Strings.hasText(sysRoleDto.getRoleName()), SysRole :: getRoleName, sysRoleDto.getRoleName());
        queryWrapper.eq(Strings.hasText(sysRoleDto.getStatus()), SysRole :: getStatus, sysRoleDto.getStatus());

        Page<SysRole> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

        List<SysRoleListVo> sysRoleListVos = BeanCopyUtil.copyBeanList(page.getRecords(), SysRoleListVo.class);
        PageVo pageVo = new PageVo(sysRoleListVos, page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    /**
     * 变更角色状态
     * @return
     */
    @Override
    public ResponseResult changeRoleStatus(RoleDto roleDto) {
        if(Objects.isNull(roleDto.getRoleId())){
            throw new SystemException(AppHttpCodeEnum.ROLE_ID_NOT_NULL);
        }
        if(!Strings.hasText(roleDto.getStatus()) ||
                (!SystemConstants.STATUS_NORMAL.equals(roleDto.getStatus()) && !SystemConstants.STATUS_DISABLE.equals(roleDto.getStatus()))){
            throw new SystemException(AppHttpCodeEnum.STATUS_ERR);
        }
        SysRole sysRole = getById(roleDto.getRoleId());
        sysRole.setStatus(roleDto.getStatus());
        updateById(sysRole);
        return ResponseResult.okResult();
    }
}

