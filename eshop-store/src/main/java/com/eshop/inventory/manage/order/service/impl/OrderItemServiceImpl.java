package com.eshop.inventory.manage.order.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.eshop.inventory.common.base.impl.BaseDBAndCacheServiceImpl;
import com.eshop.inventory.manage.common.enums.RedisCachePrefixEnum;
import com.eshop.inventory.manage.order.entity.TbOrderItem;
import com.eshop.inventory.manage.order.mapper.OrderItemMapper;
import com.eshop.inventory.manage.order.service.OrderItemServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zeryts
 * @description: 订单商品操作的service层实现类
 * ```````````````````````````
 * @title: OrderItemServiceImpl
 * @projectName inventory
 * @date 2019/6/17 23:26
 */
@Service
public class OrderItemServiceImpl extends BaseDBAndCacheServiceImpl<TbOrderItem, String> implements OrderItemServiceI {
    @Autowired
    private OrderItemMapper orderItemMapper;


    @Override
    protected String getPrefix() {
        return RedisCachePrefixEnum.ORDER_ITEM.getName();
    }

    @Override
    protected BaseMapper<TbOrderItem> getMapper() {
        return orderItemMapper;
    }
}
