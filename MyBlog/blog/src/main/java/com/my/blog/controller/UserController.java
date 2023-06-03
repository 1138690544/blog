package com.my.blog.controller;

import com.my.blog.log.SystemLog;
import com.my.blog.domain.ResponseResult;
import com.my.blog.domain.entity.User;
import com.my.blog.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author WH
 * @since 2023-05-16
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @GetMapping("/userInfo")
    @ResponseBody
    public ResponseResult userInfo() {
        return userService.userInfo();
    }

    @PutMapping("/userInfo")
    @ResponseBody
    @SystemLog(businessName = "更新用户信息接口")
    public ResponseResult updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseResult register(@RequestBody User user){
        return userService.register(user);
    }
}
