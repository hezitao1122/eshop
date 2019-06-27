package com.eshop.inventory.common.base;

import com.eshop.inventory.common.entity.BaseEntity;

/**
 * @author zeryts
 * @description: 操作缓存的公共的service层接口
 * ```````````````````````````
 * @title: IBaseCacheService
 * @projectName inventory
 * @date 2019/6/1516:51
 */
public interface IBaseCacheService<T extends BaseEntity,ID> {

    /**
     * 功能描述: 缓存根据主键查询单条数据<br>
     * 〈〉
     *
     * @param id 在缓存中的key
     * @param t 存储的对象
     * @since: 1.0.0
     * @author zeryts
     * @Date: 2019/5/30 23:32
     */
    void setCache(ID id,T t);
    /**
     * 功能描述: 缓存根据主键查询单条数据<br>
     * 〈〉
     *
     * @param id 单条数据的主键
     * @since: 1.0.0
     * @author zeryts
     * @Date: 2019/5/30 23:32
     */
    T getCacheById(ID id);
    /**
     * 功能描述: 缓存根据主键删除单条数据<br>
     * 〈〉
     *
     * @param id 单条数据的主键
     * @since: 1.0.0
     * @author zeryts
     * @Date: 2019/5/30 23:32
     */
    void deleteCacheById(ID id);

}
