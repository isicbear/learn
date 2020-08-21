package com.example.swagger.learn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.swagger.learn.dao.AttributeMapper;
import com.example.swagger.learn.entity.Attribute;
import com.example.swagger.learn.entity.Category;
import com.example.swagger.learn.service.AttributeService;
import com.example.swagger.learn.service.CategoryService;
import com.example.swagger.learn.util.CommonIOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class AttributeServiceImpl extends ServiceImpl<AttributeMapper, Attribute> implements AttributeService {

    @Autowired
    private AttributeMapper attributeMapper;
    @Autowired
    private CategoryService categoryService;

    @Transactional
    @Override
    public void insertAttribute() {
        // todo 根据类目生成属性
        ExecutorService threadPool = Executors.newFixedThreadPool(6);

        List<Category> categories = categoryService.getBaseMapper().selectList(null);
        categories.forEach(category -> {

            threadPool.execute(()->{
                List<String> list = CommonIOUtil.test8(category.getCategoryName());
                list.forEach(s -> {
                    String attrId = UUID.randomUUID().toString();
                    QueryWrapper<Attribute> wrapper = new QueryWrapper<>();
                    wrapper.eq("attribute_name",s);
                    List<Attribute> attributes = attributeMapper.selectList(wrapper);
                    if(attributes.isEmpty()){
                        Attribute attr = new Attribute();
                        attr.setId(attrId);
                        attr.setAttributeName(s);
                        attr.setAttributeDesc(s);
                        this.save(attr);
                    } else{
                        attrId = attributes.get(0).getId();
                    }
                    this.saveAttributeCategory(category.getId(),attrId);
                });
            });
        });

        threadPool.shutdown();
        try{
            while (!threadPool.awaitTermination(100, TimeUnit.SECONDS));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean saveAttributeCategory(String categoryId, String attributeId) {
        return attributeMapper.saveAttributeCategory(categoryId,attributeId);
    }
}
