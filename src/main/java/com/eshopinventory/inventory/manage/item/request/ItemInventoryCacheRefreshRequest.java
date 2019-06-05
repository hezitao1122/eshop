package com.eshopinventory.inventory.manage.item.request;

import com.alibaba.fastjson.JSON;
import com.eshopinventory.inventory.common.base.BaseReaderRequest;
import com.eshopinventory.inventory.manage.item.entity.TbItem;
import com.eshopinventory.inventory.manage.item.service.ItemService;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Administrator
 * @description: 重新加载商品库存缓存
 * ```````````````````````````
 * @title: ItemInventoryCacheRefreshRequest
 * @projectName inventory
 * @date 2019/5/30 23:26
 */
@Slf4j
public class ItemInventoryCacheRefreshRequest  implements Request<TbItem,Long> , BaseReaderRequest {
    /**
     * 商品信息
     */
    private Long id;

    /**
     * 商品操作的service
     */
    private ItemService itemService;

    /**
     * 是否强制刷新缓存
     */
    private boolean forceRefresh ;
    /**
     * 标识位map
     */
    private Map<Long, Boolean> cacheMap = new ConcurrentHashMap<>();

    /**
     * 构造，传入商品信息
     * @param id 商品信息id
     * @param itemService 商品操作的实体类
     */
    public ItemInventoryCacheRefreshRequest(Long id, ItemService itemService) {
        this.id = id;
        this.itemService = itemService;
        forceRefresh = false;
    }
    /**
     * 构造，传入商品信息
     * @param id 商品信息id
     * @param itemService 商品操作的实体类
     */
    public ItemInventoryCacheRefreshRequest(Long id, ItemService itemService, boolean forceRefresh) {
        this.id = id;
        this.itemService = itemService;
        this.forceRefresh = forceRefresh;
    }
    /**
     * 功能描述: 修改库存的方法<br>
     * 〈〉
     *
     * @since: 1.0.0
     * @Author: Administrator
     * @Date: 2019/5/31 21:38
     */
    @Override
    public void process() {
        TbItem item1 = itemService.getById(id);
        log.info("===========日志===========: 已查询到商品最新的库存数量，商品=[{}]" , JSON.toJSONString(item1));
        // 将最新的商品库存数量，刷新到redis缓存中去
        itemService.setCache(item1);
    }

    @Override
    public Long getId() {
        return id;
    }

    public boolean isForceRefresh() {
        return forceRefresh;
    }

    public Map<Long, Boolean> getCacheMap() {
        return cacheMap;
    }
}
