package com.my.blog.controller;

import com.my.blog.domain.ResponseResult;
import com.my.blog.domain.entity.Link;
import com.my.blog.domain.entity.Tag;
import com.my.blog.domain.vo.TagVo;
import com.my.blog.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @projectName: MyBlog
 * @package: com.my.blog.controller
 * @className: AdminTagController
 * @author: LYH
 * @description: TODO
 * @date: 2023/6/2 16:45
 * @version: 1.0
 */
@RestController
@RequestMapping("/content/tag")
public class AdminTagController {
    @Autowired
    ITagService tagService;
    @GetMapping("/list")
    public ResponseResult getTaglist(Integer pageNum, Integer pageSize,String name){
        return tagService.getAdminTagList(pageNum,pageSize,name);
    }
    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        List<TagVo> list = tagService.listAllTag();
        return ResponseResult.okResult(list);
    }
    @PostMapping
    public ResponseResult addTag(@RequestBody Tag tag){
        return tagService.addTag(tag);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable("id") Integer[] id){
        tagService.removeById(id);
        return ResponseResult.okResult( );
    }
    @GetMapping("/{id}")
    public ResponseResult getLink(@PathVariable("id") Integer id){
        return tagService.getTag(id);
    }
    @PutMapping
    public ResponseResult updateLink(@RequestBody Tag tag){
        return tagService.update(tag);
    }
}
