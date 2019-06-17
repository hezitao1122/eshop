package com.eshop.inventory.manage.item.controller;

import com.eshop.inventory.common.base.BaseDBService;
import com.eshop.inventory.common.base.impl.BaseDBController;
import com.eshop.inventory.manage.item.entity.TbItemCat;
import com.eshop.inventory.manage.item.service.ItemCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zeryts
 * @description: 商品类目操作的controller层
 * ```````````````````````````
 * @title: ItemCartController
 * @projectName inventory
 * @date 2019/6/17 22:12
 */
@RestController
@RequestMapping("/api/v1/item/cart")
public class ItemCartController extends BaseDBController<TbItemCat,Long> {
    @Autowired
    private ItemCartService itemCartService;




    @Override
    public BaseDBService<TbItemCat, Long> getDBService() {
        return itemCartService;
    }
}
