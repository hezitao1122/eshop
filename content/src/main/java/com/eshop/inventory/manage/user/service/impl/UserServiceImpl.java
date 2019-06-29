package com.eshop.inventory.manage.user.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.eshop.inventory.common.base.impl.BaseDBAndCacheServiceImpl;
import com.eshop.inventory.manage.common.enums.RedisCachePrefixEnum;
import com.eshop.inventory.manage.user.entity.TbUser;
import com.eshop.inventory.manage.user.mapper.UserMapper;
import com.eshop.inventory.manage.user.service.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zeryts
 * @description: 用户操作的service层实现类
 * ```````````````````````````
 * @title: UserServiceImpl
 * @projectName inventory
 * @date 2019/6/17 23:36
 */
@Service
public class UserServiceImpl extends BaseDBAndCacheServiceImpl<TbUser, Long> implements UserServiceI {
    @Autowired
    private UserMapper userMapper;




    @Override
    protected String getPrefix() {
        return RedisCachePrefixEnum.USER.getName();
    }

    @Override
    protected BaseMapper<TbUser> getMapper() {
        return userMapper;
    }
}
