package com.my.blog.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.my.blog.domain.entity.Tag;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 标签 Mapper 接口
 * </p>
 *
 * @author WH
 * @since 2023-06-01
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

}
