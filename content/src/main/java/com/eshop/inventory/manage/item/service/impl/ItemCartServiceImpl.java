package com.eshop.inventory.manage.item.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.eshop.inventory.common.base.impl.BaseDBAndCacheServiceImpl;
import com.eshop.inventory.manage.common.enums.RedisCachePrefixEnum;
import com.eshop.inventory.manage.item.entity.TbItemCat;
import com.eshop.inventory.manage.item.mapper.ItemCartMapper;
import com.eshop.inventory.manage.item.service.ItemCartServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zeryts
 * @description: 商品类目操作的service层实现类
 * ```````````````````````````
 * @title: ItemCartServiceImpl
 * @projectName inventory
 * @date 2019/6/17 22:14
 */
@Service
public class ItemCartServiceImpl extends BaseDBAndCacheServiceImpl<TbItemCat,Long> implements ItemCartServiceI {

    @Autowired
    private ItemCartMapper itemCartMapper;





    @Override
    protected String getPrefix() {
        return RedisCachePrefixEnum.ITEM_CART.getName();
    }

    @Override
    protected BaseMapper<TbItemCat> getMapper() {
        return itemCartMapper;
    }
}
