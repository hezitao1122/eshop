package com.zeryts.c2c.admin.api.dto;

import com.zeryts.c2c.admin.api.entity.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author zeryts
 * @date 2017/11/5
 */
@Data
@ApiModel(value = "系统用户传输对象")
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends SysUser {

	/**
	 * 角色ID
	 */
	@ApiModelProperty(value = "角色id集合")
	private List<Integer> role;

	/**
	 * 部门id
	 */
	@ApiModelProperty(value = "部门id")
	private Integer deptId;

	/**
	 * 新密码
	 */
	@ApiModelProperty(value = "新密码")
	private String newpassword1;

}
