package com.zeryts.c2c.admin.mapper;

import com.zeryts.c2c.admin.api.entity.SysDeptRelation;
import com.zeryts.c2c.common.data.datascope.SocialBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zeryts
 * @since 2018-02-12
 */
@Mapper
public interface SysDeptRelationMapper extends SocialBaseMapper<SysDeptRelation> {

	/**
	 * 删除部门关系表数据
	 * @param id 部门ID
	 */
	void deleteDeptRelationsById(Integer id);

	/**
	 * 更改部分关系表数据
	 * @param deptRelation
	 */
	void updateDeptRelations(SysDeptRelation deptRelation);

}
