package com.example.swagger.learn.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.swagger.learn.entity.Category;
import com.example.swagger.learn.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CategoryMapper extends BaseMapper<Category> {


}
