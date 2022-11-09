package com.taro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taro.constant.SystemConstants;
import com.taro.domain.ResponseResult;
import com.taro.domain.dto.AddArticleDto;
import com.taro.domain.dto.ArticleListDto;
import com.taro.domain.entity.Article;
import com.taro.domain.entity.ArticleTag;
import com.taro.domain.vo.*;
import com.taro.enums.AppHttpCodeEnum;
import com.taro.exception.SystemException;
import com.taro.mapper.ArticleMapper;
import com.taro.service.ArticleService;
import com.taro.service.ArticleTagService;
import com.taro.service.CategoryService;
import com.taro.utils.BeanCopyUtil;
import com.taro.utils.RedisCache;
import com.taro.utils.StatusCheckUtils;
import io.jsonwebtoken.lang.Strings;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.SyncFailedException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ClassName ArticleServiceImpl
 * Author taro
 * Date 2022/10/11 10:49
 * Version 1.0
 */

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleTagService articleTagService;

    public void correctFormat(AddArticleDto addArticleDto) {
        if(!Strings.hasText(addArticleDto.getTitle())) {
            throw new SystemException(AppHttpCodeEnum.ARTICLE_TITLE_NOT_NULL);
        }
        if(!Strings.hasText(addArticleDto.getSummary())) {
            throw new SystemException(AppHttpCodeEnum.ARTICLE_SUMMARY_NOT_NULL);
        }
        if(StatusCheckUtils.statusIllegal(addArticleDto.getStatus())){
            throw new SystemException(AppHttpCodeEnum.ARTICLE_STATUS_ILLEGAL);
        }
        if(StatusCheckUtils.statusIllegal(addArticleDto.getIsTop())) {
            throw new SystemException(AppHttpCodeEnum.ARTICLE_IS_TOP_ILLEGAL);
        }
        if(StatusCheckUtils.statusIllegal(addArticleDto.getIsComment())) {
            throw new SystemException(AppHttpCodeEnum.ARTICLE_IS_COMMENT_ILLEGAL);
        }
    }

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
        //流处理，获取分类名
        List<Article> articles = page.getRecords().stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());

        List<ArticleListVo> articleListVos = BeanCopyUtil.copyBeanList(articles, ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos, page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult articleDetails(Long id) {

        Article article = super.getById(id);
        //从 redis 缓存中读取浏览量
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        article.setViewCount(viewCount.longValue());

        String categoryName = categoryService.getById(article.getCategoryId()).getName();
        if(categoryName != null) article.setCategoryName(categoryName);

        ArticleDetailVo articleDetailVo = BeanCopyUtil.copyBean(article, ArticleDetailVo.class);

        return ResponseResult.okResult(articleDetailVo);
    }

    //更新 redis 中文章浏览量
    @Override
    public ResponseResult updateViewCount(Long id) {
        redisCache.incrementCacheMapValue("article:viewCount", id.toString(), 1);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<PageVo> pageArticleList(Integer pageNum, Integer pageSize, ArticleListDto articleListDto) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //支持模糊查询
        queryWrapper.like(StringUtils.hasText(articleListDto.getTitle()), Article :: getTitle, articleListDto.getTitle());
        queryWrapper.like(StringUtils.hasText(articleListDto.getSummary()), Article :: getSummary, articleListDto.getSummary());

        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<ArticleListVo> articleListVos = page.getRecords()
                .stream()
                .map(article -> BeanCopyUtil.copyBean(article, ArticleListVo.class))
                .collect(Collectors.toList());

        PageVo pageVo = new PageVo(articleListVos, page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    @Transactional
    public ResponseResult addArticle(AddArticleDto addArticleDto) {
        correctFormat(addArticleDto);
        //存文章
        Article article = BeanCopyUtil.copyBean(addArticleDto, Article.class);
        super.save(article);

        //保存文章和 tag 的关系
        List<ArticleTag> articleTags = addArticleDto.getTags()
                .stream()
                .map(tag -> new ArticleTag(addArticleDto.getId(), tag))
                .collect(Collectors.toList());

        articleTagService.saveBatch(articleTags);

        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult updateArticle(AddArticleDto addArticleDto) {
        correctFormat(addArticleDto);
        Article article = BeanCopyUtil.copyBean(addArticleDto, Article.class);
        super.updateById(article);

        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag :: getArticleId,  addArticleDto.getId());

        articleTagService.remove(queryWrapper);

        List<ArticleTag> articleTags = addArticleDto.getTags().stream()
                .map(tag -> new ArticleTag(addArticleDto.getId(), tag))
                .collect(Collectors.toList());

        articleTagService.saveBatch(articleTags);

        return ResponseResult.okResult();
    }

    /**
     * 获取 文章信息，并且获取对应的 tag id
     * @param id
     * @return
     */
    @Override
    public ResponseResult getArticleInfoById(Long id) {
        Article article = super.getById(id);
        ArticleTagDetailVo articleTagDetailVo = BeanCopyUtil.copyBean(article, ArticleTagDetailVo.class);
        List<Long> tagIds = new ArrayList<>();

        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag :: getArticleId, id);
        List<ArticleTag> articleTags = articleTagService.list(queryWrapper);

        for(ArticleTag articleTag : articleTags) tagIds.add(articleTag.getTagId());

        articleTagDetailVo.setTags(tagIds);

        return ResponseResult.okResult(articleTagDetailVo);
    }

    // @Override
    // public ResponseResult deleteArticleById(Long id) {
    //     super.removeById(id);
    //     LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
    //     queryWrapper.eq(ArticleTag :: getArticleId, id);
    //
    //     //删除 tag 映射关系
    //     articleTagService.remove(queryWrapper);
    //
    //     return ResponseResult.okResult();
    // }

}
