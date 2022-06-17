

package com.zeryts.c2c.common.data.tenant;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zeryts
 * @date 2018-12-26
 * <p>
 * 租户维护处理器
 */
@Slf4j
public class SocialTenantHandler implements TenantLineHandler {

	@Autowired
	private SocialTenantConfigProperties properties;

	/**
	 * 获取租户 ID 值表达式，只支持单个 ID 值
	 * <p>
	 * @return 租户 ID 值表达式
	 */
	@Override
	public Expression getTenantId() {
		Integer tenantId = TenantContextHolder.getTenantId();
		log.debug("当前租户为 >> {}", tenantId);

		if (tenantId == null) {
			return new NullValue();
		}
		return new LongValue(tenantId);
	}

	/**
	 * 获取租户字段名
	 * @return 租户字段名
	 */
	@Override
	public String getTenantIdColumn() {
		return properties.getColumn();
	}

	/**
	 * 根据表名判断是否忽略拼接多租户条件
	 * <p>
	 * 默认都要进行解析并拼接多租户条件
	 * @param tableName 表名
	 * @return 是否忽略, true:表示忽略，false:需要解析并拼接多租户条件
	 */
	@Override
	public boolean ignoreTable(String tableName) {
		Integer tenantId = TenantContextHolder.getTenantId();
		// 租户中ID 为空，查询全部，不进行过滤
		if (tenantId == null) {
			return Boolean.TRUE;
		}

		return !properties.getTables().contains(tableName);
	}

}