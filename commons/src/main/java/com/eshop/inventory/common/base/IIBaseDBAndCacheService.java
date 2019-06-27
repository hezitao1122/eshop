package com.eshop.inventory.common.base;

import com.eshop.inventory.common.entity.BaseEntity;

/**
 * @author zeryts
 * @description: DB和Cache共同操作的service接口规范
 * ```````````````````````````
 * @title: IIBaseDBAndCacheService
 * @projectName inventory
 * @date 2019/6/15 17:38
 */
public interface IIBaseDBAndCacheService<T extends BaseEntity,ID>  extends IBaseDBService<T,ID>, IBaseCacheService<T,ID> {
}
