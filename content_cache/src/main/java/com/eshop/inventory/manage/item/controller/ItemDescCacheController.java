package com.eshop.inventory.manage.item.controller;

import com.eshop.inventory.hystrixy.collapser.ItemDesCollasperCommand;
import com.eshop.inventory.hystrixy.commands.ItemDescCachePerwarmCommand;
import com.eshop.inventory.common.base.BaseFeign;
import com.eshop.inventory.common.base.ICacheService;
import com.eshop.inventory.common.base.impl.BaseCacheController;
import com.eshop.inventory.common.dto.ResultDto;
import com.eshop.inventory.manage.item.dto.TbItemDescDTO;
import com.eshop.inventory.manage.item.feign.ItemDescFeign;
import com.eshop.inventory.manage.item.service.ItemDescCacheService;
import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixObservableCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
public class ItemDescCacheController extends BaseCacheController<TbItemDescDTO, Long> {
    @Autowired
    private ItemDescCacheService itemDescCacheService;
    @Autowired
    private ItemDescFeign itemDescFeign;


    @PostMapping("/add")
    public ResultDto<TbItemDescDTO> add(@RequestBody TbItemDescDTO dto) {
        return new ResultDto<>(itemDescCacheService.saveLoadCache(dto.getItemId(), dto));
    }

    @Override
    public ICacheService<TbItemDescDTO, Long> getCacheService() {
        return itemDescCacheService;
    }

    @Override
    public BaseFeign<TbItemDescDTO, Long> getFeign() {
        return itemDescFeign;
    }

    @Override
    public HystrixCommand<TbItemDescDTO> getHystrixyCommand(Long id) {
        return new ItemDescCachePerwarmCommand(id);
    }

    @Override
    public HystrixObservableCommand<TbItemDescDTO> getHystrixObservableCommand(List<Long> longs) {
        return null;
    }

    @Override
    public HystrixCollapser<List<TbItemDescDTO>, TbItemDescDTO, Long> getHystrixCollapser(Long aLong) {
        return new ItemDesCollasperCommand(aLong,itemDescFeign);
    }


}
