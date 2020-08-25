package com.example.swagger.learn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.swagger.learn.dao.CategoryMapper;
import com.example.swagger.learn.dao.KnowledgeMapper;
import com.example.swagger.learn.entity.Category;
import com.example.swagger.learn.entity.Knowledge;
import com.example.swagger.learn.feign.BlazegraphClient;
import com.example.swagger.learn.service.CategoryService;
import com.example.swagger.learn.service.KnowledgeService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class KnowledgeServiceImpl extends ServiceImpl<KnowledgeMapper, Knowledge> implements KnowledgeService {

    @Autowired
    private KnowledgeMapper knowledgeMapper;
    @Autowired
    private BlazegraphClient blazegraphClient;

    private Gson gson = new Gson();


    @Override
    public boolean saveKnowledgeCategory(String knowledgeId, String categoryId) {
        return knowledgeMapper.saveKnowledgeCategory(knowledgeId, categoryId);
    }

    @Override
    public String readBlazegraph(String key,String namespace) {
        String queryStr = "select * {<http://scistor.com/" + key + "> ?p ?o}";
        Map<String,String> header = new HashMap<>();
        header.put("Accept","application/sparql-results+json");
        return blazegraphClient.read(queryStr,namespace,header);
    }
}
