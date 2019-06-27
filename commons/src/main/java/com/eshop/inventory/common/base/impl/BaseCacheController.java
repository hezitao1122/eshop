package com.eshop.inventory.common.base.impl;

import com.eshop.inventory.common.base.IBaseCacheController;
import com.eshop.inventory.common.dto.BaseDTO;
import com.eshop.inventory.common.dto.ResultDto;

import java.io.Serializable;

/**
 * @author zeryts
 * @description: 用于reids缓存操作的公共接口
 * ```````````````````````````
 * @title: IBaseCacheController
 * @projectName inventory
 * @date 2019/6/20 23:16
 */
public abstract class BaseCacheController<T extends BaseDTO,ID extends Serializable> implements IBaseCacheController<T,ID> {

    @Override
    public ResultDto<T> getEhcache(ID id) {
        return new ResultDto<>(getCacheService().getLoadEhCache(id));
    }

    @Override
    public ResultDto<T> deleteEhcache(ID id) {
        getCacheService().delLoadEhCache(id);
        return new ResultDto<>();
    }




}
