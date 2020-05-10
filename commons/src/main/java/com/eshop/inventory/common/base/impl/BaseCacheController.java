package com.eshop.inventory.common.base.impl;

import com.eshop.inventory.common.base.IBaseCacheController;
import com.eshop.inventory.common.base.IBaseEhCacheController;
import com.eshop.inventory.common.dto.BaseDTO;
import com.eshop.inventory.common.dto.ResultDto;
import com.eshop.inventory.common.queue.RebuildCacheQueue;
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
public abstract class BaseCacheController<T extends BaseDTO, ID extends Serializable> implements IBaseEhCacheController<T, ID>, IBaseCacheController<T, ID> {

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
        if (t == null) {
            t = getCacheService().getLoadEhCache(id);
        }


        /**
         * 如果缓存中取不出来,则需要被动重建数据
         * 1.直接去对应的服务器数据库中取
         * 2.返回给nginx
         * 3.同时推送一条消息到内存队列中
         * 4.后台异步去执行更新缓存
         */
        if (t == null) {
            ResultDto<T> tResultDto = getFeign().find(id);
            t = tResultDto.getData();
            RebuildCacheQueue.getInstance().add(t);
        }
        return new ResultDto<T>(t);
    }


    @Override
    @RequestMapping(value = "/del")
    public ResultDto<T> deleteCache(ID id) {
        return new ResultDto(getCacheService().deleteLoadCache(id));
    }
}
