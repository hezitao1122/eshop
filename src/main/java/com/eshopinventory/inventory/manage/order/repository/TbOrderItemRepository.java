package com.eshopinventory.inventory.manage.order.repository;

import com.eshopinventory.inventory.manage.order.entity.TbOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Administrator
 * @description: TbOrderItem操作的JPA接口
 * ```````````````````````````
 * @title: TbOrderRepository
 * @projectName inventory
 * @date 2019/5/3021:50
 */
public interface TbOrderItemRepository extends JpaRepository<TbOrderItem,Long> {
}
