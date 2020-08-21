package com.example.swagger.learn.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.swagger.learn.dao.CategoryMapper;
import com.example.swagger.learn.dao.UserMapper;
import com.example.swagger.learn.entity.Category;
import com.example.swagger.learn.entity.User;
import com.example.swagger.learn.service.CategoryService;
import com.example.swagger.learn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

}
