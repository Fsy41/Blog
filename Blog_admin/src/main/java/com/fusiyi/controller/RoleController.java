package com.fusiyi.controller;

import com.fusiyi.domain.ResponseResult;
import com.fusiyi.domain.dto.RoleDto;
import com.fusiyi.domain.dto.RoleStatusDto;
import com.fusiyi.domain.entity.Role;
import com.fusiyi.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("list")
    public ResponseResult getRoleList(Integer pageNum,Integer pageSize,RoleDto roleDto){
        return roleService.getRoleList(pageNum,pageSize,roleDto);
    }

    @PutMapping("changeStatus")
    public ResponseResult changeStatus(@RequestBody RoleStatusDto roleStatusDto){
        return roleService.changeStatus(roleStatusDto);
    }

    @PostMapping
    public ResponseResult addRole(@RequestBody Role role){
        return roleService.addRole(role);
    }

    @GetMapping("{id}")
    public ResponseResult getRoleById(@PathVariable("id") Long id){
        return ResponseResult.okResult(roleService.getById(id));
    }

    @PutMapping
    public ResponseResult updateRole(@RequestBody Role role){
        return roleService.updateRole(role);
    }

    @DeleteMapping("{id}")
    public ResponseResult deleteRole(@PathVariable Long id){
        return roleService.deleteRole(id);
    }

    @GetMapping("listAllRole")
    public ResponseResult getAllRole(){
        return roleService.getAllRole();
    }
}
