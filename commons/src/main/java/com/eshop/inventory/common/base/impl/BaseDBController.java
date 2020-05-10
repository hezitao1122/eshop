package com.eshop.inventory.common.base.impl;

import com.eshop.inventory.common.base.IBaseDBService;
import com.eshop.inventory.common.base.IBaseController;
import com.eshop.inventory.common.dto.ResultDto;
import com.eshop.inventory.common.entity.BaseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zeryts
 * @description: 公共controller的接口
 * ```````````````````````````
 * @title: BaseDBController
 * @projectName inventory
 * @date 2019/5/31 22:51
 */
public abstract class BaseDBController<T extends BaseEntity, ID> implements IBaseController<T, ID> {

    @PostMapping("/add")
    public ResultDto<T> add(@RequestBody T t) {
        return new ResultDto<T>(getDBService().add(t));
    }

    @RequestMapping("/delete")
    public ResultDto<T> delete(@RequestParam ID id) {
        return new ResultDto<T>(getDBService().delete(id));
    }

    @RequestMapping("/find")
    public ResultDto<T> find(@RequestParam ID id) {
        return new ResultDto<>(getDBService().getById(id));
    }

    @PostMapping("/update")
    public ResultDto<T> update(@RequestBody T t) {
        return new ResultDto<T>(getDBService().update(t));
    }

    @PostMapping("/findByCondition")
    public ResultDto<T> findByCondition(@RequestBody T t) {
        return new ResultDto<T>(getDBService().getByCondition(t));
    }

    @PostMapping("/findByIds")
    public ResultDto<T> findByIds(@RequestBody List<ID> ids) {
        return new ResultDto<T>(getDBService().getByIds(ids));
    }


    /**
     * 获取执行业务逻辑的service接口
     *
     * @return getService 业务逻辑service
     */
    public abstract IBaseDBService<T, ID> getDBService();

}
