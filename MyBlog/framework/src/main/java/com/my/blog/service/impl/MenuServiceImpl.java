package com.my.blog.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.blog.dao.MenuMapper;
import com.my.blog.dao.RoleMenuMapper;
import com.my.blog.domain.ResponseResult;
import com.my.blog.domain.SystemConstants;
import com.my.blog.domain.entity.Menu;
import com.my.blog.domain.entity.RoleMenu;
import com.my.blog.service.IMenuService;
import com.my.blog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author lyh
 * @since 2023-05-30
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    @Autowired
    MenuMapper menuMapper;
    @Autowired
    RoleMenuMapper roleMenuMapper;
    @Override
    public List<String> selectPermsByUserId(Long id) {
        if(id==1L){
            LambdaQueryWrapper<Menu> qw = new LambdaQueryWrapper<>();
            qw.in(Menu::getMenuType, SystemConstants.MENU,SystemConstants.BUTTON)
              .eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            List<Menu> menus = menuMapper.selectList(qw);
            //只要权限
            List<String> perms = menus.stream().map(Menu::getPerms).collect(Collectors.toList());
            return perms;
        }
        //查数据库返回权限
        return  menuMapper.selectPermsByUserId(id);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        List<Menu> menus= null;
        if(SecurityUtils.isAdmin()){
            //是管理员
            menus=menuMapper.selectAllRouterMenu();
        }
        else {
            //否则，获取当前用户所具有的Menu
            menus = menuMapper.selectRouterMenuByUserId(userId);
        }
        //构建tree
        //先找出一级菜单，然后去找他们的子菜单设置到children属性中
        List<Menu> menuTree = builderMenuTree(menus);
        return menuTree;
    }

    @Override
    public ResponseResult list(String menuName, String status) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(menuName!=null,Menu::getMenuName,menuName)
                    .like(status!=null,Menu::getStatus,status);
        List<Menu> menus = menuMapper.selectList(queryWrapper);
        return ResponseResult.okResult(menus);

    }

    @Override
    public ResponseResult add(Menu menu) {
        if(menu.getMenuType().equals("M")){
            throw  new RuntimeException("没有新的路由地址，不允许添加目录！");
        }
        menuMapper.insert(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult get(Integer id) {
        Menu menu = menuMapper.selectById(id);
        return ResponseResult.okResult(menu);
    }

    @Override
    public ResponseResult update(Menu menu) {
        menuMapper.updateById(menu);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        menuMapper.deleteById(id);
        LambdaQueryWrapper<RoleMenu> qw = new LambdaQueryWrapper<>();
        qw.eq(RoleMenu::getMenuId,id);
        roleMenuMapper.delete(qw);
    }

    @Override
    public ResponseResult selectlist() {
        List<Menu> menus = menuMapper.selectList(null);
        List<Long> collect = menus.stream().map(s -> s.getId()).collect(Collectors.toList());
        return ResponseResult.okResult(collect);

    }

    private List<Menu> builderMenuTree(List<Menu> menus) {
        List<Menu> collect = menus.stream()
                .filter(menu -> menu.getParentId().equals(0L)) //查询根节点
                .map(menu -> menu.setChildren(getChildren(menus, menu.getId())))
                .collect(Collectors.toList());
        return collect;
    }

    private List<Menu> getChildren(List<Menu> menus, Long id) {
        List<Menu> collect = menus.stream().filter(menu -> menu.getParentId().equals(id))
                .collect(Collectors.toList());
        return collect;
    }
}
