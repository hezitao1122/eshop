package com.eshop.inventory.manage.item.feign;

import com.eshop.inventory.common.base.BaseFeign;
import com.eshop.inventory.common.dto.ResultDto;
import com.eshop.inventory.manage.item.dto.TbItemDescDTO;
import com.eshop.inventory.manage.item.feign.rollback.ItemDescFeignRollback;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author zeryts
 * @description: 商品详情操作相关的远程调用feign
 * ```````````````````````````
 * @title: ItemFeign
 * @projectName inventory
 * @date 2019/6/18 23:05
 */
@FeignClient(name = "eshop-content", path = "/api/v1/item/desc", fallback = ItemDescFeignRollback.class)
@Component
public interface ItemDescFeign extends BaseFeign<TbItemDescDTO, Long> {

    /**
     * 功能描述: 根据itemId查找数据<br>
     * 〈〉
     *
     * @param itemIds
     * @return: com.eshop.inventory.common.dto.ResultDto<T>
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/6/15 17:33
     */
    @RequestMapping(value = "/findByItemIds", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResultDto<TbItemDescDTO> findByItemIds(@RequestBody List<Long> itemIds);
}
