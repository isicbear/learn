package com.example.swagger.learn.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.swagger.learn.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper extends BaseMapper<User> {

    List<User> selectBySomething(Map<String, Object> params);

}
