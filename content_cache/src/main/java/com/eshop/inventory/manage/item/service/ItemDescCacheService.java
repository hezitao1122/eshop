package com.eshop.inventory.manage.item.service;

import com.eshop.inventory.common.base.ICacheService;
import com.eshop.inventory.manage.item.dto.TbItemDescDTO;

/**
 * @author zeryts
 * @description: 商品描述缓存用的service层接口
 * ```````````````````````````
 * @title: ItemCacheService
 * @projectName inventory
 * @date 2019/6/1223:32
 */
public interface ItemDescCacheService extends ICacheService<TbItemDescDTO, Long> {

}
