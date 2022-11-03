package com.taro.controller;

import com.taro.domain.ResponseResult;
import com.taro.domain.dto.AddArticleDto;
import com.taro.domain.dto.ArticleListDto;
import com.taro.domain.entity.Article;
import com.taro.domain.vo.PageVo;
import com.taro.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/list")
    public ResponseResult<PageVo> pageArticleList(Integer pageNum, Integer pageSize, ArticleListDto articleListDto){
        return articleService.pageArticleList(pageNum, pageSize, articleListDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getArticleInfoById(@PathVariable("id") Long id) {
        return ResponseResult.okResult(articleService.getById(id));
    }

    @PutMapping
    public ResponseResult updateArticle(@RequestBody AddArticleDto addArticleDto) {
        return articleService.updateArticle(addArticleDto);
    }

    @PostMapping
    public ResponseResult addArticle(@RequestBody AddArticleDto addArticleDto) {
        return articleService.addArticle(addArticleDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteArticle(@PathVariable("id") Long id) {
        articleService.removeById(id);
        return ResponseResult.okResult();
    }
}
