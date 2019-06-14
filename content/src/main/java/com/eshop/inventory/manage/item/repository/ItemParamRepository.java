package com.eshop.inventory.manage.item.repository;

import com.eshop.inventory.manage.item.entity.TbItemParam;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zeryts
 * @description: TbItemParam操作的JPA接口
 * ```````````````````````````
 * @title: ItemParamRepository
 * @projectName inventory
 * @date 2019/5/3021:50
 */
public interface ItemParamRepository extends JpaRepository<TbItemParam,Long> {
}
