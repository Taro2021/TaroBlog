package com.taro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taro.domain.entity.Article;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName ArticleMapper
 * Author taro
 * Date 2022/10/11 10:43
 * Version 1.0
 */

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}
