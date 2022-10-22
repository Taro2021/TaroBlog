package com.taro.job;

import com.taro.domain.entity.Article;
import com.taro.service.ArticleService;
import com.taro.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ClassName ViewCountJob
 * Author taro
 * Date 2022/10/22 12:26
 * Version 1.0
 */
//定时任务，将 redis 中的浏览量持久化到数据库中
@Component
public class UpdateViewCountJob {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "0/30 * * * * ?")
    public void updateViewCount(){
        //redis 中获取浏览量信息
        Map<String, Integer> cacheMap = redisCache.getCacheMap("article:viewCount");
        List<Article> articles = cacheMap.entrySet().stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()),
                        entry.getValue().longValue()))
                .collect(Collectors.toList());

        //更新到数据库中
        articleService.updateBatchById(articles);
    }
}
