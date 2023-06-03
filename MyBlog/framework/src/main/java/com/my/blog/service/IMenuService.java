package com.my.blog.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.my.blog.domain.ResponseResult;
import com.my.blog.domain.entity.Menu;

import java.util.List;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author lyh
 * @since 2023-05-30
 */
public interface IMenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);


    ResponseResult list(String menuName, String status);

    ResponseResult add(Menu menu);

    ResponseResult get(Integer id);

    ResponseResult update(Menu menu);

    void delete(Integer id);

    ResponseResult selectlist();
}
