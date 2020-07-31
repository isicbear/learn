package com.example.swagger.learn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.swagger.learn.dao.UserMapper;
import com.example.swagger.learn.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {

    List<User> selectBySomething(Map<String,Object> params);

}
