package com.eshop.inventory.common.base;

import com.eshop.inventory.common.entity.BaseEntity;

import java.util.List;

/**
 * @author zeryts
 * @description: 操作数据库的公共的service层接口
 * ```````````````````````````
 * @title: IBaseDBService
 * @projectName inventory
 * @date 2019/5/3023:29
 */
public interface IBaseDBService<T extends BaseEntity, ID> {
    /**
     * 功能描述: 数据库增加单条数据<br>
     * 〈〉
     *
     * @param t 对应的数据库entity
     * @since: 1.0.0
     * @author zeryts
     * @Date: 2019/5/30 23:32
     */
    T add(T t);

    /**
     * 功能描述: 数据库根据主键删除单条数据<br>
     * 〈〉
     *
     * @param id 主键
     * @since: 1.0.0
     * @author zeryts
     * @Date: 2019/5/30 23:32
     */
    T delete(ID id);

    /**
     * 功能描述: 数据库更新单条数据<br>
     * 〈〉
     *
     * @param t entity对象
     * @since: 1.0.0
     * @author zeryts
     * @Date: 2019/5/30 23:32
     */
    T update(T t);

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
     * 功能描述: 根据条件查询数据的集合<br>
     * 〈〉
     *
     * @param t
     * @return: java.util.List<T>
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/6/27 23:51
     */
    List<T> getByCondition(T t);

    /**
     * 功能描述: 根据主键查询数据的集合<br>
     * 〈〉
     *
     * @param ids
     * @return: java.util.List<T>
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/6/27 23:51
     */
    List<T> getByIds(List<ID> ids);


}
