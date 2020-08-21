package com.example.swagger.learn.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 *
 * @Description : Attribute表
 * @time 创建时间 : 2020-08-21
 * @author : hua
 * @Copyright (c) 2020 一碑科技
 * @version
 */
@Data
@TableName("attribute_tbl")
public class Attribute implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id")
	private String id;
	@TableField("attribute_name")
	private String attributeName;
	@TableField("attribute_desc")
	private String attributeDesc;
	@TableField("attribute_alias")
	private String attributeAlias;
	@TableField("attribute_value_type")
	private Integer attributeValueType;
	@TableField("attribute_enum")
	private Integer attributeEnum;
	@TableField("attribute_value_parity")
	private Integer attributeValueParity;
	@TableField("field_id")
	private String fieldId;

}
