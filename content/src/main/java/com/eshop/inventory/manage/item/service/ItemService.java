package com.eshop.inventory.manage.item.service;

import com.eshop.inventory.common.base.BaseCacheService;
import com.eshop.inventory.common.base.BaseDBService;
import com.eshop.inventory.manage.item.entity.TbItem;

/**
 * @author zeryts
 * @description: 商品库存操作的service层接口
 * ```````````````````````````
 * @title: ItemService
 * @projectName inventory
 * @date 2019/5/3023:27
 */
public interface ItemService extends BaseDBService<TbItem,Long>, BaseCacheService<TbItem,Long> {

    TbItem getNumById(Long itemId);

}
