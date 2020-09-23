package com.eshop.inventory.manage.item.service.impl;

import com.eshop.inventory.common.base.impl.CacheServiceImpl;
import com.eshop.inventory.manage.common.enums.RedisCachePrefixEnum;
import com.eshop.inventory.manage.item.dto.TbItemDescDTO;
import com.eshop.inventory.manage.item.service.ItemDescCacheService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author zeryts
 * @description: 商品描述操作的service层实现类
 * ```````````````````````````
 * @title: ItemDescCacheServiceImpl
 * @projectName inventory
 * @date 2019/6/29 11:49
 */
@Service
public class ItemDescCacheServiceImpl extends CacheServiceImpl<TbItemDescDTO, Long> implements ItemDescCacheService {
    /**
     * redis对应商品描述信息的前缀
     */
    private final String redisPrefix = RedisCachePrefixEnum.ITEM_DESC.getName();

    @Override
    public String prefix() {
        return redisPrefix;
    }

    @Override
    @CachePut(value = "local", key = "'EHCACHE:ITEM:DESC:'+#tbItemDescDTO.itemId")
    public TbItemDescDTO saveLoadEhCache(TbItemDescDTO tbItemDescDTO) {
        return null;
    }

    @Override
    @Cacheable(value = "local", key = "'EHCACHE:ITEM:DESC:'+#id")
    public TbItemDescDTO getLoadEhCache(Long id) {
        return null;
    }

    @Override
    @CacheEvict(value = "local", key = "'EHCACHE:ITEM:DESC:'+#id")
    public void delLoadEhCache(Long id) {
    }

}
