package com.fusiyi.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.fusiyi.domain.ResponseResult;
import com.fusiyi.domain.dto.CategoryDto;
import com.fusiyi.domain.dto.CategoryStatusDto;
import com.fusiyi.domain.dto.LinkStatusDto;
import com.fusiyi.domain.entity.Category;
import com.fusiyi.domain.vo.CategoryVo;
import com.fusiyi.domain.vo.ExcelCategoryVo;
import com.fusiyi.enums.AppHttpCodeEnum;
import com.fusiyi.service.CategoryService;
import com.fusiyi.utils.BeanCopyUtils;
import com.fusiyi.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/content/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        List<CategoryVo> categoryVos = categoryService.listAllCategory();
        return ResponseResult.okResult(categoryVos);
    }

    @GetMapping("/export")
    @PreAuthorize("@ps.hasPermission('content:category:export')")
    public void export(HttpServletResponse response){
        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            //获取需要导出的数据
            List<Category> categoryVos = categoryService.list();

            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categoryVos, ExcelCategoryVo.class);
            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);

        } catch (Exception e) {
            //如果出现异常也要响应json
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }

    @GetMapping("list")
    public ResponseResult getCategoryListPage(Integer pageNum, Integer pageSize, CategoryDto categoryDto){
        return categoryService.getCategoryListPage(pageNum,pageSize,categoryDto);
    }

    @PostMapping
    public ResponseResult addCategory(@RequestBody Category category){
        return ResponseResult.okResult(categoryService.save(category));
    }

    @GetMapping("{id}")
    public ResponseResult getCategoryById(@PathVariable("id") Long id){
        return ResponseResult.okResult(categoryService.getById(id));
    }

    @PutMapping
    public ResponseResult updateCategory(@RequestBody Category category){
        return ResponseResult.okResult(categoryService.updateById(category));
    }

    @DeleteMapping("{id}")
    public ResponseResult deleteCategory(@PathVariable("id") Long id){
        return ResponseResult.okResult(categoryService.removeById(id));
    }

    @PutMapping("changeLinkStatus")
    public ResponseResult changeCategoryStatus(@RequestBody CategoryStatusDto categoryStatusDto){
        return categoryService.changeCategoryStatus(categoryStatusDto);
    }
}
