package com.my.blog.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @projectName: MyBlog
 * @package: com.my.blog.domain.VO
 * @className: UserInfoVo
 * @author: LYH
 * @description: TODO
 * @date: 2023/5/18 21:00
 * @version: 1.0
 */
@Data
@Accessors(chain = true)
public class UserInfoVo {
    /**
     * 主键
     */
    private Long id;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像
     */
    private String avatar;
    private String sex;
    private String email;
}
