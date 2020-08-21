package com.example.swagger.learn.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.swagger.learn.entity.Attribute;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeMapper extends BaseMapper<Attribute> {

    @Insert(value = " insert into category_attribute_tbl values (#{categoryId},#{attributeId}) ")
    boolean saveAttributeCategory(String categoryId,String attributeId);
}
