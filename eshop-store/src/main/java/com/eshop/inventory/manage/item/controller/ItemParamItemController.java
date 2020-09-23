package com.eshop.inventory.manage.item.controller;

import com.eshop.inventory.common.base.IBaseDBService;
import com.eshop.inventory.common.base.impl.BaseDBController;
import com.eshop.inventory.manage.item.entity.TbItemParamItem;
import com.eshop.inventory.manage.item.service.ItemParamItemServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zeryts
 * @description: 商品规格和商品的关系操作controller层
 * ```````````````````````````
 * @title: ItemParamItemController
 * @projectName inventory
 * @date 2019/6/17 22:36
 */
@RestController
@RequestMapping("/api/v1/item/param/item")
public class ItemParamItemController extends BaseDBController<TbItemParamItem, Long> {
    @Autowired
    private ItemParamItemServiceI itemParamItemService;


    @Override
    public IBaseDBService<TbItemParamItem, Long> getDBService() {
        return itemParamItemService;
    }
}
