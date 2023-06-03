package com.my.blog.controller;

import com.my.blog.domain.ResponseResult;
import com.my.blog.domain.entity.User;
import com.my.blog.service.IBlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @projectName: MyBlog
 * @package: com.my.blog.controller
 * @className: BlogLoginController
 * @author: LYH
 * @description: TODO
 * @date: 2023/5/18 20:52
 * @version: 1.0
 */
@RestController
public class BlogLoginController {
    @Autowired
    private IBlogLoginService blogLoginService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user) {
        return blogLoginService.login(user);
    }

    @PostMapping("/logout")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }
}
