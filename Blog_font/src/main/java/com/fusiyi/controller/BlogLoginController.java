package com.fusiyi.controller;

import com.fusiyi.annotation.SystemLog;
import com.fusiyi.domain.ResponseResult;
import com.fusiyi.domain.entity.User;
import com.fusiyi.service.BlogLoginService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(description = "登录相关接口")
public class BlogLoginController {

    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    @SystemLog(businessName = "用户登录")
    public ResponseResult login(@RequestBody User user){
        return blogLoginService.login(user);
    }

    @PostMapping("/logout")
    @SystemLog(businessName = "退出登录")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }
}
