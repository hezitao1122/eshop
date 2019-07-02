package com.eshop.inventory.manage.item.controller;

import com.eshop.inventory.common.base.BaseFeign;
import com.eshop.inventory.common.base.ICacheService;
import com.eshop.inventory.common.base.impl.BaseCacheController;
import com.eshop.inventory.common.dto.ResultDto;
import com.eshop.inventory.manage.item.dto.TbItemDTO;
import com.eshop.inventory.manage.item.feign.ItemFeign;
import com.eshop.inventory.manage.item.service.ItemCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zeryts
 * @description: 缓存操作的测试controller层
 * ```````````````````````````
 * @title: ItemCacheController
 * @projectName inventory
 * @date 2019/6/18 23:44
 */
@RestController
@RequestMapping("/item")
public class ItemCacheController extends BaseCacheController<TbItemDTO,Long> {
    @Autowired
    private ItemCacheService itemCacheService;
    @Autowired
    private ItemFeign itemFeign;

    @PostMapping("/add")
    public ResultDto<TbItemDTO> add(@RequestBody TbItemDTO dto){
        return new ResultDto<>(itemCacheService.saveLoadCache(dto.getId(),dto));
    }
    @Override
    public ICacheService<TbItemDTO, Long> getCacheService() {
        return itemCacheService;
    }

    @Override
    public BaseFeign<TbItemDTO, Long> getFeign() {
        return itemFeign;
    }

}
