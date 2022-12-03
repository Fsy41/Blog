package com.fusiyi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fusiyi.domain.ResponseResult;
import com.fusiyi.domain.dto.TagListDto;
import com.fusiyi.domain.entity.Tag;
import com.fusiyi.domain.vo.PageVo;
import com.fusiyi.domain.vo.TagVo;
import com.fusiyi.service.TagService;
import com.fusiyi.mapper.TagMapper;
import com.fusiyi.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
* @author Fusiyi
* @description 针对表【fu_tag(标签)】的数据库操作Service实现
* @createDate 2022-11-24 14:13:04
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

    @Override
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        //分页查询
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
        queryWrapper.eq(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());

        Page<Tag> page = new Page<>(pageNum,pageSize);
        page(page, queryWrapper);
        //封装数据返回
        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    @Transactional
    public ResponseResult addTag(TagListDto tagListDto) {
        Tag tag = BeanCopyUtils.copyBean(tagListDto, Tag.class);
        return ResponseResult.okResult(baseMapper.insert(tag));
    }

    @Override
    @Transactional
    public TagVo getTag(Long id) {
        Tag tag = getById(id);
        TagVo tagVo = BeanCopyUtils.copyBean(tag, TagVo.class);
        return tagVo;
    }

    @Override
    @Transactional
    public ResponseResult updateTag(TagListDto tagListDto) {
        Tag tag = BeanCopyUtils.copyBean(tagListDto, Tag.class);
        LambdaQueryWrapper<Tag> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(Tag::getId,tagListDto.getId());
        return ResponseResult.okResult(update(tag,queryWrapper));
    }
}




