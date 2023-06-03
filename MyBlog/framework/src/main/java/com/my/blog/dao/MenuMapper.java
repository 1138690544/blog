package com.my.blog.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.my.blog.domain.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author lyh
 * @since 2023-05-30
 */
@Mapper
@Repository
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectAllRouterMenu();

    List<Menu> selectRouterMenuByUserId(Long userId);
}
