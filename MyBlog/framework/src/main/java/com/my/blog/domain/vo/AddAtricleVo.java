package com.my.blog.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @projectName: MyBlog
 * @package: com.my.blog.domain.vo
 * @className: AddAtricleVo
 * @author: LYH
 * @description: TODO
 * @date: 2023/6/2 21:27
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddAtricleVo {
    private Long id;
    //标题
    private String title;
    //⽂章内容
    private String content;
    //⽂章摘要
    private String summary;
    //所属分类id
    private Long categoryId;
    //缩略图
    private String thumbnail;
    //是否置顶（0否，1是）
    private String isTop;
    //状态（0已发布，1草稿）
    private String status;
    //访问量
    private Long viewCount;
    //是否允许评论 1是，0否
    private String isComment;
    private List<Long> tags;


}
