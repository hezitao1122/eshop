package com.zeryts.c2c.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zeryts.c2c.admin.api.dto.SysOauthClientDetailsDTO;
import com.zeryts.c2c.admin.api.entity.SysOauthClientDetails;
import com.zeryts.c2c.admin.service.SysOauthClientDetailsService;
import com.zeryts.c2c.common.core.util.R;
import com.zeryts.c2c.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zeryts
 * @since 2018-05-15
 */
@RestController
@AllArgsConstructor
@RequestMapping("/client")
@Api(value = "client", tags = "客户端管理模块")
public class SysClientController {

	private final SysOauthClientDetailsService clientDetailsService;

	/**
	 * 通过ID查询
	 * @param clientId clientId
	 * @return SysOauthClientDetails
	 */
	@GetMapping("/{clientId}")
	public R getByClientId(@PathVariable String clientId) {
		return R.ok(clientDetailsService
				.list(Wrappers.<SysOauthClientDetails>lambdaQuery().eq(SysOauthClientDetails::getClientId, clientId)));
	}

	/**
	 * 简单分页查询
	 * @param page 分页对象
	 * @param sysOauthClientDetails 系统终端
	 * @return
	 */
	@GetMapping("/page")
	public R getOauthClientDetailsPage(Page page, SysOauthClientDetails sysOauthClientDetails) {
		return R.ok(clientDetailsService.queryPage(page, sysOauthClientDetails));
	}

	/**
	 * 添加
	 * @param clientDetailsDTO 实体
	 * @return success/false
	 */
	@SysLog("添加终端")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('sys_client_add')")
	public R add(@Valid @RequestBody SysOauthClientDetailsDTO clientDetailsDTO) {
		return R.ok(clientDetailsService.saveClient(clientDetailsDTO));
	}

	/**
	 * 删除
	 * @param clientId ID
	 * @return success/false
	 */
	@SysLog("删除终端")
	@DeleteMapping("/{clientId}")
	@PreAuthorize("@pms.hasPermission('sys_client_del')")
	public R removeById(@PathVariable String clientId) {
		return R.ok(clientDetailsService.removeByClientId(clientId));
	}

	/**
	 * 编辑
	 * @param clientDetailsDTO 实体
	 * @return success/false
	 */
	@SysLog("编辑终端")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('sys_client_edit')")
	public R update(@Valid @RequestBody SysOauthClientDetailsDTO clientDetailsDTO) {
		return R.ok(clientDetailsService.updateClientById(clientDetailsDTO));
	}

}
