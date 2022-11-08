package com.taro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taro.constant.SystemConstants;
import com.taro.domain.ResponseResult;
import com.taro.domain.dto.AddRoleDto;
import com.taro.domain.dto.RoleDto;
import com.taro.domain.dto.SysRoleDto;
import com.taro.domain.dto.UpdateRoleDto;
import com.taro.domain.entity.SysRole;
import com.taro.domain.entity.SysRoleMenu;
import com.taro.domain.vo.PageVo;
import com.taro.domain.vo.SysRoleListVo;
import com.taro.domain.vo.SysRoleVo;
import com.taro.enums.AppHttpCodeEnum;
import com.taro.exception.SystemException;
import com.taro.mapper.SysRoleMapper;
import com.taro.service.SysRoleMenuService;
import com.taro.service.SysRoleService;
import com.taro.utils.BeanCopyUtil;
import com.taro.utils.SecurityUtils;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * 角色信息表(SysRole)表服务实现类
 *
 * @author makejava
 * @since 2022-11-02 14:23:58
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

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

    /**
     * 添加新角色，并建立角色于菜单的映射
     * @param addRoleDto
     * @return
     */
    @Override
    @Transactional
    public ResponseResult saveSysRole(AddRoleDto addRoleDto) {
        SysRole sysRole = BeanCopyUtil.copyBean(addRoleDto, SysRole.class);
        save(sysRole);
        saveRoleMenu(sysRole.getId(), addRoleDto.getMenuIds());
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getRoleInfoById(Long id) {
        return ResponseResult.okResult(BeanCopyUtil.copyBean(getById(id), SysRoleVo.class));
    }

    /**
     * 更新角色信息，并更新菜单映射
     * @param updateRoleDto
     * @return
     */
    @Override
    public ResponseResult updateRoleInfo(UpdateRoleDto updateRoleDto) {
        SysRole sysRole = BeanCopyUtil.copyBean(updateRoleDto, SysRole.class);
        updateById(sysRole);
        deleteRoleMenu(updateRoleDto.getId());
        saveRoleMenu(updateRoleDto.getId(), updateRoleDto.getMenuIds());
        return ResponseResult.okResult();
    }

    /**
     * 列出所有正常的角色信息
     * @return
     */
    @Override
    public ResponseResult listAllRole() {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRole :: getStatus, SystemConstants.STATUS_NORMAL);
        List<SysRole> roles = list(queryWrapper);
        List<SysRoleListVo> sysRoleListVos = BeanCopyUtil.copyBeanList(roles, SysRoleListVo.class);
        return ResponseResult.okResult(sysRoleListVos);
    }

    // @Override
    // public ResponseResult deleteRole(Long roleId) {
    //     deleteRoleMenu(roleId);
    //     removeById(roleId);
    //     return ResponseResult.okResult();
    // }

    /**
     * 保存角色和菜单映射
     * @param roleId
     * @param menuIds
     */
    public void saveRoleMenu(Long roleId, List<Long> menuIds){
        List<SysRoleMenu> roleMenus = menuIds.stream()
                .map(menuId -> new SysRoleMenu(roleId, menuId))
                .collect(Collectors.toList());
        sysRoleMenuService.saveBatch(roleMenus);
    }

    public void deleteRoleMenu(Long roleId) {
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleMenu::getRoleId, roleId);
        sysRoleMenuService.remove(queryWrapper);
    }
}

