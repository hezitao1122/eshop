package com.zeryts.c2c.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zeryts.c2c.admin.api.dto.UserDTO;
import com.zeryts.c2c.admin.api.entity.SysUser;
import com.zeryts.c2c.admin.api.vo.UserVO;
import com.zeryts.c2c.common.data.datascope.DataScope;
import com.zeryts.c2c.common.data.datascope.SocialBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author zeryts
 * @since 2017-10-29
 */
@Mapper
public interface SysUserMapper extends SocialBaseMapper<SysUser> {

	/**
	 * 通过用户名查询用户信息（含有角色信息）
	 * @param username 用户名
	 * @return userVo
	 */
	UserVO getUserVoByUsername(String username);

	/**
	 * 分页查询用户信息（含角色）
	 * @param page 分页
	 * @param userDTO 查询参数
	 * @param dataScope
	 * @return list
	 */
	IPage<List<UserVO>> getUserVosPage(Page page, @Param("query") UserDTO userDTO, DataScope dataScope);

	/**
	 * 通过ID查询用户信息
	 * @param id 用户ID
	 * @return userVo
	 */
	UserVO getUserVoById(Integer id);

}
