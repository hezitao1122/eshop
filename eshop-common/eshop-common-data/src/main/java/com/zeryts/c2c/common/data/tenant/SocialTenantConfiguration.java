

package com.zeryts.c2c.common.data.tenant;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;

/**
 * @author zeryts
 * @date 2020/4/29
 * <p>
 * 租户信息拦截
 */
@Configuration
public class SocialTenantConfiguration {

	@Bean
	public RequestInterceptor pigxFeignTenantInterceptor() {
		return new SocialFeignTenantInterceptor();
	}

	@Bean
	public ClientHttpRequestInterceptor pigxTenantRequestInterceptor() {
		return new TenantRequestInterceptor();
	}

}
