package com.my.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.blog.domain.entity.Article;
import com.my.blog.domain.vo.ArticleListVo;
import com.my.blog.domain.vo.PageVo;
import com.my.blog.handler.SystemException;
import com.my.blog.dao.UserMapper;
import com.my.blog.domain.ResponseResult;
import com.my.blog.domain.vo.UserInfoVo;
import com.my.blog.domain.entity.User;
import com.my.blog.enums.AppHttpCodeEnum;
import com.my.blog.service.IUserService;
import com.my.blog.utils.BeanCopyUtils;
import com.my.blog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.my.blog.domain.SystemConstants.ARTICLE_STATUS_NORMAL;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author WH
 * @since 2023-05-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult userInfo() {
        Long userId = SecurityUtils.getUserId();
        User user = userMapper.selectById(userId);
        UserInfoVo vo = BeanCopyUtils.copyBean(user,UserInfoVo.class);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        userMapper.updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {
        userNameExist(user.getUserName());
        emailExist(user.getEmail());
        nickNameExist(user.getNickName());
        String encodePassword = passwordExist(user.getPassword());
        user.setPassword(encodePassword);
        userMapper.insert(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult list(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status) {
        Page<User> page = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(userName!=null,User::getUserName,userName)
                .like(phonenumber!=null,User::getPhonenumber,phonenumber)
                .eq(status!=null,User::getStatus,status)
                .orderByDesc(User::getCreateTime);

        userMapper.selectPage(page,queryWrapper);
        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    private String passwordExist(String password) {
        if (StringUtils.isEmpty(password)){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        return passwordEncoder.encode(password);
    }

    private void nickNameExist(String nickName) {
        if (StringUtils.isEmpty(nickName)){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getNickName,nickName);
        if(userMapper.selectOne(userLambdaQueryWrapper)!=null){
            throw  new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
    }

    private void emailExist(String email) {
        if (StringUtils.isEmpty(email)){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getEmail,email);
        if(userMapper.selectOne(userLambdaQueryWrapper)!=null){
            throw  new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
    }

    private void userNameExist(String userName) {
        if (StringUtils.isEmpty(userName)){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUserName,userName);
        if(userMapper.selectOne(userLambdaQueryWrapper)!=null){
            throw  new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
    }
}
