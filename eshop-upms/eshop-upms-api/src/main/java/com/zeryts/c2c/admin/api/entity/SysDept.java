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
 * 部门管理
 * </p>
 *
 * @author zeryts
 * @since 2018-01-22
 */
@Data
@ApiModel(value = "部门")
@EqualsAndHashCode(callSuper = true)
public class SysDept extends Model<SysDept> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "dept_id", type = IdType.AUTO)
	@ApiModelProperty(value = "部门id")
	private Integer deptId;

	/**
	 * 部门名称
	 */
	@NotBlank(message = "部门名称不能为空")
	@ApiModelProperty(value = "部门名称")
	private String name;

	/**
	 * 排序
	 */
	@NotNull(message = "排序值不能为空")
	@ApiModelProperty(value = "排序值")
	private Integer sort;

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
	 * 父级部门id
	 */
	@ApiModelProperty(value = "父级部门id")
	private Integer parentId;

	/**
	 * 是否删除 1：已删除 0：正常
	 */
	@TableLogic
	@ApiModelProperty(value = "删除标记,1:已删除,0:正常")
	private String delFlag;

}
