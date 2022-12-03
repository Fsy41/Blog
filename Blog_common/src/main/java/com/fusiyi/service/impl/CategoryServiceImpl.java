package com.fusiyi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fusiyi.constants.SystemConstants;
import com.fusiyi.domain.ResponseResult;
import com.fusiyi.domain.dto.CategoryDto;
import com.fusiyi.domain.dto.CategoryStatusDto;
import com.fusiyi.domain.entity.Article;
import com.fusiyi.domain.entity.Category;
import com.fusiyi.domain.entity.Link;
import com.fusiyi.domain.vo.CategoryVo;
import com.fusiyi.domain.vo.PageVo;
import com.fusiyi.service.ArticleService;
import com.fusiyi.service.CategoryService;
import com.fusiyi.mapper.CategoryMapper;
import com.fusiyi.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author Fusiyi
* @description 针对表【fu_category(分类表)】的数据库操作Service实现
* @createDate 2022-11-22 17:11:56
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService{

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        //查询文章列表，状态为已发布的文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>();
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(queryWrapper);
        //获取文章的分类id，并且去重
        Set<Long> categoryIds = articleList.stream().map(article -> article.getCategoryId())
                .collect(Collectors.toSet());
        //查询分类表
        List<Category> categories = listByIds(categoryIds);
        categories = categories.stream()
                .filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装VO
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        //返回结果
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public List<CategoryVo> listAllCategory() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getStatus,SystemConstants.STATUS_NORMAL);
        List<Category> list = list(queryWrapper);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(list, CategoryVo.class);
        return categoryVos;
    }

    @Override
    public ResponseResult getCategoryListPage(Integer pageNum, Integer pageSize, CategoryDto categoryDto) {
        //分装查询条件
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(categoryDto.getName()),Category::getName,categoryDto.getName());
        queryWrapper.eq(StringUtils.hasText(categoryDto.getStatus()),Category::getStatus,categoryDto.getStatus());
        //封装分页
        Page<Category> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult changeCategoryStatus(CategoryStatusDto categoryStatusDto) {
        UpdateWrapper<Category> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",categoryStatusDto.getId());
        updateWrapper.set("status",categoryStatusDto.getStatus());
        update(updateWrapper);
        return ResponseResult.okResult(200,"操作成功");
    }
}




