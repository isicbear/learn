package com.example.swagger.learn.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.swagger.learn.entity.Category;
import com.example.swagger.learn.entity.Knowledge;
import com.example.swagger.learn.service.CategoryService;

import com.example.swagger.learn.service.KnowledgeService;
import com.example.swagger.learn.util.CommonIOUtil;
import com.example.swagger.learn.util.POIUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/category")
@Api(value = "类目模块")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private KnowledgeService knowledgeService;
    @Autowired
    private POIUtil poiUtil;

    @GetMapping("/test")
    public String test() throws Exception {

        List<String> list = poiUtil.readExcel1("C:\\Users\\32616\\Desktop\\工作簿1.xlsx");

        list.forEach(s -> {
            Category category = new Category();
            category.setId(UUID.randomUUID().toString());
            category.setCategoryName(s);
            category.setCategoryDesc(s);
            categoryService.save(category);
        });

        return "success" + list.size();
    }

    @GetMapping("/test2")
    public String test2() {
        /*Category category = new Category();
        category.setId(UUID.randomUUID().toString());
        category.setCategoryName("文学");
        category.setCategoryDesc("文学");
        boolean save = categoryService.save(category);*/
        boolean b = knowledgeService.saveKnowledgeCategory("11", "11");
        return "success" + b;
    }

    @GetMapping("/test3")
    public String test3() throws Exception {

        List<Map<String, Object>> list = poiUtil.readExcel2("C:\\Users\\32616\\Desktop\\工作簿1.xlsx");

        System.out.println(list.size());

        Map<String, Object> map = list.get(0);
        String next = map.keySet().iterator().next();

        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("category_name",next);
        Category one = categoryService.getOne(wrapper);

        Knowledge knowledge = new Knowledge();
        knowledge.setKnowledgeName(map.get(next).toString());
        QueryWrapper<Knowledge> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("entry_name",map.get(next));
        Knowledge one1 = knowledgeService.getOne(wrapper1);
        if(one1!=null){
            //
            knowledgeService.saveKnowledgeCategory(one1.getId(),one.getId());
        }else{
            knowledgeService.save(knowledge);
            Knowledge one2 = knowledgeService.getOne(wrapper1);
            knowledgeService.saveKnowledgeCategory(one2.getId(),one.getId());
        }

        return "success";
    }

    public void insertCategory(String s){
        Category category = new Category();
        category.setId(UUID.randomUUID().toString());
        category.setCategoryName(s);
        category.setCategoryDesc(s);
        categoryService.save(category);
    }

    @GetMapping("/test4")
    public String test4(){
        List<String> list = CommonIOUtil.test6();

        list.forEach(this::insertCategory);

        return "success";
    }

}

