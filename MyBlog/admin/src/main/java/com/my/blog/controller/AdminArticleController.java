package com.my.blog.controller;

import com.my.blog.domain.ResponseResult;
import com.my.blog.domain.entity.Article;
import com.my.blog.domain.entity.Category;
import com.my.blog.domain.vo.AddAtricleVo;
import com.my.blog.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @projectName: MyBlog
 * @package: com.my.blog.controller
 * @className: AdminArticleController
 * @author: LYH
 * @description: TODO
 * @date: 2023/6/1 16:15
 * @version: 1.0
 */
@RestController
@RequestMapping("/content/article")
public class AdminArticleController {
    @Autowired
    IArticleService articleService;

    @GetMapping("/list")
    public ResponseResult getArticleList(Integer pageNum, Integer pageSize, String title, String summary){
        ResponseResult articles = articleService.getAdminArticleList(pageNum, pageSize, title, summary);
        return  articles;
    }
    @PostMapping
    public ResponseResult add(@RequestBody AddAtricleVo article){
        return articleService.add(article);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deletArticle(@PathVariable("id") Integer[] id){
        articleService.removeById(id);
        return ResponseResult.okResult( );
    }
    @GetMapping("/{id}")
    public ResponseResult getArticle(@PathVariable("id") Integer id){
        return articleService.getArticle(id);
    }
    @PutMapping
    public ResponseResult updateArticle(@RequestBody AddAtricleVo articlevo){
        return articleService.update(articlevo);
    }
}
