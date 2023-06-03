package com.my.blog.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.blog.dao.RoleMapper;
import com.my.blog.domain.ResponseResult;
import com.my.blog.domain.entity.Article;
import com.my.blog.domain.entity.Role;
import com.my.blog.domain.vo.ArticleListVo;
import com.my.blog.domain.vo.PageVo;
import com.my.blog.service.IRoleService;
import com.my.blog.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.my.blog.domain.SystemConstants.ARTICLE_STATUS_NORMAL;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author lyh
 * @since 2023-05-30
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Autowired
    RoleMapper roleMapper;
    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        if(id==1L){
            //是1号则直接返回admin角色
            List<String> list = new ArrayList<>();
            list.add("admin");
            return list;
        }
        //否则查询用户所具有的角色信息
        return roleMapper.selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult list(Integer pageNum, Integer pageSize, String rolename, String status) {
        Page<Role> rolePage = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(rolename!=null,Role::getRoleName,rolename)
                .like(status!=null,Role::getStatus,status)
                .orderByDesc(Role::getCreateTime);

        roleMapper.selectPage(rolePage,queryWrapper);

        PageVo pageVo = new PageVo(rolePage.getRecords(),rolePage.getTotal());
        return ResponseResult.okResult(pageVo);

    }
}
