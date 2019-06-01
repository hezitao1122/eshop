package com.eshopinventory.inventory.manage.item.repository;

import com.eshopinventory.inventory.manage.item.entity.TbItemParam;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Administrator
 * @description: TbItemParam操作的JPA接口
 * ```````````````````````````
 * @title: ItemParamRepository
 * @projectName inventory
 * @date 2019/5/3021:50
 */
public interface ItemParamRepository extends JpaRepository<TbItemParam,Long> {
}
