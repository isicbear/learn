package com.example.swagger.learn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.swagger.learn.entity.Knowledge;

public interface KnowledgeService extends IService<Knowledge> {


    boolean saveKnowledgeCategory(String knowledgeId,String categoryId);

    String readBlazegraph(String key,String namespace);

}
