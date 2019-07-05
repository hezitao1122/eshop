package com.eshop.inventory.manage.item.feign;

import com.eshop.inventory.common.base.BaseFeign;
import com.eshop.inventory.manage.item.dto.TbItemDTO;
import com.eshop.inventory.manage.item.feign.rollback.ItemFeignRollback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zeryts
 * @description: 商品操作相关的远程调用feign
 * ```````````````````````````
 * @title: ItemFeign
 * @projectName inventory
 * @date 2019/6/18 23:05
 */
@FeignClient(name = "eshop-content",path = "/api/v1/item",fallback = ItemFeignRollback.class)
@Component
public interface ItemFeign extends BaseFeign<TbItemDTO,Long> {
}
