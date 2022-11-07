package com.taro.controller;

import com.taro.domain.ResponseResult;
import com.taro.domain.dto.AddArticleDto;
import com.taro.domain.dto.ArticleListDto;
import com.taro.domain.vo.PageVo;
import com.taro.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName ContentArticleController
 * Author taro
 * Date 2022/11/3 15:21
 * Version 1.0
 */

@RestController
@RequestMapping("/content/article")
public class ContentArticleController {

    @Autowired
    private ArticleService articleService;

    @PreAuthorize("@ps.hasPermission('content:article:list')")
    @GetMapping("/list")
    public ResponseResult<PageVo> pageArticleList(Integer pageNum, Integer pageSize, ArticleListDto articleListDto){
        return articleService.pageArticleList(pageNum, pageSize, articleListDto);
    }

    @PreAuthorize("@ps.hasPermission('content:article:list')")
    @GetMapping("/{id}")
    public ResponseResult getArticleInfoById(@PathVariable("id") Long id) {
        return articleService.getArticleInfoById(id);
    }

    @PreAuthorize("@ps.hasPermission('content:article:writer')")
    @PutMapping
    public ResponseResult updateArticle(@RequestBody AddArticleDto addArticleDto) {
        return articleService.updateArticle(addArticleDto);
    }

    @PreAuthorize("@ps.hasPermission('content:article:writer')")
    @PostMapping
    public ResponseResult addArticle(@RequestBody AddArticleDto addArticleDto) {
        return articleService.addArticle(addArticleDto);
    }

    @PreAuthorize("@ps.hasPermission('content:article:writer')")
    @DeleteMapping("/{id}")
    public ResponseResult deleteArticle(@PathVariable("id") Long id) {
        articleService.removeById(id);
        return ResponseResult.okResult();
    }
}
