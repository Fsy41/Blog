package com.fusiyi.controller;

import com.fusiyi.domain.ResponseResult;
import com.fusiyi.domain.dto.TagListDto;
import com.fusiyi.domain.entity.Tag;
import com.fusiyi.domain.vo.PageVo;
import com.fusiyi.domain.vo.TagVo;
import com.fusiyi.service.TagService;
import com.fusiyi.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }

    @PostMapping
    public ResponseResult addTag(@RequestBody TagListDto tagListDto){
        return tagService.addTag(tagListDto);
    }

    @DeleteMapping("{id}")
    public ResponseResult deleteTag(@PathVariable("id") Long id){
        return ResponseResult.okResult(tagService.removeById(id));
    }

    @GetMapping("{id}")
    public ResponseResult getTag(@PathVariable("id") Long id){
        TagVo tagVo = tagService.getTag(id);
        return ResponseResult.okResult(tagVo);
    }

    @PutMapping
    public ResponseResult updateTag(@RequestBody TagListDto tagListDto){
        return tagService.updateTag(tagListDto);
    }

    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        List<Tag> tags = tagService.list();
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(tags, TagVo.class);
        return ResponseResult.okResult(tagVos);
    }
}
