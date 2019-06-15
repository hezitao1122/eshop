package com.eshop.inventory.common.base;

import com.eshop.inventory.common.entity.BaseEntity;

/**
 * @author zeryts
 * @description: DB和Cache共同操作的service接口规范
 * ```````````````````````````
 * @title: BaseDBAndCacheService
 * @projectName inventory
 * @date 2019/6/15 17:38
 */
public interface BaseDBAndCacheService<T extends BaseEntity,ID>  extends BaseDBService<T,ID> , BaseCacheService<T,ID>{
}
