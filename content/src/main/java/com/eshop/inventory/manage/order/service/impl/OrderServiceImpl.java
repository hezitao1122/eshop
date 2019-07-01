package com.eshop.inventory.manage.order.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.eshop.inventory.common.base.impl.BaseDBAndCacheServiceImpl;
import com.eshop.inventory.manage.common.enums.RedisCachePrefixEnum;
import com.eshop.inventory.manage.order.entity.TbOrder;
import com.eshop.inventory.manage.order.mapper.OrderMapper;
import com.eshop.inventory.manage.order.service.OrderServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zeryts
 * @description: 订单操作的service层实现类
 * ```````````````````````````
 * @title: OrderServiceImpl
 * @projectName inventory
 * @date 2019/6/17 23:19
 */
@Service
public class OrderServiceImpl extends BaseDBAndCacheServiceImpl<TbOrder, String> implements OrderServiceI {
    @Autowired
    private OrderMapper orderMapper;


    @Override
    protected String getPrefix() {
        return RedisCachePrefixEnum.ORDER.getName();
    }

    @Override
    protected BaseMapper<TbOrder> getMapper() {
        return orderMapper;
    }
}
