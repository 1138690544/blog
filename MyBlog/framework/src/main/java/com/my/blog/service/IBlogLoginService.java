package com.my.blog.service;

import com.my.blog.domain.ResponseResult;
import com.my.blog.domain.entity.User;
import org.springframework.stereotype.Service;

/**
 * @projectName: MyBlog
 * @package: com.my.blog.service
 * @className: IBlogLoginService
 * @author: LYH
 * @description: TODO
 * @date: 2023/5/18 20:53
 * @version: 1.0
 */
@Service
public interface IBlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
