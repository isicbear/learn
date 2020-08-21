package com.example.swagger.learn.controller;


import com.example.swagger.learn.entity.Category;
import com.example.swagger.learn.entity.Knowledge;
import com.example.swagger.learn.service.AttributeService;
import com.example.swagger.learn.service.CategoryService;
import com.example.swagger.learn.service.KnowledgeService;
import com.example.swagger.learn.util.CommonIOUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/attribute")
@Api(value = "属性模块")
public class AttributeController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AttributeService attributeService;

    @GetMapping("/test1")
    public String test(){

        attributeService.insertAttribute();

        return "success";
    }


}

