package com.eshop.inventory.common.base;

import com.eshop.inventory.common.dto.ResultDto;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author zeryts
 * @description: 用于公用的存储EhCache的公共实体类
 * ```````````````````````````
 * @title: BaseEhCacheController
 * @projectName inventory
 * @date 2019/6/2023:12
 */
public interface BaseEhCacheController<T,ID> {
    ResultDto<T> addEhcache(@RequestBody T dto);

    ResultDto<T> getEhcache(ID id);

    ResultDto<T> updateEhcache(T t);

    ResultDto<T> deleteEhcache(ID id);

    ResultDto<T> getEhcacheAll();

}
