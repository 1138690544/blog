package com.my.blog.job;

import com.my.blog.domain.entity.Article;
import com.my.blog.service.IArticleService;
import com.my.blog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @projectName: MyBlog
 * @package: com.my.blog
 * @className: UpdateViewCountJob
 * @author: LYH
 * @description: TODO
 * @date: 2023/5/30 15:51
 * @version: 1.0
 */
@Component
public class UpdateViewCountJob {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private IArticleService articleService;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void updateViewCountJob(){
        Map<String, Integer> viewCount = redisCache.getCacheMap("viewCount");
        List<Article> collect = viewCount.entrySet().stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), Long.valueOf(entry.getValue())))
                .collect(Collectors.toList());
        //更新到数据库中
        articleService.updateBatchById(collect);

    }
}
