package com.example.swagger.learn.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 *
 * @Description : Category表
 * @time 创建时间 : 2020-08-18
 * @author : hua
 * @Copyright (c) 2020 一碑科技
 * @version
 */
@Data
@TableName("category_tbl")
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id")
	private String id;
	@TableField("category_name")
	private String categoryName;
	@TableField("field_id")
	private String fieldId;
	@TableField("category_desc")
	private String categoryDesc;
	@TableField("parent_id")
	private String parentId;

}
