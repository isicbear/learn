package com.example.swagger.learn.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.swagger.learn.entity.Category;
import com.example.swagger.learn.entity.Knowledge;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

@Repository
public interface KnowledgeMapper extends BaseMapper<Knowledge> {

    @Insert(value = " insert into knowledge_category_tbl values (#{knowledgeId},#{categoryId}) ")
    boolean saveKnowledgeCategory(String knowledgeId,String categoryId);

}
