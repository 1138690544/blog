package com.my.blog.runner;

import com.my.blog.dao.ArticleMapper;
import com.my.blog.domain.entity.Article;
import com.my.blog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * @projectName: MyBlog
 * @package: com.my.blog.runner
 * @className: ViewCountRunner
 * @author: LYH
 * @description: TODO
 * @date: 2023/5/30 15:41
 * @version: 1.0
 */
@Component
public class ViewCountRunner implements CommandLineRunner {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private RedisCache redisCache;
    //启动时count就存在redis中
    @Override
    public void run(String... args) throws Exception {
        List<Article> articles = articleMapper.selectList(null);
        HashMap<String, Integer> viewCountMap = new HashMap<>();
        for (Article article : articles) {
            viewCountMap.put(article.getId().toString(),article.getViewCount().intValue());
        }
        redisCache.setCacheMap("viewCount",viewCountMap);
    }
}
