package com.eshop.inventory.manage.item.controller;

import com.eshop.inventory.common.base.ICacheService;
import com.eshop.inventory.common.base.impl.BaseCacheController;
import com.eshop.inventory.common.dto.ResultDto;
import com.eshop.inventory.manage.item.dto.TbItemDescDTO;
import com.eshop.inventory.manage.item.service.ItemDescCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zeryts
 * @description: 缓存操作的测试controller层
 * ```````````````````````````
 * @title: ItemCacheController
 * @projectName inventory
 * @date 2019/6/18 23:44
 */
@RestController
@RequestMapping("/item/desc")
public class ItemDescCacheController extends BaseCacheController<TbItemDescDTO,Long> {
    @Autowired
    private ItemDescCacheService itemDescCacheService;

    @PostMapping("/add")
    public ResultDto<TbItemDescDTO> add(@RequestBody TbItemDescDTO dto){
        return new ResultDto<>(itemDescCacheService.saveLoadCache(dto.getItemId(),dto));
    }

    @Override
    public ICacheService<TbItemDescDTO, Long> getCacheService() {
        return itemDescCacheService;
    }


}
