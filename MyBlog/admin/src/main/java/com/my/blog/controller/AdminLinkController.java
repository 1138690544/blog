package com.my.blog.controller;

import com.my.blog.domain.ResponseResult;
import com.my.blog.domain.entity.Category;
import com.my.blog.domain.entity.Link;
import com.my.blog.domain.vo.LinkVo;
import com.my.blog.service.ILinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @projectName: MyBlog
 * @package: com.my.blog.controller
 * @className: AdminLinkController
 * @author: LYH
 * @description: TODO
 * @date: 2023/6/2 11:10
 * @version: 1.0
 */
@RequestMapping("/content/link")
@RestController
public class AdminLinkController {
    @Autowired
    ILinkService linkService;
    @GetMapping("/list")
    public ResponseResult getLinklist(Integer pageNum, Integer pageSize,String name,String status){
        return linkService.getAdminLinkList(pageNum,pageSize,name,status);
    }
    @PostMapping
    public ResponseResult addLink(@RequestBody Link link){
        return linkService.addLink(link);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteLink(@PathVariable("id") Integer[] id){
        linkService.removeById(id);
        return ResponseResult.okResult( );
    }
    @GetMapping("/{id}")
    public ResponseResult getLink(@PathVariable("id") Integer id){
        return linkService.getLink(id);
    }
    @PutMapping
    public ResponseResult updateLink(@RequestBody Link link){
        return linkService.update(link);
    }

    @PutMapping("/changeLinkStatus")
    public ResponseResult changeLinkStatus(@RequestBody Link link){
        return linkService.update(link);
    }
}
