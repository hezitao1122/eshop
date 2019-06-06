package com.eshopinventory.inventory.manage.item.repository;

import com.eshopinventory.inventory.manage.item.entity.TbItemDesc;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Administrator
 * @description: TbItemDesc操作的JPA接口
 * ```````````````````````````
 * @title: ItemDescRepository
 * @projectName inventory
 * @date 2019/5/30 21:50
 */
public interface ItemDescRepository extends JpaRepository<TbItemDesc,Long> {
}
