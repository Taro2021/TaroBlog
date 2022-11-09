package com.taro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taro.domain.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户和角色关联表(SysUserRole)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-09 15:47:02
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

}


