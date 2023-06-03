package com.my.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.my.blog.domain.ResponseResult;
import com.my.blog.domain.entity.Category;
import com.my.blog.domain.vo.CategoryVo;

import java.util.List;

/**
 * <p>
 * 分类表 服务类
 * </p>
 *
 * @author WH
 * @since 2023-05-16
 */
public interface ICategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    ResponseResult getAdminCategoryList(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult addCategory(Category category);
    ResponseResult removeById(Integer[] id);

    ResponseResult getCategory(Integer id);
    ResponseResult update(Category category);


    List<CategoryVo> listAllCategory();
}
