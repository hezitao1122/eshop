package com.eshop.inventory.manage.item.service;

import com.eshop.inventory.manage.common.CacheEnum;
import com.eshop.inventory.manage.item.dto.TbItemDTO;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author zeryts
 * @description: TODO
 * ```````````````````````````
 * @title: ItemCacheServiceImpl
 * @projectName inventory
 * @date 2019/6/12 23:32
 */
@Service
public class ItemCacheServiceImpl implements ItemCacheService<TbItemDTO,Long> {
    private static final String CACHE_NAME = CacheEnum.CACHE_COMMON_PRE.getCode();
    /**
     * 功能描述: 将商品存入缓存中<br>
     * 〈〉
     *
     * @param tbItemDTO
     * @return: com.eshop.inventory.manage.item.dto.TbItemDTO
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/6/12 23:55
     */
    @CachePut(value = "LOAD:CACHEITEM:", key = "'key:'+#tbItemDTO.id")
    public TbItemDTO saveLoadCache(TbItemDTO tbItemDTO) {

        return tbItemDTO;
    }
    /**
     * 功能描述: 从缓存中取商品信息，没取到就是null<br>
     * 〈〉
     *
     * @param id 商品的id
     * @return: com.eshop.inventory.manage.item.dto.TbItemDTO
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/6/12 23:55
     */
    @Cacheable(value = "LOAD:CACHEITEM:", key = "'key:'+#id")
    public TbItemDTO getLoadCache(Long id) {

        return null;
    }

}
