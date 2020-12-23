

package com.zeryts.c2c.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zeryts.c2c.admin.api.entity.SysTenant;

import java.util.List;

/**
 * 租户管理
 *
 * @author zeryts
 * @date 2019-05-15 15:55:41
 */
public interface SysTenantService extends IService<SysTenant> {

	/**
	 * 获取正常的租户
	 * @return
	 */
	List<SysTenant> getNormalTenant();

	/**
	 * 保存租户
	 * @param sysTenant
	 * @return
	 */
	Boolean saveTenant(SysTenant sysTenant);

}
