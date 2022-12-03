package com.fusiyi.controller;

import com.fusiyi.domain.ResponseResult;
import com.fusiyi.domain.dto.AddArticleDto;
import com.fusiyi.domain.dto.ArticleDto;
import com.fusiyi.domain.entity.Article;
import com.fusiyi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseResult addArticle(@RequestBody AddArticleDto addArticleDto){
        return articleService.addArticle(addArticleDto);
    }

    @GetMapping("list")
    public ResponseResult getArticleList(Integer pageNum, Integer pageSize, ArticleDto articleDto){
        return articleService.getArticleList(pageNum,pageSize,articleDto);
    }

    @GetMapping("{id}")
    public ResponseResult getArticleById(@PathVariable("id") Long id){
        return articleService.getArticleById(id);
    }

    @PutMapping
    public ResponseResult updateArticle(@RequestBody Article article){
        return articleService.updateArticle(article);
    }

    @DeleteMapping("{id}")
    public ResponseResult deleteArticle(@PathVariable("id") Long id){
        return articleService.deleteArticle(id);
    }
}
