package com.eshop.inventory.manage.item.feign;

import com.eshop.inventory.common.base.BaseFeign;
import com.eshop.inventory.manage.item.dto.TbItemDescDTO;
import com.eshop.inventory.manage.item.feign.rollback.ItemDescFeignRollback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zeryts
 * @description: 商品详情操作相关的远程调用feign
 * ```````````````````````````
 * @title: ItemFeign
 * @projectName inventory
 * @date 2019/6/18 23:05
 */
@FeignClient(name = "eshop-content",path = "/api/v1/item/desc",fallback = ItemDescFeignRollback.class)
@Component
public interface ItemDescFeign extends BaseFeign<TbItemDescDTO,Long> {
}
