package com.fusiyi.service;

import com.fusiyi.domain.ResponseResult;
import com.fusiyi.domain.dto.CategoryDto;
import com.fusiyi.domain.dto.CategoryStatusDto;
import com.fusiyi.domain.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fusiyi.domain.vo.CategoryVo;

import java.util.List;

/**
* @author Fusiyi
* @description 针对表【fu_category(分类表)】的数据库操作Service
* @createDate 2022-11-22 17:11:56
*/
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    List<CategoryVo> listAllCategory();

    ResponseResult getCategoryListPage(Integer pageNum, Integer pageSize, CategoryDto categoryDto);

    ResponseResult changeCategoryStatus(CategoryStatusDto categoryStatusDto);
}
