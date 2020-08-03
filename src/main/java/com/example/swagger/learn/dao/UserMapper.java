package com.example.swagger.learn.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.swagger.learn.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper extends BaseMapper<User> {

    List<User> selectBySomething(Map<String, Object> params);

    IPage<Map<String,Object>> selectPage (Page<Map<String,Object>> page);

}
