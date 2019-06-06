package com.eshop.inventory.common.base;

import com.eshop.inventory.common.dto.ResultDto;
import com.eshop.inventory.common.entity.BaseEntity;

/**
 * @author zeryts
 * @description: 公共controller的接口
 * ```````````````````````````
 * @title: BaseController
 * @projectName inventory
 * @date 2019/5/31 22:51
 */
public interface BaseController<T extends BaseEntity, ID> {

    ResultDto<T> add(T t);

    ResultDto<T> delete(ID id);

    ResultDto<T> find(ID id);

    ResultDto<T> update(T t);

}
