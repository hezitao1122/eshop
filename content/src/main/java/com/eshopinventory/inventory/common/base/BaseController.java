package com.eshopinventory.inventory.common.base;

import com.eshopinventory.inventory.common.dto.ResultDto;
import com.eshopinventory.inventory.common.entity.BaseEntity;

/**
 * @author zeryts
 * @description: 公共controller的接口
 * ```````````````````````````
 * @title: BaseController
 * @projectName inventory
 * @date 2019/5/31 22:51
 */
public interface BaseController<T extends BaseEntity, ID> {

    public ResultDto<T> add(T t);

    public ResultDto<T> delete(ID id);

    public ResultDto<T> find(ID id);

    public ResultDto<T> update(T t);

}
