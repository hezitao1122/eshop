package com.eshop.inventory.manage.order.controller;

import com.eshop.inventory.common.base.IBaseDBService;
import com.eshop.inventory.common.base.impl.BaseDBController;
import com.eshop.inventory.manage.order.entity.TbOrder;
import com.eshop.inventory.manage.order.service.OrderServiceII;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zeryts
 * @description: 订单操作的controller层接口
 * ```````````````````````````
 * @title: OrderController
 * @projectName inventory
 * @date 2019/6/17 23:16
 */
@RestController
@RequestMapping("/api/v1/order")
public class OrderController extends BaseDBController<TbOrder, String> {
    @Autowired
    private OrderServiceII orderService;



    @Override
    public IBaseDBService<TbOrder, String> getDBService() {
        return orderService;
    }
}
