package com.my.blog.controller;

import com.my.blog.domain.ResponseResult;
import com.my.blog.domain.entity.Category;
import com.my.blog.domain.entity.Tag;
import com.my.blog.domain.vo.CategoryVo;
import com.my.blog.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @projectName: MyBlog
 * @package: com.my.blog.controller
 * @className: AdminCategoryController
 * @author: LYH
 * @description: TODO
 * @date: 2023/6/1 20:22
 * @version: 1.0
 */
@RestController
@RequestMapping("/content/category")
public class AdminCategoryController {
    @Autowired
    ICategoryService categoryService;
    @GetMapping("/list")
    public ResponseResult getCategorylist(Integer pageNum, Integer pageSize,String name,String status){
        return categoryService.getAdminCategoryList(pageNum,pageSize,name,status);
    }
    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        List<CategoryVo> list = categoryService.listAllCategory();
        return ResponseResult.okResult(list);
    }
    @PostMapping
    public ResponseResult addCategory(@RequestBody Category category){
        return categoryService.addCategory(category);
    }
    @GetMapping("/{id}")
    public ResponseResult getCategory(@PathVariable("id") Integer id){

        return categoryService.getCategory(id);
    }
    @PutMapping
    public ResponseResult updateCategory(@RequestBody Category category){
        return categoryService.update(category);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteCategory(@PathVariable("id") Integer[] id){
            categoryService.removeById(id);
        return ResponseResult.okResult( );
    }

}
