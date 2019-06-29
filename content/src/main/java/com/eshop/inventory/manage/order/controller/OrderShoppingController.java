package com.eshop.inventory.manage.order.controller;

import com.eshop.inventory.common.base.IBaseDBService;
import com.eshop.inventory.common.base.impl.BaseDBController;
import com.eshop.inventory.manage.order.entity.TbOrderShipping;
import com.eshop.inventory.manage.order.service.OrderShoppingServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zeryts
 * @description: 购物车操作的controller层
 * ```````````````````````````
 * @title: OrderShoppingController
 * @projectName inventory
 * @date 2019/6/17 23:29
 */
@RestController
@RequestMapping("/api/v1/shopping")
public class OrderShoppingController  extends BaseDBController<TbOrderShipping, String> {
    @Autowired
    private OrderShoppingServiceI orderShoppingService;



    @Override
    public IBaseDBService<TbOrderShipping, String> getDBService() {
        return orderShoppingService;
    }
}
