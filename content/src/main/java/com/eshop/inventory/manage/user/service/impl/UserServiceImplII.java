package com.eshop.inventory.manage.user.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.eshop.inventory.common.base.impl.IIBaseDBAndCacheServiceImpl;
import com.eshop.inventory.manage.common.enums.RedisCachePrefixEnum;
import com.eshop.inventory.manage.user.entity.TbUser;
import com.eshop.inventory.manage.user.mapper.UserMapper;
import com.eshop.inventory.manage.user.service.UserServiceII;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zeryts
 * @description: 用户操作的service层实现类
 * ```````````````````````````
 * @title: UserServiceImplII
 * @projectName inventory
 * @date 2019/6/17 23:36
 */
@Service
public class UserServiceImplII extends IIBaseDBAndCacheServiceImpl<TbUser, Long> implements UserServiceII {
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
