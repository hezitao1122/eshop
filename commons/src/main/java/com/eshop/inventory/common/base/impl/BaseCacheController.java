package com.eshop.inventory.common.base.impl;

import com.eshop.inventory.common.base.IBaseCacheController;
import com.eshop.inventory.common.base.IBaseEhCacheController;
import com.eshop.inventory.common.dto.BaseDTO;
import com.eshop.inventory.common.dto.ResultDto;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.Serializable;

/**
 * @author zeryts
 * @description: 用于reids缓存操作的公共接口
 * ```````````````````````````
 * @title: IBaseEhCacheController
 * @projectName inventory
 * @date 2019/6/20 23:16
 */
public abstract class BaseCacheController<T extends BaseDTO,ID extends Serializable> implements IBaseEhCacheController<T,ID> , IBaseCacheController<T,ID> {

    @Override
    @RequestMapping(value = "/findEhcache")
    public ResultDto<T> getEhcache(ID id) {
        return new ResultDto<>(getCacheService().getLoadEhCache(id));
    }

    @Override
    @RequestMapping(value = "/delEhcache")
    public ResultDto<T> deleteEhcache(ID id) {
        getCacheService().delLoadEhCache(id);
        return new ResultDto<>();
    }

    @Override
    @RequestMapping(value = "/get")
    public ResultDto<T> getCache(ID id) {
        T t = null;
        //先从redis中取，取出来的话就直接返回
        t = getCacheService().getLoadCache(id);

        //取不出来去ehcache中取
        if(t == null){
            t = getCacheService().getLoadEhCache(id);
        }
        //取不出来再去调用数据库
        if(t == null ){

        }
        return new ResultDto<T>(t);
    }


    @Override
    @RequestMapping(value = "/del")
    public ResultDto<T> deleteCache(ID id) {
        return new ResultDto(getCacheService().deleteLoadCache(id));
    }
}
