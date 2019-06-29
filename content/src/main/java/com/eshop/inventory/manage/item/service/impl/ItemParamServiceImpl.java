package com.eshop.inventory.manage.item.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.eshop.inventory.common.base.impl.BaseDBAndCacheServiceImpl;
import com.eshop.inventory.manage.common.enums.RedisCachePrefixEnum;
import com.eshop.inventory.manage.item.entity.TbItemParam;
import com.eshop.inventory.manage.item.mapper.ItemParamMapper;
import com.eshop.inventory.manage.item.service.ItemParamServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zeryts
 * @description: 商品参数描述的service层实现类
 * ```````````````````````````
 * @title: ItemParamServiceImpl
 * @projectName inventory
 * @date 2019/6/17 22:31
 */
@Service
public class ItemParamServiceImpl extends BaseDBAndCacheServiceImpl<TbItemParam,Long> implements ItemParamServiceI {
    @Autowired
    private ItemParamMapper itemParamMapper;




    @Override
    protected String getPrefix() {
        return RedisCachePrefixEnum.ITEM_PARAM.getName();
    }

    @Override
    protected BaseMapper<TbItemParam> getMapper() {
        return itemParamMapper;
    }
}
