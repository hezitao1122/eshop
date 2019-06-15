package com.eshop.inventory.common.base;

import com.eshop.inventory.common.dto.ResultDto;
import com.eshop.inventory.common.entity.BaseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zeryts
 * @description: 公共controller的接口
 * ```````````````````````````
 * @title: BaseController
 * @projectName inventory
 * @date 2019/5/31 22:51
 */
public abstract class BaseController<T extends BaseEntity, ID> {

    @RequestMapping("/add")
    protected ResultDto<T> add(T t){
        return new ResultDto<T>(getService().add(t));
    }
    @RequestMapping("/delete")
    protected ResultDto<T> delete(ID id){
        return new ResultDto<T>(getService().delete(id));
    }
    @RequestMapping("/find")
    protected ResultDto<T> find(ID id){
        return new ResultDto<T>(getService().getById(id));
    }
    @RequestMapping("/update")
    protected ResultDto<T> update(T t){
        return new ResultDto<T>(getService().update(t));
    }

    protected abstract BaseDBService<T,ID> getService();

}
