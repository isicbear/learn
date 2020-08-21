package com.example.swagger.learn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.swagger.learn.entity.Attribute;
import org.springframework.transaction.annotation.Transactional;

public interface AttributeService extends IService<Attribute> {

    void insertAttribute();

    boolean saveAttributeCategory(String categoryId,String attributeId);

}
