package com.taro.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taro.constant.SystemConstants;
import com.taro.domain.ResponseResult;
import com.taro.domain.dto.CategoryListDto;
import com.taro.domain.entity.Article;
import com.taro.domain.entity.Category;
import com.taro.domain.vo.CategoryListVo;
import com.taro.domain.vo.CategoryVo;
import com.taro.domain.vo.PageVo;
import com.taro.mapper.CategoryMapper;
import com.taro.service.ArticleService;
import com.taro.service.CategoryService;
import com.taro.utils.BeanCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2022-10-11 21:40:40
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        //查询文章表中已发布文章
        LambdaQueryWrapper<Article> articleQueryWrapper = new LambdaQueryWrapper<>();
        articleQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articles = articleService.list(articleQueryWrapper);

        //对文章分类 id 去重
        Set<Long> categoryIds = articles.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());

        //查询分类表
        List<Category> categories = listByIds(categoryIds);
        categories = categories.stream()
                .filter(o -> SystemConstants.STATUS_NORMAL.equals(o.getStatus()))
                .collect(Collectors.toList());

        //封装 vo
        List<CategoryVo> categoryVos = BeanCopyUtil.copyBeanList(categories, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult<PageVo> pageCategoryList(Integer pageNum, Integer pageSize, CategoryListDto categoryListDto) {

        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(categoryListDto.getName()), Category :: getName, categoryListDto.getName());
        queryWrapper.eq(StringUtils.hasText(categoryListDto.getStatus()), Category :: getStatus, categoryListDto.getStatus());

        Page<Category> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);

        List<CategoryListVo> categoryListVos = page.getRecords()
                .stream()
                .map(category -> BeanCopyUtil.copyBean(category, CategoryListVo.class))
                .collect(Collectors.toList());

        PageVo pageVo = new PageVo(categoryListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getCategoryById(Long id) {
        Category category = super.getById(id);
        CategoryListVo categoryListVo = BeanCopyUtil.copyBean(category, CategoryListVo.class);
        return ResponseResult.okResult(categoryListVo);
    }

}

