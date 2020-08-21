package com.example.swagger.learn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.swagger.learn.dao.CategoryMapper;
import com.example.swagger.learn.dao.KnowledgeMapper;
import com.example.swagger.learn.entity.Category;
import com.example.swagger.learn.entity.Knowledge;
import com.example.swagger.learn.service.CategoryService;
import com.example.swagger.learn.service.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KnowledgeServiceImpl extends ServiceImpl<KnowledgeMapper, Knowledge> implements KnowledgeService {

    @Autowired
    private KnowledgeMapper knowledgeMapper;


    @Override
    public boolean saveKnowledgeCategory(String knowledgeId, String categoryId) {
        return knowledgeMapper.saveKnowledgeCategory(knowledgeId,categoryId);
    }
}
