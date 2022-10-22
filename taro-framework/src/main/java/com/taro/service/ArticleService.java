package com.taro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taro.domain.ResponseResult;
import com.taro.domain.entity.Article;

/**
 * ClassName AticleService
 * Author taro
 * Date 2022/10/11 10:47
 * Version 1.0
 */

public interface ArticleService extends IService<Article>{
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult articleDetails(Long id);

    ResponseResult updateViewCount(Long id);
}
