package com.zeryts.c2c.admin.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门树对象
 *
 * @author zeryts
 * @date 2020-06-19
 */
@Data
@ApiModel(value = "部门树")
@EqualsAndHashCode(callSuper = true)
public class DeptTree extends TreeNode {

	@ApiModelProperty(value = "部门名称")
	private String name;

	/**
	 * 是否显示被锁定
	 */
	private Boolean isLock = true;

}
