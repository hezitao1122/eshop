package com.eshopinventory.inventory.manage.item.request;

import com.eshopinventory.inventory.manage.item.entity.TbItem;
import com.eshopinventory.inventory.manage.item.service.ItemService;

/**
 * @author Administrator
 * @description: 重新加载商品库存缓存
 * ```````````````````````````
 * @title: ItemInventoryCacheRefreshRequest
 * @projectName inventory
 * @date 2019/5/30 23:26
 */
public class ItemInventoryCacheRefreshRequest  implements Request<TbItem,Long> {
    /**
     * 商品信息
     */
    private Long id;

    /**
     * 商品操作的service
     */
    private ItemService itemService;

    /**
     * 构造，传入商品信息
     * @param id 商品信息id
     * @param itemService 商品操作的实体类
     */
    public ItemInventoryCacheRefreshRequest(Long id, ItemService itemService) {
        this.id = id;
        this.itemService = itemService;
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
        // 将最新的商品库存数量，刷新到redis缓存中去
        itemService.setCache(item1);
    }

    @Override
    public Long getId() {
        return id;
    }
}
