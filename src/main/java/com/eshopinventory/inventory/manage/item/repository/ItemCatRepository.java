package com.eshopinventory.inventory.manage.item.repository;

import com.eshopinventory.inventory.manage.item.entity.TbItemCat;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Administrator
 * @description: ItemCat操作的JPA接口
 * ```````````````````````````
 * @title: ItemCatRepository
 * @projectName inventory
 * @date 2019/5/3021:50
 */
public interface ItemCatRepository extends JpaRepository<TbItemCat,Long> {
}
