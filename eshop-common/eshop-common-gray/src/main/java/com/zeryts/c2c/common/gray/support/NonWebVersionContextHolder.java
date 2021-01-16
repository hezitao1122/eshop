

package com.zeryts.c2c.common.gray.support;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.experimental.UtilityClass;

/**
 * @author zeryts
 * @date 2020-10-16
 * <p>
 * 灰度版本号传递工具 ,在非web 调用feign 传递之前手动setVersion
 */
@UtilityClass
public class NonWebVersionContextHolder {

	private final ThreadLocal<String> THREAD_LOCAL_VERSION = new TransmittableThreadLocal<>();

	/**
	 * TTL 设置版本号<br/>
	 * @param version 版本号
	 */
	public void setVersion(String version) {
		THREAD_LOCAL_VERSION.set(version);
	}

	/**
	 * 获取TTL中的版本号
	 * @return 版本 || null
	 */
	public String getVersion() {
		return THREAD_LOCAL_VERSION.get();
	}

	public void clear() {
		THREAD_LOCAL_VERSION.remove();
	}

}
