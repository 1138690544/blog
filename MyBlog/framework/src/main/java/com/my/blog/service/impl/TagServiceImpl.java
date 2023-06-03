package com.my.blog.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.blog.dao.ArticleTagMapper;
import com.my.blog.dao.TagMapper;
import com.my.blog.domain.ResponseResult;
import com.my.blog.domain.entity.ArticleTag;
import com.my.blog.domain.entity.Link;
import com.my.blog.domain.entity.Tag;
import com.my.blog.domain.vo.LinkVo;
import com.my.blog.domain.vo.PageVo;
import com.my.blog.domain.vo.TagListVo;
import com.my.blog.domain.vo.TagVo;
import com.my.blog.service.ITagService;
import com.my.blog.utils.BeanCopyUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.my.blog.domain.SystemConstants.LINK_STATUS_NORMAL;

/**
 * <p>
 * 标签 服务实现类
 * </p>
 *
 * @author WH
 * @since 2023-06-01
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {
    @Autowired
    TagMapper tagMapper;
    @Autowired
    ArticleTagMapper articleTagMapper;
    @Override
    public ResponseResult getAdminTagList(Integer pageNum, Integer pageSize, String name) {
        Page<Tag> tagPage = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null,Tag::getName,name);
        tagMapper.selectPage(tagPage,queryWrapper);
        List<TagListVo> tagListVos = BeanCopyUtils.copyBeanList(tagPage.getRecords(), TagListVo.class);

        return ResponseResult.okResult( new PageVo(tagListVos,tagPage.getTotal()));
    }

    @Override
    public ResponseResult addTag(Tag tag) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Tag::getName,tag.getName());
        Tag tag0 = tagMapper.selectOne(queryWrapper);
        if(tag0!=null){
            throw new RuntimeException("不许重名");
        }
        if(tag.getName()==null){
            throw new RuntimeException("名称不许为空");
        }
        tagMapper.insert(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult removeById(Integer[] id) {
        //删除标签 需要先查询文章标签表 标签下需没有文章
        List<Integer> collect = Arrays.stream(id).collect(Collectors.toList());
        for (Integer integer : collect) {
            LambdaQueryWrapper<ArticleTag> qw = new LambdaQueryWrapper<>();
            qw.eq(ArticleTag::getTagId,integer);
            List<ArticleTag> articleTags = articleTagMapper.selectList(qw);
            if(articleTags.size()==0){
                int i = tagMapper.deleteById(integer);
                if(i==0){
                    throw  new RuntimeException("删除失败");
                }
            }else {
                throw new RuntimeException("该标签下有文章，无法删除");
            }
        }
        return ResponseResult.okResult();
    }

    private Integer exitById(Integer integer) {
        return integer;
    }


    @Override
    public ResponseResult getTag(Integer id) {
        Tag tag = tagMapper.selectById(id);
        TagListVo tagListVo = new TagListVo();
        BeanUtils.copyProperties(tag,tagListVo);

        return ResponseResult.okResult(tagListVo);
    }

    @Override
    public ResponseResult update(Tag tag) {
        tagMapper.updateById(tag);
        return ResponseResult.okResult();
    }

    @Override
    public List<TagVo> listAllTag() {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Tag::getId,Tag::getName);
        List<Tag> list = list(wrapper);
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(list, TagVo.class);
        return tagVos;
    }
}
