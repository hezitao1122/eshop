

package com.zeryts.c2c.admin.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 文件管理
 *
 * @author Luckly
 * @date 2019-06-18 17:18:42
 */
@Data
@ApiModel(value = "文件")
@EqualsAndHashCode(callSuper = true)
public class SysFile extends Model<SysFile> {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId(type = IdType.AUTO)
	@ApiModelProperty(value = "文件编号")
	private Long id;

	/**
	 * 文件名
	 */
	@ApiModelProperty(value = "文件名")
	private String fileName;

	/**
	 * 原文件名
	 */
	@ApiModelProperty(value = "原始文件名")
	private String original;

	/**
	 * 容器名称
	 */
	@ApiModelProperty(value = "存储桶名称")
	private String bucketName;

	/**
	 * 文件类型
	 */
	@ApiModelProperty(value = "文件类型")
	private String type;

	/**
	 * 文件大小
	 */
	@ApiModelProperty(value = "文件大小")
	private Long fileSize;

	/**
	 * 上传人
	 */
	@ApiModelProperty(value = "创建者")
	private String createUser;

	/**
	 * 上传时间
	 */
	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 更新人
	 */
	@ApiModelProperty(value = "更新者")
	private String updateUser;

	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	private LocalDateTime updateTime;

	/**
	 * 删除标识：1-删除，0-正常
	 */
	@TableLogic
	@ApiModelProperty(value = "删除标记,1:已删除,0:正常")
	private Integer delFlag;

}
