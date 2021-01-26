package com.zeryts.c2c.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeryts.c2c.admin.api.entity.SysRole;
import com.zeryts.c2c.admin.api.entity.SysRoleMenu;
import com.zeryts.c2c.admin.api.vo.RoleVO;
import com.zeryts.c2c.admin.mapper.SysRoleMapper;
import com.zeryts.c2c.admin.service.SysRoleMenuService;
import com.zeryts.c2c.admin.service.SysRoleService;
import com.zeryts.c2c.common.core.constant.CacheConstants;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zeryts
 * @since 2017-10-29
 */
@Service
@AllArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

	private SysRoleMenuService roleMenuService;

	/**
	 * 通过用户ID，查询角色信息
	 * @param userId
	 * @return
	 */
	@Override
	public List findRolesByUserId(Integer userId) {
		return baseMapper.listRolesByUserId(userId);
	}

	/**
	 * 根据角色ID 查询角色列表，注意缓存删除
	 * @param roleIdList 角色ID列表
	 * @param key 缓存key
	 * @return
	 */
	@Override
	@Cacheable(value = CacheConstants.ROLE_DETAILS, key = "#key")
	public List<SysRole> findRolesByRoleIds(List<Integer> roleIdList, String key) {
		return baseMapper.selectBatchIds(roleIdList);
	}

	/**
	 * 通过角色ID，删除角色,并清空角色菜单缓存
	 * @param id
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean removeRoleById(Integer id) {
		roleMenuService.remove(Wrappers.<SysRoleMenu>update().lambda().eq(SysRoleMenu::getRoleId, id));
		return this.removeById(id);
	}

	/**
	 * 根据角色菜单列表
	 * @param roleVo 角色&菜单列表
	 * @return
	 */
	@Override
	public Boolean updateRoleMenus(RoleVO roleVo) {
		SysRole sysRole = baseMapper.selectById(roleVo.getRoleId());
		return roleMenuService.saveRoleMenus(sysRole.getRoleCode(), roleVo.getRoleId(), roleVo.getMenuIds());
	}

}
