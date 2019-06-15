package com.eshop.inventory.common.base.impl;

import com.eshop.inventory.common.base.BaseDBService;
import com.eshop.inventory.common.base.IBaseController;
import com.eshop.inventory.common.dto.ResultDto;
import com.eshop.inventory.common.entity.BaseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zeryts
 * @description: 公共controller的接口
 * ```````````````````````````
 * @title: BaseDBController
 * @projectName inventory
 * @date 2019/5/31 22:51
 */
public abstract class BaseDBController<T extends BaseEntity, ID> implements IBaseController<T,ID> {

    @RequestMapping("/add")
    public ResultDto<T> add(T t){
        return new ResultDto<T>(getDBService().add(t));
    }
    @RequestMapping("/delete")
    public ResultDto<T> delete(ID id){
        return new ResultDto<T>(getDBService().delete(id));
    }
    @RequestMapping("/find")
    public ResultDto<T> find(ID id){
        return new ResultDto<T>(getDBService().getById(id));
    }
    @RequestMapping("/update")
    public ResultDto<T> update(T t){
        return new ResultDto<T>(getDBService().update(t));
    }

    /**
     * 获取执行业务逻辑的service接口
     * @return getService 业务逻辑service
     */
    public abstract BaseDBService<T,ID> getDBService();

}
