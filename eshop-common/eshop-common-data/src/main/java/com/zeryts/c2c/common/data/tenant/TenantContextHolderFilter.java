

package com.zeryts.c2c.common.data.tenant;

import cn.hutool.core.util.StrUtil;
import com.zeryts.c2c.common.core.constant.CommonConstants;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zeryts
 * @date 2018/9/13
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TenantContextHolderFilter extends GenericFilterBean {

	@Override
	@SneakyThrows
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		String headerTenantId = request.getHeader(CommonConstants.TENANT_ID);
		String paramTenantId = request.getParameter(CommonConstants.TENANT_ID);

		log.debug("获取header中的租户ID为:{}", headerTenantId);

		if (StrUtil.isNotBlank(headerTenantId)) {
			TenantContextHolder.setTenantId(Integer.parseInt(headerTenantId));
		}
		else if (StrUtil.isNotBlank(paramTenantId)) {
			TenantContextHolder.setTenantId(Integer.parseInt(paramTenantId));
		}
		else {
			TenantContextHolder.setTenantId(CommonConstants.TENANT_ID_1);
		}

		filterChain.doFilter(request, response);
		TenantContextHolder.clear();
	}

}
