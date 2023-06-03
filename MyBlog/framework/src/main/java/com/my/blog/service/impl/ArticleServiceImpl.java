package com.my.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.blog.dao.ArticleMapper;
import com.my.blog.dao.ArticleTagMapper;
import com.my.blog.dao.CategoryMapper;
import com.my.blog.domain.ResponseResult;
import com.my.blog.domain.entity.ArticleTag;
import com.my.blog.domain.vo.*;
import com.my.blog.domain.entity.Article;
import com.my.blog.domain.entity.Category;
import com.my.blog.service.IArticleService;
import com.my.blog.service.IArticleTagService;
import com.my.blog.utils.BeanCopyUtils;
import com.my.blog.utils.RedisCache;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.my.blog.domain.SystemConstants.ARTICLE_STATUS_NORMAL;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author WH
 * @since 2023-05-16
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {
    @Autowired
     ArticleMapper articleMapper;
    @Autowired
    ArticleTagMapper articleTagMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    RedisCache redisCache;

    @Autowired
    IArticleTagService articleTagService;
    @Autowired
    RedisTemplate redisTemplate;

    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, ARTICLE_STATUS_NORMAL).orderByDesc(Article::getViewCount);
        Page<Article> page = new Page(1, 10);
        articleMapper.selectPage(page,queryWrapper);
        List<Article> records = page.getRecords();

        List<HotArticleVo> hotArticleVos = new ArrayList<>();
        for (Article article : records){
            HotArticleVo hotArticleVo = new HotArticleVo();
            BeanUtils.copyProperties(article,hotArticleVo);
            hotArticleVos.add(hotArticleVo);
        }
        return ResponseResult.okResult(hotArticleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        Page<Article> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Article> articleQueryWrapper = new LambdaQueryWrapper<>();
        articleQueryWrapper .eq(categoryId!=0,Article::getCategoryId,categoryId)
                            .eq(Article::getStatus,ARTICLE_STATUS_NORMAL)
                            .orderByDesc(Article::getIsTop);

        articleMapper.selectPage(page,articleQueryWrapper);

        List<Article> records = page.getRecords();

        ArrayList<ArticleListVo> articleListVos = new ArrayList<>();
        for (Article article:records){
            ArticleListVo articleListVo = new ArticleListVo();
            BeanUtils.copyProperties(article,articleListVo);
            if(categoryId!=0){
                Category category = categoryMapper.selectById(categoryId);
                articleListVo.setCategoryName(category.getName());
            }
            articleListVos.add(articleListVo);
        }

        PageVo pageVo = new PageVo(articleListVos,page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        Article article = articleMapper.selectById(id);
        //count从redis中取，因为redis中数据最新
        Integer viewCount = redisCache.getCacheMapValue("viewCount", article.getId().toString());
        article.setViewCount(viewCount.longValue());

        ArticleDetailVo articleDetailVo = new ArticleDetailVo();
        BeanUtils.copyProperties(article,articleDetailVo);
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        redisTemplate.opsForHash().increment("viewCount",id.toString(),1);
        return  ResponseResult.okResult();
    }

    @Override
    public ResponseResult getAdminArticleList(Integer pageNum, Integer pageSize, String title, String summary) {
        Page<Article> page = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(title!=null,Article::getTitle,title)
                    .like(summary!=null,Article::getSummary,summary)
                    .eq(Article::getStatus,ARTICLE_STATUS_NORMAL)
                    .orderByDesc(Article::getCreateTime);

        articleMapper.selectPage(page,queryWrapper);
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult add(AddAtricleVo articlevo) {
        if(articlevo.getCategoryId()==null||articlevo.getTags().size()==0
                ||articlevo.getTitle()==null||articlevo.getContent()==null
                ||articlevo.getSummary()==null){
            throw  new RuntimeException("请填写完整");
        }

        //添加 博客
        Article article = BeanCopyUtils.copyBean(articlevo, Article.class);
        articleMapper.insert(article);

        List<ArticleTag> collect = articlevo.getTags().stream().map(tagId -> new ArticleTag(articlevo.getId(), tagId)
        ).collect(Collectors.toList());
        //添加 博客和标签的关联
        articleTagService.saveBatch(collect);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult removeById(Integer[] id) {
        List<Integer> collect = Arrays.stream(id).collect(Collectors.toList());
        for (Integer integer : collect) {
            int i = articleMapper.deleteById(integer);
            LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ArticleTag::getArticleId,integer);
            int j = articleTagMapper.delete(queryWrapper);
            if(i==0&&j==0){
                throw  new RuntimeException("删除失败");
            }
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getArticle(Integer id) {
        Article article = articleMapper.selectById(id);

        AddAtricleVo articlevo = new AddAtricleVo();
        BeanUtils.copyProperties(article,articlevo);
        //查询标签放到articlevo
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId,id);
        List<ArticleTag> articleTags = articleTagMapper.selectList(queryWrapper);
        List<Long> collect = articleTags.stream().map(articleTag -> articleTag.getTagId()).collect(Collectors.toList());
        articlevo.setTags(collect);

        return ResponseResult.okResult(articlevo);
    }

    @Override
    @Transactional
    public ResponseResult update(AddAtricleVo articlevo) {
        if(articlevo.getCategoryId()==null||articlevo.getTags().size()==0
                ||articlevo.getTitle()==null||articlevo.getContent()==null
                ||articlevo.getSummary()==null){
            throw  new RuntimeException("请填写完整");
        }
        Article article = new Article();
        BeanUtils.copyProperties(articlevo,article);
        articleMapper.updateById(article);

        //添加 博客和标签的关联
        List<ArticleTag> collect = articlevo.getTags().stream().map(s -> new ArticleTag(articlevo.getId(), s)).collect(Collectors.toList());
        LambdaQueryWrapper<ArticleTag> qw = new LambdaQueryWrapper<>();
        qw.eq(ArticleTag::getArticleId,article.getId());
        articleTagMapper.delete(qw);
        collect.stream().forEach(s->articleTagMapper.insert(s));
        return ResponseResult.okResult();
    }


}
