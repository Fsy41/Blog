package com.fusiyi.service;

import com.fusiyi.domain.ResponseResult;
import com.fusiyi.domain.dto.TagListDto;
import com.fusiyi.domain.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fusiyi.domain.vo.PageVo;
import com.fusiyi.domain.vo.TagVo;

import java.util.List;

/**
* @author Fusiyi
* @description 针对表【fu_tag(标签)】的数据库操作Service
* @createDate 2022-11-24 14:13:04
*/
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult addTag(TagListDto tagListDto);

    TagVo getTag(Long id);

    ResponseResult updateTag(TagListDto tagListDto);
}
