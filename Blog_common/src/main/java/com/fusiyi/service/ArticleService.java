package com.fusiyi.service;

import com.fusiyi.domain.ResponseResult;
import com.fusiyi.domain.dto.AddArticleDto;
import com.fusiyi.domain.dto.ArticleDto;
import com.fusiyi.domain.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fusiyi.domain.vo.PageVo;

/**
* @author Fusiyi
* @description 针对表【fu_article(文章表)】的数据库操作Service
* @createDate 2022-11-22 16:29:50
*/
public interface ArticleService extends IService<Article> {

    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult addArticle(AddArticleDto addArticleDto);

    ResponseResult<PageVo> getArticleList(Integer pageNum, Integer pageSize, ArticleDto articleDto);

    ResponseResult updateArticle(Article article);

    ResponseResult getArticleById(Long id);

    ResponseResult deleteArticle(Long id);
}
