package com.fusiyi.controller;

import com.fusiyi.domain.ResponseResult;
import com.fusiyi.domain.dto.LinkDto;
import com.fusiyi.domain.dto.LinkStatusDto;
import com.fusiyi.domain.entity.Link;
import com.fusiyi.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("list")
    public ResponseResult getLinkList(Integer pageNum, Integer pageSize, LinkDto linkDto){
        return linkService.getLinkList(pageNum,pageSize,linkDto);
    }

    @PostMapping
    public ResponseResult addLink(@RequestBody Link link){
        return ResponseResult.okResult(linkService.save(link));
    }

    @GetMapping("{id}")
    public ResponseResult getLinkById(@PathVariable("id") Long id){
        return ResponseResult.okResult(linkService.getById(id));
    }

    @PutMapping
    public ResponseResult updateLink(@RequestBody Link link){
        return ResponseResult.okResult(linkService.updateById(link));
    }

    @DeleteMapping("{id}")
    public ResponseResult deleteLink(@PathVariable("id") Long id){
        return ResponseResult.okResult(linkService.removeById(id));
    }

    @PutMapping("changeLinkStatus")
    public ResponseResult changeLinkStatus(@RequestBody LinkStatusDto linkStatusDto){
        return linkService.changeLinkStatus(linkStatusDto);
    }

}
