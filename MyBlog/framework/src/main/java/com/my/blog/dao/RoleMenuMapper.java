package com.my.blog.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.my.blog.domain.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色和菜单关联表 Mapper 接口
 * </p>
 *
 * @author WH
 * @since 2023-06-02
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

}
