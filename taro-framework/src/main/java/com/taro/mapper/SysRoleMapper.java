package com.taro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taro.domain.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色信息表(SysRole)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-02 14:23:58
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<String> selectRoleKeyByUserId(Long userId);
}


