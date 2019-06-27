package com.eshop.inventory.manage.item.service.impl;

import com.eshop.inventory.common.base.IBaseReaderRequest;
import com.eshop.inventory.common.base.IBaseWriterRequest;
import com.eshop.inventory.common.util.HashUtil;
import com.eshop.inventory.manage.item.entity.TbItem;
import com.eshop.inventory.manage.item.request.Request;
import com.eshop.inventory.manage.item.request.RequestQueue;
import com.eshop.inventory.manage.item.service.ItemAsyncService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Map;
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
public class ItemAsyncServiceImpl implements ItemAsyncService {


    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ItemAsyncServiceImpl.class);

    @Override
    public void process(Request<TbItem, Long> request) {
        try {
            try {
                RequestQueue queue = RequestQueue.getInstance();
                //查询是否为缓存强制刷新的请求
                boolean forceRefresh = request.isForceRefresh();

                if (!forceRefresh) {
                    //如果不是强制刷新缓存的请求，需要做请求去重处理
                    Map<Long, Boolean> cacheMap = queue.getCacheMap();
                    //写请求标志位true ， 读请求标志为 false
                    if (request instanceof IBaseWriterRequest) {
                        //如果是写请求，将设置为false
                        cacheMap.put(request.getId(), Boolean.TRUE);
                    } else if (request instanceof IBaseReaderRequest) {
                        Boolean aBoolean = cacheMap.get(request.getId());
                        //如果为null
                        if (aBoolean == null) {
                            cacheMap.put(request.getId(), Boolean.FALSE);
                        }
                        //如果是读请求，并且前面也具有一个相同的写请求
                        if (aBoolean != null && !aBoolean)
                            return ;
                        //如果是读请求，前面没有相同读请求 , 将此读请求压入队列
                        if (aBoolean != null && aBoolean) {
                            cacheMap.put(request.getId(), Boolean.FALSE);
                        }
                    }
                }



            } catch (Exception e) {
                log.info(e.toString(), e);
            }


            // 做请求的路由，根据每个请求的商品id，路由到对应的内存队列中去
            ArrayBlockingQueue<Request> queue = getRoutingQueue(request.getId());
            //将请求放至内存队列中，完成路由操作
            queue.put(request);
        } catch (Exception e) {
            log.info(e.toString(), e);
        }
    }

    /**
     * 功能描述: 获取路由到的内存队列<br>
     * 〈〉
     *
     * @return: java.util.concurrent.ArrayBlockingQueue<com.eshop.inventory.manage.item.request.Request < com.eshop.inventory.manage.item.entity.TbItem, java.lang.Long>>
     * @since: 1.0.0
     * @author zeryts
     * @Date: 2019/5/31 22:09
     */
    public ArrayBlockingQueue<Request> getRoutingQueue(Long itemId) {
        RequestQueue requestQueue = RequestQueue.getInstance();

        //先获取id的hash值

        int hash = HashUtil.getHash(itemId);

        // 对hash值取模，将hash值路由到指定的内存队列中，比如内存队列大小8
        // 用内存队列的数量对hash值取模之后，结果一定是在0~7之间
        // 所以任何一个商品id都会被固定路由到同样的一个内存队列中去的

        int index = hash & (requestQueue.getSize() - 1);
        log.info("===========日志===========: 路由内存队列，商品id=" + itemId + ", 队列索引=" + index);
        return requestQueue.getQueue(index);
    }

}
