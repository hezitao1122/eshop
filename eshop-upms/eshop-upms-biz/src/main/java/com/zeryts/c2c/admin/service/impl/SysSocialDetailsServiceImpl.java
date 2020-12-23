

package com.zeryts.c2c.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeryts.c2c.admin.api.dto.UserInfo;
import com.zeryts.c2c.admin.api.entity.SysSocialDetails;
import com.zeryts.c2c.admin.api.entity.SysUser;
import com.zeryts.c2c.admin.handler.LoginHandler;
import com.zeryts.c2c.admin.mapper.SysSocialDetailsMapper;
import com.zeryts.c2c.admin.mapper.SysUserMapper;
import com.zeryts.c2c.admin.service.SysSocialDetailsService;
import com.zeryts.c2c.common.core.constant.CacheConstants;
import com.zeryts.c2c.common.core.constant.enums.LoginTypeEnum;
import com.zeryts.c2c.common.security.util.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author zeryts
 * @date 2018年08月16日
 */
@Slf4j
@AllArgsConstructor
@Service("sysSocialDetailsService")
public class SysSocialDetailsServiceImpl extends ServiceImpl<SysSocialDetailsMapper, SysSocialDetails>
		implements SysSocialDetailsService {

	private final Map<String, LoginHandler> loginHandlerMap;

	private final CacheManager cacheManager;

	private final SysUserMapper sysUserMapper;

	/**
	 * 绑定社交账号
	 * @param type type
	 * @param code code
	 * @return
	 */
	@Override
	public Boolean bindSocial(String type, String code) {
		String identify = loginHandlerMap.get(type).identify(code);
		SysUser sysUser = sysUserMapper.selectById(SecurityUtils.getUser().getId());

		if (LoginTypeEnum.GITEE.getType().equals(type)) {
			sysUser.setGiteeLogin(identify);
		}
		else if (LoginTypeEnum.OSC.getType().equals(type)) {
			sysUser.setOscId(identify);
		}
		else if (LoginTypeEnum.WECHAT.getType().equals(type)) {
			sysUser.setWxOpenid(identify);
		}
		else if (LoginTypeEnum.QQ.getType().equals(type)) {
			sysUser.setQqOpenid(identify);
		}
		else if (LoginTypeEnum.MINI_APP.getType().equals(type)) {
			sysUser.setMiniOpenid(identify);
		}

		sysUserMapper.updateById(sysUser);
		// 更新緩存
		cacheManager.getCache(CacheConstants.USER_DETAILS).evict(sysUser.getUsername());
		return Boolean.TRUE;
	}

	/**
	 * 根据入参查询用户信息
	 * @param inStr TYPE@code
	 * @return
	 */
	@Override
	public UserInfo getUserInfo(String inStr) {
		String[] inStrs = inStr.split(StringPool.AT);
		String type = inStrs[0];
		String loginStr = inStrs[1];
		return loginHandlerMap.get(type).handle(loginStr);
	}

}
