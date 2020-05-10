package com.eshop.inventory.common.base.impl;

import com.alibaba.fastjson.JSON;
import com.eshop.inventory.common.base.IBaseCacheService;
import com.eshop.inventory.common.entity.BaseEntity;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;

/**
 * @author zeryts
 * @description: 即存在DB逻辑也存在Cache逻辑的service实现类
 * ```````````````````````````
 * @title: BaseDBAndCacheServiceImpl
 * @projectName inventory
 * @date 2019/6/15 17:04
 */
public abstract class BaseDBAndCacheServiceImpl<T extends BaseEntity, ID extends Serializable> extends BaseDBServiceImpl<T, ID> implements IBaseCacheService<T, ID> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(BaseDBAndCacheServiceImpl.class);
    @Autowired
    protected RedisTemplate redisTemplate;

    @Override
    public void setCache(ID id, T t) {
        log.info("===========日志===========: 将数据加入缓存中，key=[{}]", getPrefix() + id + ", data=[{}]", JSON.toJSONString(t));
        redisTemplate.opsForValue().set(id, t);
    }

    @Override
    public T getCacheById(ID id) {
        String key = getPrefix() + id;
        T t = (T) redisTemplate.opsForValue().get(key);
        log.info("===========日志===========: 根据id获取到缓存，data=[{}]", JSON.toJSONString(t));
        return t;
    }

    @Override
    public void deleteCacheById(ID id) {
        log.info("===========日志===========: 已删除redis中的缓存，key=[{}]", getPrefix() + id);
        redisTemplate.delete(getPrefix() + id);
    }

    protected abstract String getPrefix();

}
