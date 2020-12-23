package com.zeryts.c2c.admin.mapper;

import com.zeryts.c2c.admin.api.entity.SysMenu;
import com.zeryts.c2c.admin.api.vo.MenuVO;
import com.zeryts.c2c.common.data.datascope.SocialBaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author zeryts
 * @since 2017-10-29
 */
@Mapper
public interface SysMenuMapper extends SocialBaseMapper<SysMenu> {

	/**
	 * 通过角色编号查询菜单
	 * @param roleId 角色ID
	 * @return
	 */
	List<MenuVO> listMenusByRoleId(Integer roleId);

	/**
	 * 通过角色ID查询权限
	 * @param roleIds Ids
	 * @return
	 */
	List<String> listPermissionsByRoleIds(String roleIds);

}
