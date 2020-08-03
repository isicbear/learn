package com.example.swagger.learn.controller;

import ch.qos.logback.core.util.TimeUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.swagger.learn.entity.User;
import com.example.swagger.learn.rabbitmq.Sender;
import com.example.swagger.learn.service.UserService;
import com.example.swagger.learn.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Api(value = "用户模块")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private Sender sender;

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

        Page<User> page = new Page<>(1,5);
        
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

    @ApiOperation("get all user by page")
    @GetMapping("/user/page")
    public JSONObject getAllUserByPage(){
        // 这种自定义的可以设置返回类型
        IPage<Map<String,Object>> userIPage = userService.selectByPage(new Page<>(0, 5));
        // 这种默认的返回类型就是实体类 这两种都要设置mybatisplusconfig配置类 否则无法实现分页工能
        //Page<User> userIPage = userService.page(new Page<>(1, 4));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result",userIPage.getRecords());
        jsonObject.put("pages",userIPage.getPages());
        jsonObject.put("total",userIPage.getTotal());
        jsonObject.put("current",userIPage.getCurrent());
        jsonObject.put("size",userIPage.getSize());
        return jsonObject ;
    }

    @ApiOperation("test redis base use")
    @GetMapping("/user/reids")
    public String testRedis(){
        boolean b = redisUtil.set("xi", "chinese wedding");
       // redisUtil.expire("xi",10, TimeUnit.SECONDS);
        System.out.println(b);
        String result = redisUtil.get("xi").toString();
        return result;
    }

    @ApiOperation("test redis publish and subscribe")
    @GetMapping("/user/reids/publis")
    public String testRedisPublish(){
        redisUtil.publishMsg();
        return "success";
    }

    @ApiOperation("test rabbitmq")
    @GetMapping("/user/rabbitmq")
    public String testRabbitMQ(){
        sender.sendDirectQueue();
        return "success";
    }
}

