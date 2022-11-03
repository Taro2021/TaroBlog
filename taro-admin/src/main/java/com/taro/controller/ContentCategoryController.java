package com.taro.controller;

import com.taro.domain.ResponseResult;
import com.taro.domain.dto.CategoryListDto;
import com.taro.domain.entity.Category;
import com.taro.domain.vo.CategoryVo;
import com.taro.domain.vo.PageVo;
import com.taro.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName CategoryController
 * Author taro
 * Date 2022/10/11 22:03
 * Version 1.0
 */

@RestController
@RequestMapping("/content/category")
public class ContentCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResponseResult<PageVo> categoryList(Integer pageNum, Integer pageSize, CategoryListDto categoryListDto){
        return categoryService.pageCategoryList(pageNum, pageSize, categoryListDto);
    }

    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        return categoryService.listAllCategory();
    }

    @GetMapping(value = "/{id}")
    public ResponseResult getCategoryById(@PathVariable(value = "id") Long id) {
        return categoryService.getCategoryById(id);
    }


    @PutMapping
    public ResponseResult updateCategory(@RequestBody Category category) {
        categoryService.updateById(category);
        return ResponseResult.okResult();
    }

    @PostMapping
    public ResponseResult saveCategory(@RequestBody Category category) {
        categoryService.save(category);
        return ResponseResult.okResult();
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteCategory(@PathVariable("id") Long id) {
        categoryService.removeById(id);
        return ResponseResult.okResult();
    }

}
