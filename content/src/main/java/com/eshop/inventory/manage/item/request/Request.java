package com.eshop.inventory.manage.item.request;

import com.eshop.inventory.common.entity.BaseEntity;

import java.util.Map;

/**
 * @author zeryts
 * @description: 请求接口
 * ```````````````````````````
 * @title: Request
 * @projectName inventory
 * @date 2019/5/3023:04
 */
public interface Request<T extends BaseEntity, I> {
    void process();

    I getId();

    boolean isForceRefresh();

    Map<I, Boolean> getCacheMap();
}
