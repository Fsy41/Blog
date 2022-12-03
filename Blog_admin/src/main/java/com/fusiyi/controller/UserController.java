package com.fusiyi.controller;

import com.fusiyi.domain.ResponseResult;
import com.fusiyi.domain.dto.RoleStatusDto;
import com.fusiyi.domain.dto.UserDto;
import com.fusiyi.domain.dto.UserStatusDto;
import com.fusiyi.domain.entity.User;
import com.fusiyi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 查询所有用户
     * @param pageNum
     * @param pageSize
     * @param userDto
     * @return
     */
    @GetMapping("list")
    public ResponseResult getUserList(Integer pageNum, Integer pageSize, UserDto userDto){
        return userService.getUserList(pageNum,pageSize,userDto);
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @PostMapping
    public ResponseResult addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public ResponseResult deleteUser(@PathVariable("id") Long id){
        return userService.deleteUser(id);
    }

    @GetMapping("{id}")
    public ResponseResult getUserListByid(@PathVariable("id") Long id){
        return userService.getUserListById(id);
    }

    @PutMapping
    public ResponseResult updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    @PutMapping("changeStatus")
    public ResponseResult changeStatus(@RequestBody UserStatusDto userStatusDto){
        return userService.changeStatus(userStatusDto);
    }

}
