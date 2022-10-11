package com.taro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taro.domain.ResponseResult;
import com.taro.domain.entity.Article;
import com.taro.mapper.ArticleMapper;
import com.taro.service.ArticleService;
import org.springframework.stereotype.Service;

/**
 * ClassName ArticleServiceImpl
 * Author taro
 * Date 2022/10/11 10:49
 * Version 1.0
 */

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章，不能把草稿展现出来，不能把删除的文章查询出来，根据浏览量降序排列
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        return null;
    }
}
