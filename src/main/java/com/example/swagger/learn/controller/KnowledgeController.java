package com.example.swagger.learn.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.swagger.learn.entity.Category;
import com.example.swagger.learn.entity.Knowledge;
import com.example.swagger.learn.service.CategoryService;
import com.example.swagger.learn.service.KnowledgeService;
import com.example.swagger.learn.util.CommonIOUtil;
import com.example.swagger.learn.util.POIUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/knowledge")
@Api(value = "知识模块")
public class KnowledgeController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private KnowledgeService knowledgeService;

    @GetMapping("/test1")
    public String test(){
        // todo 根据类目生成知识 并生成简介
        ExecutorService threadPool = Executors.newFixedThreadPool(6);

        List<Category> categories = categoryService.getBaseMapper().selectList(null);
        categories.forEach(category -> {

            threadPool.execute(()->{
                Map<String, String> map = CommonIOUtil.test7(category.getCategoryName());
                map.forEach((s,desc) -> {
                    String knowId = UUID.randomUUID().toString();
                    Knowledge knowledge = new Knowledge();
                    knowledge.setId(knowId);
                    knowledge.setKnowledgeName(s);
                    knowledge.setAdjectDescription(desc);
                    knowledgeService.save(knowledge);
                    knowledgeService.saveKnowledgeCategory(knowId,category.getId());
                });
            });
        });

        threadPool.shutdown();
        try{
            while (!threadPool.awaitTermination(100, TimeUnit.SECONDS));
        }catch (Exception e){
            e.printStackTrace();
        }

        return "success";
    }

    @GetMapping("/get/{key}")
    public Map<String,String> test2(@PathVariable String key){

       return CommonIOUtil.test10(key);

    }

    @GetMapping("/feign/{key}")
    public String testFeign(@PathVariable String key){
        return knowledgeService.readBlazegraph(key);
    }



}

