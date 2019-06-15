package com.eshop.inventory.common.base.impl;

import com.alibaba.fastjson.JSON;
import com.eshop.inventory.common.base.BaseCacheService;
import com.eshop.inventory.common.entity.BaseEntity;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public abstract class BaseDBAndCacheServiceImpl<T extends BaseEntity, ID extends Serializable> extends BaseDBServiceImpl<T,ID>  implements BaseCacheService<T,ID> {

    @Override
    public void setCache(ID id,T t) {
        log.info("===========日志===========: 将数据加入缓存中，key=[{}]" ,getPrefix()+id+", data=[{}]" , JSON.toJSONString(t));
        getRedisTemplate().opsForValue().set(id,t);
    }

    @Override
    public T getCacheById(ID id) {
        String key = getPrefix()+ id;
        T t = (T)getRedisTemplate().opsForValue().get(key);
        log.info("===========日志===========: 根据id获取到缓存，data=[{}]" , JSON.toJSONString(t));
        return t;
    }

    @Override
    public void deleteCacheById(ID id) {
        log.info("===========日志===========: 已删除redis中的缓存，key=[{}]" , getPrefix()+id);
        getRedisTemplate().delete(getPrefix()+id);
    }

    protected abstract RedisTemplate getRedisTemplate();

    protected abstract String getPrefix();


}
