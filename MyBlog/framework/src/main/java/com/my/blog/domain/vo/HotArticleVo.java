package com.my.blog.domain.vo;

import lombok.Data;

/**
 * @projectName: MyBlog
 * @package: com.my.blog.domain.VO
 * @className: HotArticleVo
 * @author: LYH
 * @description: TODO
 * @date: 2023/5/16 21:02
 * @version: 1.0
 */
@Data
public class HotArticleVo {
    private Long id;
    //标题
    private String title;
    //访问量
    private Long viewCount;
}
