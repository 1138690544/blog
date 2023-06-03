package com.my.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.my.blog.domain.ResponseResult;
import com.my.blog.domain.entity.Link;
import com.my.blog.domain.vo.LinkVo;

/**
 * <p>
 * 友链 服务类
 * </p>
 *
 * @author WH
 * @since 2023-05-16
 */
public interface ILinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult getAdminLinkList(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult addLink(Link link);
    ResponseResult removeById(Integer[] id);
    ResponseResult getLink(Integer id);

    ResponseResult update(Link link);

}
