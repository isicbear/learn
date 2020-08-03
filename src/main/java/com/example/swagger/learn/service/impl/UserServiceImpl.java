package com.example.swagger.learn.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.swagger.learn.dao.UserMapper;
import com.example.swagger.learn.entity.User;
import com.example.swagger.learn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> selectBySomething(Map<String, Object> params) {
        return userMapper.selectBySomething(params);
    }

    @Override
    public IPage<Map<String,Object>> selectByPage(Page<Map<String,Object>> page) {
        return userMapper.selectPage(page);
    }
}
