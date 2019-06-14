package com.eshop.inventory.manage.item.service;

import com.eshop.inventory.common.base.CacheService;
import com.eshop.inventory.manage.item.dto.TbItemDTO;

/**
 * @author zeryts
 * @description: 商品缓存用的service层接口
 * ```````````````````````````
 * @title: ItemCacheService
 * @projectName inventory
 * @date 2019/6/1223:32
 */
public interface ItemCacheService extends CacheService<TbItemDTO,Long> {
}
