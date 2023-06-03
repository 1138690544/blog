package com.my.blog.domain;

/**
 * @projectName: MyBlog
 * @package: com.my.blog.domain
 * @className: SystemConstants
 * @author: LYH
 * @description: TODO
 * @date: 2023/5/16 21:06
 * @version: 1.0
 */
public class SystemConstants {
    /**
     * 文章是草稿状态
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;
    /**
     * 文章是正常发布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    public static final String CATEGORY_STATUS_NORMAL = "0";

    public static final String LINK_STATUS_NORMAL = "0";

    /**
     * 评论类型为：文章评论
     */
    public static final String ARTICLE_COMMENT = "0";
    /**
     * 评论类型为：友联评论
     */
    public static final String LINK_COMMENT = "1";
    /**
     * 权限类型：菜单
     */
    public static final String MENU = "C";
    /**
     * 权限类型：按钮
     */
    public static final String BUTTON = "F";
    /**
     * 权限状态：启用
     */
    public static final String STATUS_NORMAL="0";
    public static final String NORMAL = "0";
}
