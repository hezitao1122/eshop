package com.eshop.inventory.manage.item.service;

import com.eshop.inventory.manage.item.entity.TbItem;
import com.eshop.inventory.manage.item.request.Request;

/**
 * @author zeryts
 * @description: 将请求异步执行并丢至内存队列中的方法
 * ```````````````````````````
 * @title: ItemAsyncService
 * @projectName inventory
 * @date 2019/5/3122:02
 */
public interface ItemAsyncService {

    void process(Request<TbItem, Long> request);
}
