package com.my.blog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: MyBlog
 * @package: com.my.blog.domain.VO
 * @className: BlogUserLoginVo
 * @author: LYH
 * @description: TODO
 * @date: 2023/5/18 21:00
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogUserLoginVo {
    private String token;
    private UserInfoVo userInfo;
}
