

package com.zeryts.c2c.admin.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zeryts
 * @date 2018/8/27 前端日志vo
 */
@Data
@ApiModel(value = "前端日志展示对象")
public class PreLogVO {

	/**
	 * 请求url
	 */
	@ApiModelProperty(value = "请求url")
	private String url;

	/**
	 * 请求耗时
	 */
	@ApiModelProperty(value = "请求耗时")
	private String time;

	/**
	 * 请求用户
	 */
	@ApiModelProperty(value = "请求用户")
	private String user;

	/**
	 * 请求结果
	 */
	@ApiModelProperty(value = "请求结果0:成功9:失败")
	private String type;

	/**
	 * 请求传递参数
	 */
	@ApiModelProperty(value = "请求传递参数")
	private String message;

	/**
	 * 异常信息
	 */
	@ApiModelProperty(value = "异常信息")
	private String stack;

	/**
	 * 日志标题
	 */
	@ApiModelProperty(value = "日志标题")
	private String info;

}
