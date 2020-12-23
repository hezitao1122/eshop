package com.zeryts.c2c.admin.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author zeryts
 * @since 2017-10-29
 */
@Data
@ApiModel(value = "角色")
@EqualsAndHashCode(callSuper = true)
public class SysRole extends Model<SysRole> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "role_id", type = IdType.AUTO)
	@ApiModelProperty(value = "角色编号")
	private Integer roleId;

	@NotBlank(message = "角色名称不能为空")
	@ApiModelProperty(value = "角色名称")
	private String roleName;

	@NotBlank(message = "角色标识不能为空")
	@ApiModelProperty(value = "角色标识")
	private String roleCode;

	@ApiModelProperty(value = "角色描述")
	private String roleDesc;

	@NotNull(message = "数据权限类型不能为空")
	@ApiModelProperty(value = "数据权限类型")
	private Integer dsType;

	/**
	 * 数据权限作用范围
	 */
	@ApiModelProperty(value = "数据权限作用范围")
	private String dsScope;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	private LocalDateTime updateTime;

	/**
	 * 删除标识（0-正常,1-删除）
	 */
	@TableLogic
	@ApiModelProperty(value = "删除标记,1:已删除,0:正常")
	private String delFlag;

}
