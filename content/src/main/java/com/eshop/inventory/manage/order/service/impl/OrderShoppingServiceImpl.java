package com.eshop.inventory.manage.order.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.eshop.inventory.common.base.impl.BaseDBAndCacheServiceImpl;
import com.eshop.inventory.manage.order.entity.TbOrderShipping;
import com.eshop.inventory.manage.order.service.OrderShoppingService;
import org.springframework.stereotype.Service;

/**
 * @author zeryts
 * @description: 购物车操作的service层实现类
 * ```````````````````````````
 * @title: OrderShoppingServiceImpl
 * @projectName inventory
 * @date 2019/6/17 23:31
 */
@Service
public class OrderShoppingServiceImpl extends BaseDBAndCacheServiceImpl<TbOrderShipping, String> implements OrderShoppingService {

    @Override
    protected String getPrefix() {
        return null;
    }

    @Override
    protected BaseMapper<TbOrderShipping> getMapper() {
        return null;
    }
}
