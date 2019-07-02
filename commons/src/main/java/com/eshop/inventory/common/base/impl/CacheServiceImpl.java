package com.eshop.inventory.common.base.impl;

import com.eshop.inventory.common.base.ICacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author zeryts
 * @description: 封装对缓存操作的一些共有操作实体类
 * ```````````````````````````
 * @title: CacheServiceImpl
 * @projectName inventory
 * @date 2019/6/15 21:57
 */
public abstract class CacheServiceImpl<T, ID> implements ICacheService<T, ID> {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public T saveLoadCache(ID id,T t) {
        redisTemplate.opsForValue().set(prefix()+id,t);
        return t;
    }

    @Override
    public T getLoadCache(ID id) {
        Object o = redisTemplate.opsForValue().get(prefix() + id);
        if(o == null){
            return null;
        }
        return (T)o;
    }

    @Override
    public Boolean deleteLoadCache(ID id) {
        return redisTemplate.delete(prefix() + id);
    }
}
