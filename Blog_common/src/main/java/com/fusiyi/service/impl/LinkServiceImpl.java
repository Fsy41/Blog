package com.fusiyi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fusiyi.constants.SystemConstants;
import com.fusiyi.domain.ResponseResult;
import com.fusiyi.domain.dto.LinkDto;
import com.fusiyi.domain.dto.LinkStatusDto;
import com.fusiyi.domain.entity.Link;
import com.fusiyi.domain.vo.LinkVo;
import com.fusiyi.domain.vo.PageVo;
import com.fusiyi.service.LinkService;
import com.fusiyi.mapper.LinkMapper;
import com.fusiyi.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
* @author Fusiyi
* @description 针对表【fu_link(友链)】的数据库操作Service实现
* @createDate 2022-11-22 19:03:10
*/
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link>
    implements LinkService{

    @Override
    public ResponseResult getAllLink() {
        //查询所有审核通过的
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(queryWrapper);
        //转换成Vo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        //封装返回
        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult getLinkList(Integer pageNum, Integer pageSize, LinkDto linkDto) {
        //封装查询条件
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(linkDto.getName()),Link::getName,linkDto.getName());
        queryWrapper.eq(StringUtils.hasText(linkDto.getStatus()),Link::getStatus,linkDto.getStatus());
        //封装分页
        Page<Link> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult changeLinkStatus(LinkStatusDto linkStatusDto) {
        UpdateWrapper<Link> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",linkStatusDto.getId());
        updateWrapper.set("status",linkStatusDto.getStatus());
        update(updateWrapper);
        return ResponseResult.okResult(200,"操作成功");
    }

}




