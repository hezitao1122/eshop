package com.eshop.inventory.manage.item.controller;

import com.alibaba.fastjson.JSON;
import com.eshop.inventory.common.base.BaseDBService;
import com.eshop.inventory.common.base.impl.BaseDBController;
import com.eshop.inventory.common.dto.ResultDto;
import com.eshop.inventory.config.exception.MyException;
import com.eshop.inventory.manage.item.entity.TbItem;
import com.eshop.inventory.manage.item.request.ItemInventoryCacheRefreshRequest;
import com.eshop.inventory.manage.item.request.ItemInventoryDBUpdateRequest;
import com.eshop.inventory.manage.item.request.Request;
import com.eshop.inventory.manage.item.service.ItemAsyncService;
import com.eshop.inventory.manage.item.service.ItemService;
import org.slf4j.Logger;
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
public class ItemController extends BaseDBController<TbItem,Long> {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ItemController.class);
    @Autowired
    private ItemAsyncService itemAsyncService;
    @Autowired
    private ItemService itemService;

    @Override
    public BaseDBService<TbItem, Long> getDBService() {
        return itemService;
    }

    /**
     * 更新商品
     */
    @PostMapping("/update")
    public ResultDto<TbItem> update(@RequestBody TbItem item) {
        log.info("===========日志===========: 接收到更新商品库存的请求，商品=[{}]" , JSON.toJSONString(item) );

        //封装请求
        Request<TbItem, Long> tbItemLongRequest = new ItemInventoryDBUpdateRequest(item, itemService);
        //执行请求
        itemAsyncService.process(tbItemLongRequest);
        return new ResultDto();

    }



    /**
     * 获取商品
     */
    @PostMapping("/getNum")
    public ResultDto<TbItem> getNum(Long id) throws Exception {
        log.info("===========日志===========: 接收到一个商品库存的读请求，商品id=[{}]" , id);
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
                log.info("===========日志===========: 在200ms内读取到了redis中的库存缓存，商品=[{}]", JSON.toJSONString(tbItem));
                return new ResultDto(tbItem);
            }

            //没有读取到则睡眠一段时间
            Thread.sleep(20);
            //获取此时的结束时间
            endTime = System.currentTimeMillis();
//             获取等待时间
            waitTime = endTime - startTime;
        }

//        直接尝试从数据库中读取
        TbItem item = itemService.getNumById(id);
        if(item != null){
            //有则进行缓存刷新
            //itemService.setCache(item);

            //代码运行到这里一般有三种情况
            //1.就是说上一次也是读请求,数据刷入到了redis中,但是给redis的lru算法给清理掉了,标志位还是处于false状态,所以下一个读请求是拿不到数据的
            //所以可以再放一个读请求到队列中去,让数据刷新一下
            //2.可能在200ms内,读请求在队列中一直积压,没有等待它去执行,所以就直接查一次库,然后给队列中塞一条读的请求队列
            //3.数据库中本身没有,缓存穿透,穿过redis,请求直接打到mysql库中

            //如果能直接从数据库中进行读取到，但是没有放到队列中去执行，故需要再次放到队列中
            //需要强制刷新的请求
            Request<TbItem, Long> refreshRequest = new ItemInventoryCacheRefreshRequest(id, itemService,true);
            itemAsyncService.process(refreshRequest);

            return new ResultDto(item);
        }
        return new ResultDto(new MyException("获取商品信息失败！"));
    }


}
