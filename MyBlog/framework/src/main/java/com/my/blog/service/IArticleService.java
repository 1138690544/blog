package com.my.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.my.blog.domain.ResponseResult;
import com.my.blog.domain.entity.Article;
import com.my.blog.domain.vo.AddAtricleVo;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author WH
 * @since 2023-05-16
 */
public interface IArticleService extends IService<Article> {

    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult getAdminArticleList(Integer pageNum, Integer pageSize, String title, String summary);

    ResponseResult add(AddAtricleVo article);
    ResponseResult removeById(Integer[] id);

    ResponseResult getArticle(Integer id);


    ResponseResult update(AddAtricleVo articlevo);
}
