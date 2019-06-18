package com.eshop.inventory.manage.item.controller;

import com.eshop.inventory.common.dto.ResultDto;
import com.eshop.inventory.manage.item.dto.TbItemDTO;
import com.eshop.inventory.manage.item.service.ItemCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/api/v1/cache/item")
public class ItemCacheController {
    @Autowired
    private ItemCacheService itemCacheService;

    @PostMapping("/add")
    public ResultDto<TbItemDTO> add(TbItemDTO dto){
        return new ResultDto<>(itemCacheService.saveLoadEhCache(dto));
    }
    @GetMapping("/get")
    public  ResultDto<TbItemDTO> get(Long id){
        return new ResultDto<>(itemCacheService.getLoadCache(id));
    }

}
