package com.eshop.inventory.manage.content.controller;

import com.eshop.inventory.common.base.IBaseDBService;
import com.eshop.inventory.common.base.impl.BaseDBController;
import com.eshop.inventory.manage.content.entity.TbContentCategory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zeryts
 * @description: 内容分类操作的controller层
 * ```````````````````````````
 * @title: ContentController
 * @projectName inventory
 * @date 2019/6/17 22:58
 */
@RestController
@RequestMapping("/api/v1/content/category")
public class ContentCategoryController extends BaseDBController<TbContentCategory,Long> {





    @Override
    public IBaseDBService<TbContentCategory, Long> getDBService() {
        return null;
    }
}
