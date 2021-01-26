

package com.zeryts.c2c.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zeryts.c2c.admin.api.entity.SysPublicParam;
import com.zeryts.c2c.admin.service.SysPublicParamService;
import com.zeryts.c2c.common.core.util.R;
import com.zeryts.c2c.common.log.annotation.SysLog;
import com.zeryts.c2c.common.security.annotation.Inner;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 公共参数
 *
 * @author Lucky
 * @date 2019-04-29
 */
@RestController
@AllArgsConstructor
@RequestMapping("/param")
@Api(value = "param", tags = "公共参数配置")
public class SysPublicParamController {

	private final SysPublicParamService sysPublicParamService;

	/**
	 * 通过key查询公共参数值
	 * @param publicKey
	 * @return
	 */
	@Inner(value = false)
	@ApiOperation(value = "查询公共参数值", notes = "根据key查询公共参数值")
	@GetMapping("/publicValue/{publicKey}")
	public R publicKey(@PathVariable("publicKey") String publicKey) {
		return R.ok(sysPublicParamService.getSysPublicParamKeyToValue(publicKey));
	}

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param sysPublicParam 公共参数
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getSysPublicParamPage(Page page, SysPublicParam sysPublicParam) {
		return R.ok(sysPublicParamService.page(page, Wrappers.query(sysPublicParam)));
	}

	/**
	 * 通过id查询公共参数
	 * @param publicId id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询公共参数", notes = "通过id查询公共参数")
	@GetMapping("/{publicId}")
	public R getById(@PathVariable("publicId") Long publicId) {
		return R.ok(sysPublicParamService.getById(publicId));
	}

	/**
	 * 新增公共参数
	 * @param sysPublicParam 公共参数
	 * @return R
	 */
	@ApiOperation(value = "新增公共参数", notes = "新增公共参数")
	@SysLog("新增公共参数")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('admin_syspublicparam_add')")
	public R save(@RequestBody SysPublicParam sysPublicParam) {
		return R.ok(sysPublicParamService.save(sysPublicParam));
	}

	/**
	 * 修改公共参数
	 * @param sysPublicParam 公共参数
	 * @return R
	 */
	@ApiOperation(value = "修改公共参数", notes = "修改公共参数")
	@SysLog("修改公共参数")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('admin_syspublicparam_edit')")
	public R updateById(@RequestBody SysPublicParam sysPublicParam) {
		return sysPublicParamService.updateParam(sysPublicParam);
	}

	/**
	 * 通过id删除公共参数
	 * @param publicId id
	 * @return R
	 */
	@ApiOperation(value = "删除公共参数", notes = "删除公共参数")
	@SysLog("删除公共参数")
	@DeleteMapping("/{publicId}")
	@PreAuthorize("@pms.hasPermission('admin_syspublicparam_del')")
	public R removeById(@PathVariable Long publicId) {
		return sysPublicParamService.removeParam(publicId);
	}

}
