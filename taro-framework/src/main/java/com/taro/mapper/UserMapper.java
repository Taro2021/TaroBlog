package com.taro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taro.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-14 19:41:05
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}


