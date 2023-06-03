package com.my.blog.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.my.blog.domain.ResponseResult;
import com.my.blog.domain.entity.Tag;
import com.my.blog.domain.vo.TagVo;

import java.util.List;

/**
 * <p>
 * 标签 服务类
 * </p>
 *
 * @author WH
 * @since 2023-06-01
 */
public interface ITagService extends IService<Tag> {

    ResponseResult getAdminTagList(Integer pageNum, Integer pageSize, String name);

    ResponseResult addTag(Tag tag);
    ResponseResult removeById(Integer[] id);

    ResponseResult getTag(Integer id);

    ResponseResult update(Tag tag);

    List<TagVo> listAllTag();
}
