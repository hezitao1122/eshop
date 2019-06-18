package com.eshop.inventory.manage.item.service;

import com.eshop.inventory.common.base.CacheService;
import com.eshop.inventory.manage.item.dto.TbItemCartDTO;
import com.eshop.inventory.manage.item.dto.TbItemDTO;

/**
 * @ClassName: ItemCartCacheService
 * @Title: ItemCartCacheService
 * @ProjectName: inventory
 * @Description: 商品类目缓存用的service层接口
 * @Author: hzt
 * @Date: Create in 2019/6/1814:42
 */
public interface ItemCartCacheService extends CacheService<TbItemCartDTO,Long> {
}
