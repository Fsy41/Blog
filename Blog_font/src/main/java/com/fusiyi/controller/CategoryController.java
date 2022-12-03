package com.fusiyi.controller;

import com.fusiyi.annotation.SystemLog;
import com.fusiyi.domain.ResponseResult;
import com.fusiyi.service.CategoryService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@Api(description = "文章类型相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCategoryList")
    @SystemLog(businessName = "查询类型")
    public ResponseResult getCategoryList(){
        return categoryService.getCategoryList();
    }
}
