package com.zeryts.c2c.admin.api.dto;

import com.zeryts.c2c.admin.api.entity.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zeryts
 * @date 2017/11/11
 */
@Data
@ApiModel(value = "用户信息")
public class UserInfo implements Serializable {

	/**
	 * 用户基本信息
	 */
	@ApiModelProperty(value = "用户基本信息")
	private SysUser sysUser;

	/**
	 * 权限标识集合
	 */
	@ApiModelProperty(value = "权限标识集合")
	private String[] permissions;

	/**
	 * 角色集合
	 */
	@ApiModelProperty(value = "角色标识集合")
	private Integer[] roles;

}
