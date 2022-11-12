package com.taro.job;

import com.taro.domain.entity.Article;
import com.taro.service.ArticleService;
import com.taro.utils.ApplicationContextUtil;
import com.taro.utils.RedisCache;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * ClassName ViewCountJob
 * Author taro
 * Date 2022/10/22 12:26
 * Version 1.0
 */
//定时任务，将 redis 中的浏览量持久化到数据库
@Component
public class UpdateViewCountJob {

    @Autowired
    private RedisCache redisCache;

    // @Autowired
    // private ArticleService articleService;

    @Scheduled(cron = "0/30 * * * * ?")
    public void updateViewCount(){
        ArticleService articleService = (ArticleService)ApplicationContextUtil.getBean("ArticleServiceImpl");
        //redis 中获取浏览量信息
        Map<String, Integer> cacheMap = redisCache.getCacheMap("article:viewCount");
        if(Objects.isNull(cacheMap) || cacheMap.size() == 0) return;
        List<Article> articles = cacheMap.entrySet().stream()
                .filter(entry -> Strings.hasText(entry.getKey()))
                .map(entry -> new Article(Long.parseLong(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());

        //System.out.println(articles);
        //更新到数据库中
        articleService.updateBatchById(articles);
    }
}
