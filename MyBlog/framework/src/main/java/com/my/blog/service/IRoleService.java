package com.my.blog.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.my.blog.domain.ResponseResult;
import com.my.blog.domain.entity.Role;

import java.util.List;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author lyh
 * @since 2023-05-30
 */
public interface IRoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);


    ResponseResult list(Integer pageNum, Integer pageSize, String rolename, String status);
}
