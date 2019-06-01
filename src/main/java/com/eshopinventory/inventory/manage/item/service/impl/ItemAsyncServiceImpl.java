package com.eshopinventory.inventory.manage.item.service.impl;

import com.eshopinventory.inventory.common.util.HashUtil;
import com.eshopinventory.inventory.manage.item.entity.TbItem;
import com.eshopinventory.inventory.manage.item.request.Request;
import com.eshopinventory.inventory.manage.item.request.RequestQueue;
import com.eshopinventory.inventory.manage.item.service.ItemAsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author zeryts
 * @description: 请求异步处理的service实现
 * ```````````````````````````
 * @title: ItemAsyncServiceImpl
 * @projectName inventory
 * @date 2019/5/31 22:01
 */
@Service
@Slf4j
public class ItemAsyncServiceImpl implements ItemAsyncService {


    @Override
    public void process(Request<TbItem,Long> request) {
        try{
            // 做请求的路由，根据每个请求的商品id，路由到对应的内存队列中去
            ArrayBlockingQueue<Request> queue = getRoutingQueue(request.getId());
            //将请求放至内存队列中，完成路由操作
            queue.put(request);
        }catch (Exception e){
            log.info(e.toString(),e);
        }
    }

    /**
     * 功能描述: 获取路由到的内存队列<br>
     * 〈〉
     * @return: java.util.concurrent.ArrayBlockingQueue<com.eshopinventory.inventory.manage.item.request.Request<com.eshopinventory.inventory.manage.item.entity.TbItem,java.lang.Long>>
     * @since: 1.0.0
     * @Author: Administrator
     * @Date: 2019/5/31 22:09
     */
    public ArrayBlockingQueue<Request> getRoutingQueue(Long itemId){
        RequestQueue requestQueue = RequestQueue.getInstance();

        //先获取id的hash值

        int hash = HashUtil.getHash(itemId);

        // 对hash值取模，将hash值路由到指定的内存队列中，比如内存队列大小8
        // 用内存队列的数量对hash值取模之后，结果一定是在0~7之间
        // 所以任何一个商品id都会被固定路由到同样的一个内存队列中去的
        int index = hash & (requestQueue.getSize() - 1)  ;
        return requestQueue.getQueue(index);
    }

}
