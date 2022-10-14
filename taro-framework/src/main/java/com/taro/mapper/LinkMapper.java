package com.taro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taro.domain.entity.Link;
import org.apache.ibatis.annotations.Mapper;

/**
 * 友链(Link)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-14 10:51:41
 */
@Mapper
public interface LinkMapper extends BaseMapper<Link> {

}


