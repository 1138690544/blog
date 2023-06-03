package com.my.blog.controller;

import com.my.blog.domain.ResponseResult;
import com.my.blog.domain.vo.AddAtricleVo;
import com.my.blog.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @projectName: MyBlog
 * @package: com.my.blog.controller
 * @className: AdminRoleController
 * @author: LYH
 * @description: TODO
 * @date: 2023/6/3 17:04
 * @version: 1.0
 */
@RestController
@RequestMapping("/system/role")
public class AdminRoleController {
    @Autowired
    private IRoleService roleService;
    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, String rolename, String status){
        return roleService.list(pageNum, pageSize, rolename, status);
    }

}
