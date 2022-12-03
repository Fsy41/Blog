package com.fusiyi.controller;

import com.fusiyi.annotation.SystemLog;
import com.fusiyi.domain.ResponseResult;
import com.fusiyi.service.LinkService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/link")
@Api(description = "友链相关接口")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/getAllLink")
    @SystemLog(businessName = "查询友链")
    public ResponseResult getAllLink(){
        return linkService.getAllLink();
    }
}
