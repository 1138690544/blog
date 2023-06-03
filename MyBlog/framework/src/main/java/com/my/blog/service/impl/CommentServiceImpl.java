package com.my.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.blog.handler.SystemException;
import com.my.blog.dao.CommentMapper;
import com.my.blog.dao.UserMapper;
import com.my.blog.domain.ResponseResult;
import com.my.blog.domain.vo.CommentVo;
import com.my.blog.domain.vo.PageVo;
import com.my.blog.domain.entity.Comment;
import com.my.blog.domain.entity.User;
import com.my.blog.enums.AppHttpCodeEnum;
import com.my.blog.service.ICommentService;
import com.my.blog.utils.BeanCopyUtils;
import com.my.blog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author WH
 * @since 2023-05-16
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    UserMapper userMapper;
    @Override
    public ResponseResult commentList(String commentType,Long articleId, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(articleId != null,Comment::getArticleId,articleId)
                .eq(Comment::getRootId,-1)
                .eq(Comment::getType,commentType)
                .orderByAsc(Comment::getCreateTime);

        Page<Comment> page = new Page(pageNum, pageSize);
        commentMapper.selectPage(page, queryWrapper);

        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());

        //设置回复的评论
        for (CommentVo commentVo : commentVoList) {
            List<CommentVo> children =  getChildren(commentType,commentVo.getId());
            commentVo.setChildren(children);
        }

        PageVo pageVo = new PageVo(commentVoList, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    //设置回复评论
    private List<CommentVo> getChildren(String commentType,Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId,id)
                    .eq(Comment::getType,commentType)
                    .orderByAsc(Comment::getCreateTime);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        List<CommentVo> commentVos = toCommentVoList(comments);

        return commentVos;
    }

    //转为CommentVo且设置名字
    private List<CommentVo> toCommentVoList(List<Comment> records) {
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(records, CommentVo.class);
        for (CommentVo commentVo : commentVos) {
            Long createBy = commentVo.getCreateBy();
            User user = userMapper.selectById(createBy);
            commentVo.setUsername(user.getNickName());

            //回复的人的名字
            Long toCommentUserId = commentVo.getToCommentUserId();
            if(toCommentUserId!=-1){
                User user1 = userMapper.selectById(toCommentUserId);
                commentVo.setToCommentUserName(user1.getNickName());
            }
        }
        return commentVos;
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        if(SecurityUtils.getUserId()==null){
            throw  new SystemException(AppHttpCodeEnum.NEED_LOGIN) ;
        }
        //评论内容不能为空
        if(!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        commentMapper.insert(comment);
        return ResponseResult.okResult();
    }
}
