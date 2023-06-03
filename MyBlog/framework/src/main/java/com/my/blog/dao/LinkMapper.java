package com.my.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.my.blog.domain.entity.Link;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 友链 Mapper 接口
 * </p>
 *
 * @author WH
 * @since 2023-05-16
 */
@Mapper
public interface LinkMapper extends BaseMapper<Link> {

}
