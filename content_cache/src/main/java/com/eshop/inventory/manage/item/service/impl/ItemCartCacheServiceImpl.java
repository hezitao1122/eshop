package com.eshop.inventory.manage.item.service.impl;

import com.eshop.inventory.common.base.impl.CacheServiceImpl;
import com.eshop.inventory.manage.common.enums.RedisCachePrefixEnum;
import com.eshop.inventory.manage.item.dto.TbItemCartDTO;
import com.eshop.inventory.manage.item.service.ItemCartCacheService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @ClassName: ItemCartServiceImpl
 * @Title: ItemCartServiceImpl
 * @ProjectName: inventory
 * @Description: 商品类目缓存操作的service实现类
 * @Author: hzt
 * @Date: Create in 2019/6/1814:44
 */
@Service
public class ItemCartCacheServiceImpl extends CacheServiceImpl<TbItemCartDTO,Long> implements ItemCartCacheService {
    /**
     * redis对应商品信息的前缀
     */
    private final String redisPrefix = RedisCachePrefixEnum.ITEM_CART.getName();


    @Override
    public String prefix() {
        return redisPrefix;
    }

    @Override
    @CachePut(value = "local", key = "'EHCACHE:ITEM:CART:'+#tbItemDTO.id")
    public TbItemCartDTO saveLoadEhCache(TbItemCartDTO tbItemCartDTO) {
        return null;
    }

    @Override
    @Cacheable(value = "local", key = "'EHCACHE:ITEM:CART:'+#id")
    public TbItemCartDTO getLoadEhCache(Long aLong) {
        return null;
    }
}
