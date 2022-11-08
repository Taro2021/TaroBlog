package com.taro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taro.constant.SystemConstants;
import com.taro.domain.ResponseResult;
import com.taro.domain.dto.SysMenuDto;
import com.taro.domain.entity.SysMenu;
import com.taro.domain.entity.SysRoleMenu;
import com.taro.domain.vo.SysMenuTreeVo;
import com.taro.domain.vo.SysMenuVo;
import com.taro.domain.vo.SysRoleMenuTreeVo;
import com.taro.enums.AppHttpCodeEnum;
import com.taro.exception.SystemException;
import com.taro.mapper.SysMenuMapper;
import com.taro.service.SysMenuService;
import com.taro.service.SysRoleMenuService;
import com.taro.utils.BeanCopyUtil;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 菜单权限表(SysMenu)表服务实现类
 *
 * @author makejava
 * @since 2022-11-02 14:21:10
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

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
            return list.stream()
                    .map(SysMenu::getPerms)
                    .collect(Collectors.toList());
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

    @Override
    public ResponseResult getMenuVoById(Long id) {
        SysMenu sysMenu = super.getById(id);
        SysMenuVo sysMenuVo = BeanCopyUtil.copyBean(sysMenu, SysMenuVo.class);

        return ResponseResult.okResult(sysMenuVo);
    }

    /**
     * 关系菜单，不能把自己设为自己的父菜单
     * @param sysMenu
     * @return
     */
    @Override
    public ResponseResult updateSysMenu(SysMenu sysMenu) {
        if(!Objects.isNull(sysMenu.getId()) && !Objects.isNull(sysMenu.getParentId())
                && sysMenu.getId().compareTo(sysMenu.getParentId()) == 0){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }

        super.updateById(sysMenu);

        return null;
    }

    /**
     * 删除菜单，若还有子菜单不能删除
     * @param id
     * @return
     */
    @Override
    public ResponseResult deleteSysMenu(Long id) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMenu :: getParentId, id);
        List<SysMenu> sysMenus = super.list(queryWrapper);
        //若存在子菜单抛出异常
        if(!Objects.isNull(sysMenus) && !sysMenus.isEmpty()) {
            throw new SystemException(AppHttpCodeEnum.DELETE_EXIT_CHILDREN);
        }

        super.removeById(id);

        return ResponseResult.okResult();
    }

    //若 Dto 中携带数据，则据此条件查询
    @Override
    public ResponseResult listByDto(SysMenuDto sysMenuDto) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();

        if(Strings.hasText(sysMenuDto.getMenuName())) {
            queryWrapper.eq(SysMenu :: getMenuName, sysMenuDto.getMenuName());
            //查询父目录 id
            SysMenu parent = super.getOne(queryWrapper);
            //更改 LambdaQueryWrapper 条件的连接方式，默认 and 连接
            queryWrapper.or();
            queryWrapper.eq(SysMenu :: getParentId, parent.getId());
        }
        if(Strings.hasText(sysMenuDto.getStatus())) {
            queryWrapper.eq(SysMenu :: getStatus, sysMenuDto.getStatus());
        }

        List<SysMenu> sysMenus = super.list(queryWrapper);
        return ResponseResult.okResult(sysMenus);
    }

    @Override
    public ResponseResult treeSelect() {
        return ResponseResult.okResult(buildMenuVoTree());
    }

    @Override
    @Transactional
    public ResponseResult roleMenuTreeSelect(Long roleId) {
        List<SysMenuTreeVo> sysMenuTreeVos = buildMenuVoTree();
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleMenu :: getRoleId, roleId);

        List<SysRoleMenu> sysRoleMenus = sysRoleMenuService.list(queryWrapper);
        List<Long> menuIds = sysRoleMenus.stream()
                .map(SysRoleMenu::getMenuId)
                .collect(Collectors.toList());

        return ResponseResult.okResult(new SysRoleMenuTreeVo(sysMenuTreeVos, menuIds));
    }

    private List<SysMenuTreeVo> buildMenuVoTree() {
        SysMenuMapper baseMapper = getBaseMapper();
        List<SysMenu> sysMenus = baseMapper.selectAllRouterMenu();

        List<SysMenuTreeVo> sysMenuTreeVos = sysMenus.stream()
                .map(menu -> BeanCopyUtil.copyBean(menu, SysMenuTreeVo.class).setLabel(menu.getMenuName()))
                .collect(Collectors.toList());

        return sysMenuTreeVos.stream()
                .filter(menu -> menu.getParentId().equals(0L))
                .map(menu -> menu.setChildren(getVoChildren(menu, sysMenuTreeVos)))
                .collect(Collectors.toList());
    }

    private List<SysMenuTreeVo> getVoChildren(SysMenuTreeVo parent, List<SysMenuTreeVo> sysMenuTreeVos){
        return sysMenuTreeVos.stream()
                .filter(menu -> menu.getParentId().equals(parent.getId()))
                .map(menu -> menu.setChildren(getVoChildren(menu, sysMenuTreeVos)))
                .collect(Collectors.toList());
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

