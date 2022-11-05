package com.taro.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.taro.domain.ResponseResult;
import com.taro.domain.dto.CategoryListDto;
import com.taro.domain.entity.Category;
import com.taro.domain.vo.CategoryVo;
import com.taro.domain.vo.ExcelCategoryVo;
import com.taro.domain.vo.PageVo;
import com.taro.enums.AppHttpCodeEnum;
import com.taro.service.CategoryService;
import com.taro.utils.BeanCopyUtil;
import com.taro.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

    @GetMapping("/export")
    public void export(HttpServletResponse response){
        //设置下载文件请求头
        try {
            WebUtils.setDownLoadHeader("分类.xlsx", response);
            List<Category> categories = categoryService.list();
            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtil.copyBeanList(categories, ExcelCategoryVo.class);
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出").doWrite(excelCategoryVos);
        } catch (IOException e) {
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }

}
