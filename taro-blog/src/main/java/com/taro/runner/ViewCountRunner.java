package com.taro.runner;

import com.taro.domain.entity.Article;
import com.taro.mapper.ArticleMapper;
import com.taro.service.ArticleService;
import com.taro.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * ClassName ViewCountRunner
 * Author taro
 * 初始化缓存
 * 实现 CommandLineRunner，run 方法会在模块启动时执行
 * 启动时，读入文章的浏览量到 redis 中
 * 因为是在模块启动前执行，所以不归 SpringBoot 管理，不能注入 service，需要注入 mapper
 * Date 2022/10/22 11:08
 * Version 1.0
 */

@Component
public class ViewCountRunner implements CommandLineRunner {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        List<Article> articles = articleMapper.selectList(null);

        Map<String, Integer> viewCountMap = articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(),
                        article -> article.getViewCount().intValue()));

        redisCache.setCacheMap("article:viewCount", viewCountMap);
    }
}
