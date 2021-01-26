

package com.zeryts.c2c.common.gray;

import com.zeryts.c2c.common.gray.feign.GrayFeignRequestInterceptor;
import com.zeryts.c2c.common.gray.rule.GrayRibbonLoadBalancerRule;
import feign.RequestInterceptor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author zeryts
 * @date 2020/1/12
 */
@Configuration
@ConditionalOnProperty(value = "gray.rule.enabled", havingValue = "true")
public class GrayRibbonLoadBalancerConfiguration {

	@Bean
	@ConditionalOnMissingBean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public GrayRibbonLoadBalancerRule ribbonLoadBalancerRule() {
		return new GrayRibbonLoadBalancerRule();
	}

	@Bean
	public RequestInterceptor grayFeignRequestInterceptor() {
		return new GrayFeignRequestInterceptor();
	}

}
