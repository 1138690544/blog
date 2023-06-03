package com.my.blog.controller;

import com.my.blog.domain.ResponseResult;
import com.my.blog.domain.entity.LoginUser;
import com.my.blog.domain.entity.User;
import com.my.blog.domain.vo.AdminUserInfoVo;
import com.my.blog.domain.vo.UserInfoVo;
import com.my.blog.service.IMenuService;
import com.my.blog.service.IRoleService;
import com.my.blog.utils.BeanCopyUtils;
import com.my.blog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 角色信息表 前端控制器
 * </p>
 *
 * @author lyh
 * @since 2023-05-30
 */
@RestController
public class RoleController {
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IRoleService roleService;
    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //查询权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        //查询角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());
        //获取用户信息
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        //封装成前端需要的
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms, roleKeyList, userInfoVo);
        return  ResponseResult.okResult(adminUserInfoVo);
    }
    
}
