package com.taro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taro.domain.ResponseResult;
import com.taro.domain.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2022-10-11 21:40:39
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}
