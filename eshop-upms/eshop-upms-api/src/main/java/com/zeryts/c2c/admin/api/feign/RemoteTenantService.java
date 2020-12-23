package com.zeryts.c2c.admin.api.feign;

import com.zeryts.c2c.admin.api.entity.SysTenant;
import com.zeryts.c2c.common.core.constant.SecurityConstants;
import com.zeryts.c2c.common.core.constant.ServiceNameConstants;
import com.zeryts.c2c.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * @author zeryts
 * @date 2019/6/19
 * <p>
 * 租户接口
 */
@FeignClient(contextId = "remoteTenantService", value = ServiceNameConstants.UPMS_SERVICE)
public interface RemoteTenantService {

	/**
	 * 查询全部有效租户
	 * @param from 内部标志
	 * @return
	 */
	@GetMapping("/tenant/list")
	R<List<SysTenant>> list(@RequestHeader(SecurityConstants.FROM) String from);

}
