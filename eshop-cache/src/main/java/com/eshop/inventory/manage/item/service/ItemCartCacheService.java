package com.eshop.inventory.manage.item.service;

import com.eshop.inventory.common.base.ICacheService;
import com.eshop.inventory.manage.item.dto.TbItemCartDTO;

/**
 * @ClassName: ItemCartCacheService
 * @Title: ItemCartCacheService
 * @ProjectName: inventory
 * @Description: 商品类目缓存用的service层接口
 * @Author: zeryts
 * @Date: Create in 2019/6/1814:42
 */
public interface ItemCartCacheService extends ICacheService<TbItemCartDTO, Long> {
}
