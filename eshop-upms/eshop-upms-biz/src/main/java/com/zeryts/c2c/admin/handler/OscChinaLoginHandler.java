

package com.zeryts.c2c.admin.handler;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zeryts.c2c.admin.api.dto.UserInfo;
import com.zeryts.c2c.admin.api.entity.SysSocialDetails;
import com.zeryts.c2c.admin.api.entity.SysUser;
import com.zeryts.c2c.admin.mapper.SysSocialDetailsMapper;
import com.zeryts.c2c.admin.service.SysUserService;
import com.zeryts.c2c.common.core.constant.SecurityConstants;
import com.zeryts.c2c.common.core.constant.enums.LoginTypeEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zeryts
 * @date 2019/4/8
 * <p>
 * 开源中国登录
 */
@Slf4j
@Component("OSC")
@AllArgsConstructor
public class OscChinaLoginHandler extends AbstractLoginHandler {

	private final SysSocialDetailsMapper sysSocialDetailsMapper;

	private final SysUserService sysUserService;

	/**
	 * 开源中国传入code
	 * <p>
	 * 通过code 调用qq 获取唯一标识
	 * @param code
	 * @return
	 */
	@Override
	public String identify(String code) {
		SysSocialDetails condition = new SysSocialDetails();
		condition.setType(LoginTypeEnum.OSC.getType());
		SysSocialDetails socialDetails = sysSocialDetailsMapper.selectOne(new QueryWrapper<>(condition));

		Map<String, Object> params = new HashMap<>(8);

		params.put("client_id", socialDetails.getAppId());
		params.put("client_secret", socialDetails.getAppSecret());
		params.put("grant_type", "authorization_code");
		params.put("redirect_uri", socialDetails.getRedirectUrl());
		params.put("code", code);
		params.put("dataType", "json");

		String result = HttpUtil.post(SecurityConstants.OSC_AUTHORIZATION_CODE_URL, params);
		log.debug("开源中国响应报文:{}", result);

		String accessToken = JSONUtil.parseObj(result).getStr("access_token");

		String url = String.format(SecurityConstants.OSC_USER_INFO_URL, accessToken);
		String resp = HttpUtil.get(url);
		log.debug("开源中国获取个人信息返回报文{}", resp);

		JSONObject userInfo = JSONUtil.parseObj(resp);
		// 开源中国唯一标识
		String id = userInfo.getStr("id");
		return id;
	}

	/**
	 * identify 获取用户信息
	 * @param identify 开源中国表示
	 * @return
	 */
	@Override
	public UserInfo info(String identify) {

		SysUser user = sysUserService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getOscId, identify));

		if (user == null) {
			log.info("开源中国未绑定:{}", identify);
			return null;
		}
		return sysUserService.findUserInfo(user);
	}

}
