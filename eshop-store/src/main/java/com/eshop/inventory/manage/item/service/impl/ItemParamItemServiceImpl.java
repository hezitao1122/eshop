package com.eshop.inventory.manage.item.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.eshop.inventory.common.base.impl.BaseDBAndCacheServiceImpl;
import com.eshop.inventory.manage.common.enums.RedisCachePrefixEnum;
import com.eshop.inventory.manage.item.entity.TbItemParamItem;
import com.eshop.inventory.manage.item.mapper.ItemParamItemMapper;
import com.eshop.inventory.manage.item.service.ItemParamItemServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zeryts
 * @description: 商品规格和商品的关系操作的service层实现类
 * ```````````````````````````
 * @title: ItemParamItemServiceImpl
 * @projectName inventory
 * @date 2019/6/17 22:41
 */
@Service
public class ItemParamItemServiceImpl extends BaseDBAndCacheServiceImpl<TbItemParamItem, Long> implements ItemParamItemServiceI {
    @Autowired
    private ItemParamItemMapper itemParamItemMapper;

    @Override
    protected String getPrefix() {
        return RedisCachePrefixEnum.ITEM_PARAM_ITEM.getName();
    }

    @Override
    protected BaseMapper<TbItemParamItem> getMapper() {
        return itemParamItemMapper;
    }
}
