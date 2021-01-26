package com.zeryts.c2c.admin.mapper;

import com.zeryts.c2c.admin.api.entity.SysRole;
import com.zeryts.c2c.common.data.datascope.SocialBaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zeryts
 * @since 2017-10-29
 */
@Mapper
public interface SysRoleMapper extends SocialBaseMapper<SysRole> {

	/**
	 * 通过用户ID，查询角色信息
	 * @param userId
	 * @return
	 */
	List<SysRole> listRolesByUserId(Integer userId);

}
