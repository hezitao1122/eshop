package com.eshopinventory.inventory.manage.item.controller;

import com.eshopinventory.inventory.common.dto.ResultDto;
import com.eshopinventory.inventory.config.exception.MyException;
import com.eshopinventory.inventory.manage.item.entity.TbItem;
import com.eshopinventory.inventory.manage.item.request.ItemInventoryCacheRefreshRequest;
import com.eshopinventory.inventory.manage.item.request.ItemInventoryDBUpdateRequest;
import com.eshopinventory.inventory.manage.item.request.Request;
import com.eshopinventory.inventory.manage.item.service.ItemAsyncService;
import com.eshopinventory.inventory.manage.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zeryts
 * @description: 商品操作的controller层
 * ```````````````````````````
 * @title: ItemController
 * @projectName inventory
 * @date 2019/5/31 23:04
 */
@RestController
@RequestMapping("/api/v1/item")
public class ItemController {
    @Autowired
    private ItemAsyncService itemAsyncService;
    @Autowired
    private ItemService itemService;

    /**
     * 更新商品
     */
    @PostMapping("/update")
    public ResultDto<TbItem> update(@RequestBody TbItem item) {
        //封装请求
        Request<TbItem, Long> tbItemLongRequest = new ItemInventoryDBUpdateRequest(item, itemService);
        //执行请求
        itemAsyncService.process(tbItemLongRequest);
        return ResultDto.create();

    }

    /**
     * 获取商品
     */
    @PostMapping("/get")
    public ResultDto<TbItem> get(Long id) throws InterruptedException {

        TbItem tbItem = null;

        //封装请求
        Request<TbItem, Long> request = new ItemInventoryCacheRefreshRequest(id, itemService);
        //将请求发送至异步队列
        itemAsyncService.process(request);


        // 将请求扔给service异步去处理以后，就需要while(true)一会儿，在这里hang住
        // 去尝试等待前面有商品库存更新的操作，同时缓存刷新的操作，将最新的数据刷新到缓存中
        long startTime = System.currentTimeMillis();
        long endTime = 0;
        long waitTime = 0;


        while(true){
            //如果等待超过200ms没有从缓存中取到结果，直接返回
            if(waitTime > 2000L)
                break;


            //尝试去redis中读取一次商品信息
            tbItem = itemService.getCacheById(id);

            if(tbItem != null){
                return ResultDto.create(tbItem);
            }

            //没有读取到则睡眠一段时间
            Thread.sleep(20);
            //获取此时的结束时间
            endTime = System.currentTimeMillis();
//             获取等待时间
            waitTime = endTime - startTime;
        }

//        直接尝试从数据库中读取
        TbItem item = itemService.getById(id);
        if(item != null){
            //有则进行缓存刷新
            itemService.setCache(item);
            return ResultDto.create(item);
        }
        return ResultDto.create(new MyException("获取商品信息失败！"));
    }


}
