package com.my.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.blog.dao.LinkMapper;
import com.my.blog.domain.ResponseResult;
import com.my.blog.domain.entity.Category;
import com.my.blog.domain.vo.CategoryVo;
import com.my.blog.domain.vo.LinkVo;
import com.my.blog.domain.entity.Link;
import com.my.blog.domain.vo.PageVo;
import com.my.blog.service.ILinkService;
import com.my.blog.utils.BeanCopyUtils;
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
 * 友链 服务实现类
 * </p>
 *
 * @author WH
 * @since 2023-05-16
 */
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements ILinkService {
    @Autowired
    LinkMapper linkMapper;

    @Override
    public ResponseResult getAllLink() {
        LambdaQueryWrapper<Link> linkLambdaQueryWrapper = new LambdaQueryWrapper<>();
        linkLambdaQueryWrapper.eq(Link::getStatus,LINK_STATUS_NORMAL);
        List<Link> links = linkMapper.selectList(linkLambdaQueryWrapper);
        ArrayList<LinkVo> linkVos = new ArrayList<>();
        for(Link link:links){
            LinkVo linkVo = new LinkVo();
            BeanUtils.copyProperties(link,linkVo);
            linkVos.add(linkVo);
        }
        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult getAdminLinkList(Integer pageNum, Integer pageSize, String name, String status) {
        Page<Link> linkPage = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null,Link::getName,name)
                .eq(status!=null,Link::getStatus,status);
        linkMapper.selectPage(linkPage,queryWrapper);
        List<LinkVo> categoryVos = BeanCopyUtils.copyBeanList(linkPage.getRecords(), LinkVo.class);

        return ResponseResult.okResult( new PageVo(categoryVos,linkPage.getTotal()));
    }

    @Override
    public ResponseResult addLink(Link link) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getName,link.getName());
        Link link0 = linkMapper.selectOne(queryWrapper);
        if(link0!=null){
            throw new RuntimeException("不许重名");
        }
        if(link.getName()==null){
            throw new RuntimeException("名称不许为空");
        }
        if(link.getAddress()==null){
            throw new RuntimeException("地址不许为空");
        }
        linkMapper.insert(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult removeById(Integer[] id) {
        List<Integer> collect = Arrays.stream(id).collect(Collectors.toList());
        int i = linkMapper.deleteBatchIds(collect);
        if(i>0){
            return ResponseResult.okResult();
        }else {
            throw  new RuntimeException("删除失败");
        }
    }

    @Override
    public ResponseResult getLink(Integer id) {
        Link link = linkMapper.selectById(id);
        LinkVo linkVo = new LinkVo();
        BeanUtils.copyProperties(link,linkVo);

        return ResponseResult.okResult(linkVo);
    }

    @Override
    public ResponseResult update(Link link) {
        linkMapper.updateById(link);
        return ResponseResult.okResult();
    }


}
