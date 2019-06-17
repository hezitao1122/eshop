package com.eshop.inventory.manage.order.controller;

import com.eshop.inventory.common.base.BaseDBService;
import com.eshop.inventory.common.base.impl.BaseDBController;
import com.eshop.inventory.manage.order.entity.TbOrderItem;
import com.eshop.inventory.manage.order.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zeryts
 * @description: 订单商品操作的controller层
 * ```````````````````````````
 * @title: OrderItemController
 * @projectName inventory
 * @date 2019/6/17 23:24
 */
@RestController
@RequestMapping("/api/v1/order/item")
public class OrderItemController extends BaseDBController<TbOrderItem, String> {
    @Autowired
    private OrderItemService orderItemService;



    @Override
    public BaseDBService<TbOrderItem, String> getDBService() {
        return orderItemService;
    }
}
