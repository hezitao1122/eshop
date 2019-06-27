package com.eshop.inventory.common.base;

import com.eshop.inventory.common.dto.BaseDTO;
import com.eshop.inventory.common.dto.ResultDto;

import java.io.Serializable;

/**
 * @author zeryts
 * @description: 用于公用的存储Cache的公共controller接口
 * ```````````````````````````
 * @title: IBaseCacheController
 * @projectName inventory
 * @date 2019/6/2023:12
 */
public interface IBaseCacheController<T extends BaseDTO,ID  extends Serializable> extends IBaseController<T,ID> {

    ResultDto<T> getEhcache(ID id);

    ResultDto<T> deleteEhcache(ID id);

    ICacheService<T,ID> getCacheService();

}
