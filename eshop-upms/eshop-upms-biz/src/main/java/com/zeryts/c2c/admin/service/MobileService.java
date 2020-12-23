

package com.zeryts.c2c.admin.service;

import com.zeryts.c2c.common.core.util.R;

/**
 * @author zeryts
 * @date 2018/11/14
 */
public interface MobileService {

	/**
	 * 发送手机验证码
	 * @param mobile mobile
	 * @return code
	 */
	R<Boolean> sendSmsCode(String mobile);

}
