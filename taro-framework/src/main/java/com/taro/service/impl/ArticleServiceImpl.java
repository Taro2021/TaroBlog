package com.taro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taro.constant.SystemConstants;
import com.taro.domain.ResponseResult;
import com.taro.domain.entity.Article;
import com.taro.domain.vo.ArticleListVo;
import com.taro.domain.vo.HotArticleVo;
import com.taro.domain.vo.PageVo;
import com.taro.mapper.ArticleMapper;
import com.taro.service.ArticleService;
import com.taro.utils.BeanCopyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Vector;

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

        //正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //浏览量排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多查询 10 条
        Page<Article> page = new Page<>(1, 10);
        page(page, queryWrapper);

        List<Article> articles = page.getRecords();
        //bean
        // List<HotArticleVo> hotArticleVos = new Vector<>();
        // for(Article article : articles){
        //     HotArticleVo hotArticleVo = new HotArticleVo();
        //     BeanUtils.copyProperties(article, hotArticleVo);
        //     hotArticleVos.add(hotArticleVo);
        // }

        List<HotArticleVo> hotArticleVos = BeanCopyUtil.copyBeanList(articles, HotArticleVo.class);

        return ResponseResult.okResult(hotArticleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //若有 categoryId，查询时需要相同
        queryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        //状态是正式发布的
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //对 isTop 降序
        queryWrapper.orderByDesc(Article::getIsTop);

        //分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

        List<ArticleListVo> articleListVos = BeanCopyUtil.copyBeanList(page.getRecords(), ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos, page.getTotal());

        return ResponseResult.okResult(pageVo);
    }
}
