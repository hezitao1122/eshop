package com.eshop.inventory.manage.item.service;

import com.eshop.inventory.common.base.impl.CacheServiceImpl;
import com.eshop.inventory.manage.common.enums.RedisCachePrefixEnum;
import com.eshop.inventory.manage.item.dto.TbItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author zeryts
 * @description: 商品信息操作的实现类
 * ```````````````````````````
 * @title: ItemCacheServiceImpl
 * @projectName inventory
 * @date 2019/6/12 23:32
 */
@Service
public class ItemCacheServiceImpl extends CacheServiceImpl<TbItemDTO,Long> implements ItemCacheService {
    /**
     * redis对应商品信息的前缀
     */
    private final String redisPrefix = RedisCachePrefixEnum.ITEM_NUM.getName();
    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 功能描述: 将商品存入缓存中<br>
     * 〈〉
     * @param tbItemDTO
     * @return: com.eshop.inventory.manage.item.dto.TbItemDTO
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/6/12 23:55
     */
    @CachePut(value = "local", key = "'EHCACHE:ITEM:'+#tbItemDTO.id")
    public TbItemDTO saveLoadEhCache(TbItemDTO tbItemDTO) {
        return tbItemDTO;
    }
    /**
     * 功能描述: 从缓存中取商品信息，没取到就是null<br>
     * 〈〉
     * @param id 商品的id
     * @return: com.eshop.inventory.manage.item.dto.TbItemDTO
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/6/12 23:55
     */
    @Cacheable(value = "local", key = "'EHCACHE:ITEM:'+#id")
    public TbItemDTO getLoadEhCache(Long id) {
        return null;
    }

    @Override
    public RedisTemplate redisTemplate() {
        return redisTemplate;
    }

    @Override
    public String prefix() {
        return redisPrefix;
    }

}
