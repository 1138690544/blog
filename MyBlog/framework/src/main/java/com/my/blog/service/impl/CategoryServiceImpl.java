package com.my.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.blog.dao.ArticleMapper;
import com.my.blog.dao.CategoryMapper;
import com.my.blog.domain.ResponseResult;
import com.my.blog.domain.SystemConstants;
import com.my.blog.domain.entity.Tag;
import com.my.blog.domain.vo.CategoryVo;
import com.my.blog.domain.entity.Article;
import com.my.blog.domain.entity.Category;
import com.my.blog.domain.vo.PageVo;
import com.my.blog.domain.vo.TagListVo;
import com.my.blog.service.ICategoryService;
import com.my.blog.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.my.blog.domain.SystemConstants.*;

/**
 * <p>
 * 分类表 服务实现类
 * </p>
 *
 * @author WH
 * @since 2023-05-16
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    ArticleMapper articleMapper;
    @Override
    public ResponseResult getCategoryList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus,ARTICLE_STATUS_NORMAL);
        List<Article> articles = articleMapper.selectList(queryWrapper);

        Set<Long> collect = articles.stream().
                map(article -> article.getCategoryId()).
                collect(Collectors.toSet());

        List<Category> categories = categoryMapper.selectBatchIds(collect);

        List<Category> collect1 = categories.stream().
                filter(category -> category.getStatus().equals(CATEGORY_STATUS_NORMAL)).
                collect(Collectors.toList());


        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(collect1, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult getAdminCategoryList(Integer pageNum, Integer pageSize, String name, String status) {
        Page<Category> categoryPage = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null,Category::getName,name)
                    .eq(status!=null,Category::getStatus,status);
        categoryMapper.selectPage(categoryPage,queryWrapper);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categoryPage.getRecords(), CategoryVo.class);

        return ResponseResult.okResult( new PageVo(categoryVos,categoryPage.getTotal()));
    }

    @Override
    public ResponseResult addCategory(Category category) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getName,category.getName());
        Category category0 = categoryMapper.selectOne(queryWrapper);
        if(category0!=null){
            throw new RuntimeException("不许重名");
        }
        if(category.getName()==null){
            throw new RuntimeException("分类名不许为空");
        }
        categoryMapper.insert(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult removeById(Integer[] id) {
        List<Integer> collect = Arrays.stream(id).collect(Collectors.toList());
        for (Integer integer : collect) {
            LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Article::getCategoryId,integer);
            List<Article> articles = articleMapper.selectList(queryWrapper);
            if(articles.size()==0){
                int i = categoryMapper.deleteById(integer);
                if(i==0){
                    throw  new RuntimeException("删除失败");
                }
            }else {
                throw  new RuntimeException("该分类下有文章,不可删除");
            }
        }
       return  ResponseResult.okResult();
    }

    @Override
    public ResponseResult getCategory(Integer id) {
        Category category = categoryMapper.selectById(id);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);

        return ResponseResult.okResult(categoryVo);
    }

    @Override
    public ResponseResult update(Category category) {
        categoryMapper.updateById(category);
        return ResponseResult.okResult();
    }

    @Override
    public List<CategoryVo> listAllCategory() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus, SystemConstants.NORMAL);
        List<Category> list = list(wrapper);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(list,
                CategoryVo.class);
        return categoryVos;
    }
}
