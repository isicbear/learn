package com.example.swagger.learn.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 *
 * @Description : Knowledge表
 * @time 创建时间 : 2020-08-21
 * @author : hua
 * @Copyright (c) 2020 一碑科技
 * @version
 */
@Data
@TableName("knowledge_tbl")
public class Knowledge implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id")
	private String id;
	@TableField("created_at")
	private Date createdAt;
	@TableField("created_by")
	private String createdBy;
	@TableField("deleted")
	private Boolean deleted;
	@TableField("remark")
	private String remark;
	@TableField("update_at")
	private Date updateAt;
	@TableField("update_by")
	private String updateBy;
	@TableField("version")
	private Integer version;
	@TableField("knowledge_code")
	private String knowledgeCode;
	@TableField("knowledge_description")
	private String knowledgeDescription;
	@TableField("knowledge_name")
	private String knowledgeName;
	@TableField("old_version")
	private Integer oldVersion;
	@TableField("public_version")
	private Integer publicVersion;
    /**
     * 简介
     */
	@TableField("adject_description")
	private String adjectDescription;
    /**
     * 领域名称
     */
	@TableField("partition_name")
	private String partitionName;
    /**
     * 知识类型（pedia：百科类，atlas：图谱类，text：文本类，structured：结构化）
     */
	@TableField("knowledge_type")
	private String knowledgeType;

}
