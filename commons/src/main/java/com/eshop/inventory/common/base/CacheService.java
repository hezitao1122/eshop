package com.eshop.inventory.common.base;

/**
 * @author zeryts
 * @description: 用语缓存的service接口
 * ```````````````````````````
 * @title: CacheService
 * @projectName inventory
 * @date 2019/6/1223:29
 */
public interface CacheService<T,ID> {

    T saveLoadCache(T t);

    T getLoadCache(ID id);
}
