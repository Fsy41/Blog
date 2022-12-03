package com.fusiyi.controller;

import com.fusiyi.domain.ResponseResult;
import com.fusiyi.domain.dto.MenuDto;
import com.fusiyi.domain.entity.Menu;
import com.fusiyi.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public ResponseResult getMenuList(MenuDto menuDto){
        return menuService.getMenuList(menuDto);
    }

    @PostMapping
    public ResponseResult addMenu(@RequestBody Menu menu){
        return ResponseResult.okResult(menuService.save(menu));
    }

    @GetMapping("{id}")
    public ResponseResult getMenuById(@PathVariable("id") Long id){
        return ResponseResult.okResult(menuService.getById(id));
    }

    @PutMapping
    public ResponseResult updateMenu(@RequestBody Menu menu){
        return menuService.updateMenu(menu);
    }

    @DeleteMapping("{menuId}")
    public ResponseResult deleteMenu(@PathVariable("menuId") Long menuId){
        return menuService.deleteMenu(menuId);
    }

    @GetMapping("treeselect")
    public ResponseResult getMenuTree(){
        return menuService.getMenuTree();
    }

    @GetMapping("roleMenuTreeselect/{id}")
    public ResponseResult getMenuTreeById(@PathVariable("id") Long id){
        return menuService.getMenuTreeById(id);
    }
}
