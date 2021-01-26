

package com.zeryts.c2c.common.data.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.zeryts.c2c.common.data.datascope.DataScopeHandle;
import com.zeryts.c2c.common.data.datascope.DataScopeInnerInterceptor;
import com.zeryts.c2c.common.data.datascope.DataScopeSqlInjector;
import com.zeryts.c2c.common.data.datascope.SocialDefaultDatascopeHandle;
import com.zeryts.c2c.common.data.resolver.SqlFilterArgumentResolver;
import com.zeryts.c2c.common.data.tenant.SocialTenantHandler;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author zeryts
 * @date 2020-02-08
 */
@Configuration
@ConditionalOnBean(DataSource.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class MybatisPlusConfiguration implements WebMvcConfigurer {

	/**
	 * 增加请求参数解析器，对请求中的参数注入SQL 检查
	 * @param resolverList
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolverList) {
		resolverList.add(new SqlFilterArgumentResolver());
	}

	/**
	 * pigx 默认数据权限处理器
	 * @return PigxDefaultDatascopeHandle
	 */
	@Bean
	@ConditionalOnMissingBean
	public DataScopeHandle dataScopeHandle() {
		return new SocialDefaultDatascopeHandle();
	}

	/**
	 * mybatis plus 拦截器配置
	 * @return PigxDefaultDatascopeHandle
	 */
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		// 多租户支持
		TenantLineInnerInterceptor tenantLineInnerInterceptor = new TenantLineInnerInterceptor();
		tenantLineInnerInterceptor.setTenantLineHandler(pigxTenantHandler());
		interceptor.addInnerInterceptor(tenantLineInnerInterceptor);
		// 数据权限
		DataScopeInnerInterceptor dataScopeInnerInterceptor = new DataScopeInnerInterceptor();
		dataScopeInnerInterceptor.setDataScopeHandle(dataScopeHandle());
		interceptor.addInnerInterceptor(dataScopeInnerInterceptor);
		// 分页支持
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
		return interceptor;
	}

	/**
	 * 创建租户维护处理器对象
	 * @return 处理后的租户维护处理器
	 */
	@Bean
	@ConditionalOnMissingBean
	public SocialTenantHandler pigxTenantHandler() {
		return new SocialTenantHandler();
	}

	/**
	 * 扩展 mybatis-plus baseMapper 支持数据权限
	 * @return
	 */
	@Bean
	@ConditionalOnBean(DataScopeHandle.class)
	public DataScopeSqlInjector dataScopeSqlInjector() {
		return new DataScopeSqlInjector();
	}

}
