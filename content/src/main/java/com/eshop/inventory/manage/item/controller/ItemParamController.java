package com.eshop.inventory.manage.item.controller;

import com.eshop.inventory.common.base.BaseDBService;
import com.eshop.inventory.common.base.impl.BaseDBController;
import com.eshop.inventory.manage.item.entity.TbItemParam;
import com.eshop.inventory.manage.item.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zeryts
 * @description: 商品规则参数操作的controller层
 * ```````````````````````````
 * @title: ItemParamController
 * @projectName inventory
 * @date 2019/6/17 22:29
 */
@RestController
@RequestMapping("/api/v1/item/param")
public class ItemParamController extends BaseDBController<TbItemParam,Long> {
    @Autowired
    private ItemParamService itemParamService;


    @Override
    public BaseDBService<TbItemParam, Long> getDBService() {
        return itemParamService;
    }
}
