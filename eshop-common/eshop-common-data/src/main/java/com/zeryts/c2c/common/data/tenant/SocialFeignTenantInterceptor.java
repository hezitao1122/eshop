

package com.zeryts.c2c.common.data.tenant;

import com.zeryts.c2c.common.core.constant.CommonConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zeryts
 * @date 2018/9/14
 */
@Slf4j
public class SocialFeignTenantInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate requestTemplate) {
		if (TenantContextHolder.getTenantId() == null) {
			log.debug("TTL 中的 租户ID为空，feign拦截器 >> 跳过");
			return;
		}
		requestTemplate.header(CommonConstants.TENANT_ID, TenantContextHolder.getTenantId().toString());
	}

}
