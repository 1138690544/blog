package com.my.blog.controller;

import com.my.blog.domain.ResponseResult;
import com.my.blog.domain.entity.Menu;
import com.my.blog.domain.vo.AddAtricleVo;
import com.my.blog.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * 菜单权限表 前端控制器
 * </p>
 *
 * @author lyh
 * @since 2023-05-30
 */
@RestController
@RequestMapping("/system/menu")
public class MenuController {
    @Autowired
    IMenuService menuService;
    @GetMapping("list")
    public ResponseResult list(String menuName,String status){
        return menuService.list(menuName,status);
    }
    @PostMapping
    public ResponseResult add(@RequestBody Menu menu){
        return menuService.add(menu);
    }
    @GetMapping("/{id}")
    public ResponseResult get(@PathVariable("id") Integer id){
        return menuService.get(id);
    }
    @PutMapping
    public ResponseResult update(@RequestBody Menu menu){
        return menuService.update(menu);
    }
    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable("id") Integer id){
        menuService.delete(id);
        return ResponseResult.okResult( );
    }
    @GetMapping("treeselect")
    public ResponseResult treeselect(){
        return menuService.selectlist();
    }
}
