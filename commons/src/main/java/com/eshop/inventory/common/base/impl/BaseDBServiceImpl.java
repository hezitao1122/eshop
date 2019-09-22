package com.eshop.inventory.common.base.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.eshop.inventory.common.base.IBaseDBService;
import com.eshop.inventory.common.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author zeryts
 * @description: 数据库操作的通用service层实现
 * ```````````````````````````
 * @title: BaseDBServiceImpl
 * @projectName inventory
 * @date 2019/6/15 16:35
 */
public abstract class BaseDBServiceImpl<T extends BaseEntity,ID extends Serializable> implements IBaseDBService<T,ID> {
    @Override
    public T add(T t) {
        getMapper().insert(t);
        return t;
    }

    @Override
    public T delete(ID id) {
        T t = getById(id);
        getMapper().deleteById(id);
        return t;
    }

    @Override
    public T update(T t) {
        getMapper().updateById(t);
        return t;
    }

    @Override
    public T getById(ID id) {
        return getMapper().selectById(id);
    }

    @Override
    public List<T> getByCondition(T t) {
        EntityWrapper<T> wrapper = new EntityWrapper();
        wrapper.setEntity(t);
        return getMapper().selectList(wrapper);
    }

    @Override
    public List<T> getByIds(List<ID> ids) {
        return getMapper().selectBatchIds(ids);
    }

    protected abstract BaseMapper<T> getMapper();
}
