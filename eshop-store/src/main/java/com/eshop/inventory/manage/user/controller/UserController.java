package com.eshop.inventory.manage.user.controller;

import com.eshop.inventory.common.base.IBaseDBService;
import com.eshop.inventory.common.base.impl.BaseDBController;
import com.eshop.inventory.manage.order.entity.TbOrder;
import com.eshop.inventory.manage.user.entity.TbUser;
import com.eshop.inventory.manage.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zeryts
 * @description: 用户操作的controller层接口
 * ```````````````````````````
 * @title: UserController
 * @projectName inventory
 * @date 2019/6/17 23:35
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserController extends BaseDBController<TbUser, Long> {
    @Autowired
    private UserService service;

    @Override
    public IBaseDBService<TbUser, Long> getDBService() {
        return service;
    }
}
