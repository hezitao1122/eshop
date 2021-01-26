

package com.zeryts.c2c.admin.controller;

import cn.hutool.json.JSONArray;
import com.zeryts.c2c.admin.service.SysRouteConfService;
import com.zeryts.c2c.common.core.util.R;
import com.zeryts.c2c.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 路由
 *
 * @author zeryts
 * @date 2018-11-06 10:17:18
 */
@RestController
@AllArgsConstructor
@RequestMapping("/route")
@Api(value = "route", tags = "动态路由管理模块")
public class SysRouteConfController {

	private final SysRouteConfService sysRouteConfService;

	/**
	 * 获取当前定义的路由信息
	 * @return
	 */
	@GetMapping
	public R listRoutes() {
		return R.ok(sysRouteConfService.list());
	}

	/**
	 * 修改路由
	 * @param routes 路由定义
	 * @return
	 */
	@SysLog("修改路由")
	@PutMapping
	public R updateRoutes(@RequestBody JSONArray routes) {
		return R.ok(sysRouteConfService.updateRoutes(routes));
	}

}
