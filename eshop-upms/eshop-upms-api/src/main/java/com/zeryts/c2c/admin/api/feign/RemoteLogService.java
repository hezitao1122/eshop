package com.zeryts.c2c.admin.api.feign;

import com.zeryts.c2c.admin.api.dto.SysLogDTO;
import com.zeryts.c2c.common.core.constant.SecurityConstants;
import com.zeryts.c2c.common.core.constant.ServiceNameConstants;
import com.zeryts.c2c.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author zeryts
 * @date 2018/6/28
 */
@FeignClient(contextId = "remoteLogService", value = ServiceNameConstants.UPMS_SERVICE)
public interface RemoteLogService {

	/**
	 * 保存日志
	 * @param sysLog 日志实体
	 * @param from 是否内部调用
	 * @return succes、false
	 */
	@PostMapping("/log/save")
	R<Boolean> saveLog(@RequestBody SysLogDTO sysLog, @RequestHeader(SecurityConstants.FROM) String from);

}
