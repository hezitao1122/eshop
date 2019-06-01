package com.eshopinventory.inventory.manage.item.repository;

import com.eshopinventory.inventory.manage.item.entity.TbItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Administrator
 * @description: TbItem操作的JPA接口
 * ```````````````````````````
 * @title: ItemRepository
 * @projectName inventory
 * @date 2019/5/3021:50
 */
public interface ItemRepository extends JpaRepository<TbItem,Long> {
}
