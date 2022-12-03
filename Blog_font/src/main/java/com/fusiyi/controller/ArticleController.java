package com.fusiyi.controller;

import com.fusiyi.annotation.SystemLog;
import com.fusiyi.domain.ResponseResult;
import com.fusiyi.domain.entity.Article;
import com.fusiyi.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
@Api(description = "文章相关接口")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

   @GetMapping("/hotArticleList")
   @SystemLog(businessName = "查询热门文章")
   @ApiOperation(value = "热门文章列表",notes = "获取最多10条热门文章")
    public ResponseResult hotArticleList(){
       return articleService.hotArticleList();
   }

   @GetMapping("/articleList")
   @SystemLog(businessName = "查询文章列表")
   @ApiOperation(value = "文章列表",notes = "文章列表")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){
       return articleService.articleList(pageNum,pageSize,categoryId);
   }

   @GetMapping("/{id}")
   @SystemLog(businessName = "查看文章详情")
   @ApiOperation(value = "文章详情",notes = "文章详情")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
       return articleService.getArticleDetail(id);
   }

    @PutMapping("/updateViewCount/{id}")
    @SystemLog(businessName = "更新浏览量")
    @ApiOperation(value = "更新浏览量",notes = "更新浏览量")
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);
    }
}
