package com.my.blog.controller;

import com.my.blog.dao.UserMapper;
import com.my.blog.domain.ResponseResult;
import com.my.blog.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @projectName: MyBlog
 * @package: com.my.blog.controller
 * @className: AdminUserController
 * @author: LYH
 * @description: TODO
 * @date: 2023/6/3 17:07
 * @version: 1.0
 */
@RestController
@RequestMapping("/system/user")
public class AdminUserController {
    @Autowired
    IUserService userService;
    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, String userName, String phonenumber,String status){
        return userService.list(pageNum, pageSize, userName, phonenumber,status);
    }
}
