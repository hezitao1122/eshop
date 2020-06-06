package com.eshop.inventory.common.base.impl;

import com.eshop.inventory.common.base.ICacheService;
import com.eshop.inventory.common.enums.RedisOperateEnums;
import com.eshop.inventory.common.hystrix.redis.RedisOperateCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author zeryts
 * @description: 封装对缓存操作的一些共有操作实体类
 * ```````````````````````````
 * @title: CacheServiceImpl
 * @projectName inventory
 * @date 2019/6/15 21:57
 */
@Component
public abstract class CacheServiceImpl<T, ID> implements ICacheService<T, ID> {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public T saveLoadCache(ID id, T t) {
        RedisOperateCommand<T> command = new RedisOperateCommand<>(prefix() + id, t, redisTemplate, RedisOperateEnums.ADD);
//        redisTemplate.opsForValue().set(prefix() + id, t);
        T resT = command.execute();
        return resT;
    }

    @Override
    public T getLoadCache(ID id) {

        RedisOperateCommand<Object> command = new RedisOperateCommand<>(prefix() + id, null, redisTemplate, RedisOperateEnums.SELECT);
//        Object o = redisTemplate.opsForValue().get(prefix() + id);
//        if (o == null) {
//            return null;
//        }
        Object execute = command.execute();
        return (T) execute;
    }

    @Override
    public Boolean deleteLoadCache(ID id) {
        RedisOperateCommand<Object> command = new RedisOperateCommand<>(prefix() + id, null, redisTemplate, RedisOperateEnums.DELETE);
        try{
            command.execute();
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
