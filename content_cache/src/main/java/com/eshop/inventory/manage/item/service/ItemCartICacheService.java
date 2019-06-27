package com.eshop.inventory.manage.item.service;

import com.eshop.inventory.common.base.ICacheService;
import com.eshop.inventory.manage.item.dto.TbItemCartDTO;

/**
 * @ClassName: ItemCartICacheService
 * @Title: ItemCartICacheService
 * @ProjectName: inventory
 * @Description: 商品类目缓存用的service层接口
 * @Author: hzt
 * @Date: Create in 2019/6/1814:42
 */
public interface ItemCartICacheService extends ICacheService<TbItemCartDTO,Long> {
}
