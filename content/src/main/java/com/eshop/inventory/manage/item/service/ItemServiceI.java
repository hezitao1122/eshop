package com.eshop.inventory.manage.item.service;

import com.eshop.inventory.common.base.IBaseDBAndCacheService;
import com.eshop.inventory.manage.item.entity.TbItem;

/**
 * @author zeryts
 * @description: 商品库存操作的service层接口
 * ```````````````````````````
 * @title: ItemServiceI
 * @projectName inventory
 * @date 2019/5/3023:27
 */
public interface ItemServiceI extends IBaseDBAndCacheService<TbItem,Long> {

    TbItem getNumById(Long itemId);

}
