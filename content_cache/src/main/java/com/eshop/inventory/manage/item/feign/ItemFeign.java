package com.eshop.inventory.manage.item.feign;

import com.eshop.inventory.common.base.BaseFeign;
import com.eshop.inventory.manage.item.entity.TbItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zeryts
 * @description: TODO
 * ```````````````````````````
 * @title: ItemFeign
 * @projectName inventory
 * @date 2019/6/18 23:05
 */
@FeignClient(name = "eshop-content")
@RequestMapping(value = "/api/v1/item")
public interface ItemFeign extends BaseFeign<TbItem,Long> {
}
