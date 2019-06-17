package com.eshop.inventory.manage.content.controller;

import com.eshop.inventory.common.base.BaseDBService;
import com.eshop.inventory.common.base.impl.BaseDBController;
import com.eshop.inventory.manage.content.entity.TbContent;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zeryts
 * @description: 内容操作的controller层
 * ```````````````````````````
 * @title: ContentController
 * @projectName inventory
 * @date 2019/6/17 22:58
 */
@RestController
@RequestMapping("/api/v1/content")
public class ContentController extends BaseDBController<TbContent,Long> {





    @Override
    public BaseDBService<TbContent, Long> getDBService() {
        return null;
    }
}
