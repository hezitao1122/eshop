package com.eshopinventory.inventory.manage.item.repository;

import com.eshopinventory.inventory.manage.item.entity.TbItemParamItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Administrator
 * @description: TbItemParamItem操作的JPA接口
 * ```````````````````````````
 * @title: ItemParamItemRepository
 * @projectName inventory
 * @date 2019/5/3021:50
 */
public interface ItemParamItemRepository extends JpaRepository<TbItemParamItem,Long> {
}
