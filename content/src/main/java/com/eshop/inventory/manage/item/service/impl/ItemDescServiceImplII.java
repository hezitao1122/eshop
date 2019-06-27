package com.eshop.inventory.manage.item.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.eshop.inventory.common.base.impl.IIBaseDBAndCacheServiceImpl;
import com.eshop.inventory.manage.common.enums.RedisCachePrefixEnum;
import com.eshop.inventory.manage.item.entity.TbItemDesc;
import com.eshop.inventory.manage.item.mapper.ItemDescMapper;
import com.eshop.inventory.manage.item.service.ItemDescServiceII;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zeryts
 * @description: 商品描述的service层实现类
 * ```````````````````````````
 * @title: ItemDescServiceImplII
 * @projectName inventory
 * @date 2019/6/17 22:21
 */
@Service
public class ItemDescServiceImplII extends IIBaseDBAndCacheServiceImpl<TbItemDesc,Long> implements ItemDescServiceII {
    @Autowired
    private ItemDescMapper itemDescMapper;



    @Override
    protected String getPrefix() {
        return RedisCachePrefixEnum.ITEM_DESC.getName();
    }

    @Override
    protected BaseMapper<TbItemDesc> getMapper() {
        return itemDescMapper;
    }
}
