package com.eshop.inventory.common.base;

import com.eshop.inventory.common.entity.BaseEntity;

/**
 * @author zeryts
 * @description: 公共的service层接口
 * ```````````````````````````
 * @title: BaseService
 * @projectName inventory
 * @date 2019/5/3023:29
 */
public interface BaseService<T extends BaseEntity,ID> {
    /**
     * 功能描述: 数据库增加单条数据<br>
     * 〈〉
     *
     * @param t 对应的数据库entity
     * @since: 1.0.0
     * @author zeryts
     * @Date: 2019/5/30 23:32
     */
    void add(T t);
    /**
     * 功能描述: 数据库根据主键删除单条数据<br>
     * 〈〉
     *
     * @param id 主键
     * @since: 1.0.0
     * @author zeryts
     * @Date: 2019/5/30 23:32
     */
    void delete(ID id);
    /**
     * 功能描述: 数据库更新单条数据<br>
     * 〈〉
     *
     * @param t entity对象
     * @since: 1.0.0
     * @author zeryts
     * @Date: 2019/5/30 23:32
     */
    void update(T t);
    /**
     * 功能描述: 数据库根据主键查询单条数据<br>
     * 〈〉
     *
     * @param id 主键
     * @since: 1.0.0
     * @author zeryts
     * @Date: 2019/5/30 23:32
     */
    T getById(ID id);
    /**
     * 功能描述: 缓存根据主键查询单条数据<br>
     * 〈〉
     *
     * @param t 存储的对象
     * @since: 1.0.0
     * @author zeryts
     * @Date: 2019/5/30 23:32
     */
    void setCache(T t);
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
