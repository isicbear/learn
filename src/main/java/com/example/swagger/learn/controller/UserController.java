package com.example.swagger.learn.controller;

import com.example.swagger.learn.entity.User;
import com.example.swagger.learn.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@Api(value = "用户模块")
public class UserController {
    @Autowired
    private UserService userService;

    /*
    * restful  get/1
    */
    @ApiOperation("get a user")
    @GetMapping("/user/{id}")
    public String getUser(@PathVariable String id){
        User user = userService.getById(id);
        return "success \t" + user.toString();
    }

    @ApiOperation("get all user")
    @GetMapping("/user")
    public String getAllUser(){
        List<User> users = userService.list();
        return "success \t" + users.size();
    }

    @ApiOperation("add a user")
    @PostMapping("/user")
    public String addUser(@RequestBody User user){
        user.setId(UUID.randomUUID().toString());
        userService.save(user);
        return "success";
    }

    @ApiOperation("modify a user")
    @PutMapping("/user")
    public String modifyUser(@RequestBody User user){
        userService.updateById(user);
        return "success";
    }

    @ApiOperation("delete a user")
    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable String id){
        userService.removeById(id);
        return "success";
    }

    @ApiOperation("find a user by something such as age and name")
    @PostMapping("/user/queries")
    public String getUserBySomething(@RequestBody Map<String,Object> params){
        userService.selectBySomething(params);
        return "success";
    }
}

