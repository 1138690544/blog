package com.my.blog.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.my.blog.domain.entity.ArticleTag;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 文章标签关联表 Mapper 接口
 * </p>
 *
 * @author WH
 * @since 2023-06-02
 */
@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

}
