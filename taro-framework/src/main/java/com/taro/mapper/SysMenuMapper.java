package com.taro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taro.domain.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜单权限表(SysMenu)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-02 14:21:09
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<String> selectPermsByUserId(Long userId);

    List<SysMenu> selectRouterMenuTreeByUserId(Long userId);

    List<SysMenu> selectAllRouterMenu();

}


