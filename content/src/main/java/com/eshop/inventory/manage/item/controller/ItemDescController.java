package com.eshop.inventory.manage.item.controller;

import com.eshop.inventory.common.base.BaseDBService;
import com.eshop.inventory.common.base.impl.BaseDBController;
import com.eshop.inventory.manage.item.entity.TbItemDesc;
import com.eshop.inventory.manage.item.service.ItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zeryts
 * @description: 商品描述操作的controller层
 * ```````````````````````````
 * @title: ItemDescController
 * @projectName inventory
 * @date 2019/6/17 22:19
 */
@RestController
@RequestMapping("/api/v1/item/desc")
public class ItemDescController extends BaseDBController<TbItemDesc,Long> {
    @Autowired
    private ItemDescService itemDescService;





    @Override
    public BaseDBService<TbItemDesc, Long> getDBService() {
        return itemDescService;
    }
}
