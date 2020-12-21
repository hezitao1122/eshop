package com.eshop.inventory.common.base;

import com.eshop.inventory.common.dto.BaseDTO;
import com.eshop.inventory.common.dto.ResultDto;

import java.io.Serializable;

/**
 * @author zeryts
 * @description: 用于公用的存储Cache的公共controller接口
 * ```````````````````````````
 * @title: IBaseEhCacheController
 * @projectName inventory
 * @date 2019/6/2023:12
 */
public interface IBaseEhCacheController<T extends BaseDTO, ID extends Serializable> {
    /**
     * 功能描述: 获取内存队列的缓存接口<br>
     * 〈〉
     *
     * @param id 主键
     * @return: com.eshop.inventory.common.dto.ResultDto<T>
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2020/5/14 21:44
     */
    ResultDto<T> getEhcache(ID id);

    /**
     * 功能描述: 删除内存队列的缓存接口<br>
     * 〈〉
     *
     * @param id 主键
     * @return: com.eshop.inventory.common.dto.ResultDto<T>
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2020/5/14 21:43
     */
    ResultDto<T> deleteEhcache(ID id);

    /**
     * 功能描述: 获取的缓存Service接口<br>
     * 〈〉
     *
     * @return: com.eshop.inventory.common.base.ICacheService<T, ID>
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2020/5/14 21:42
     */
    ICacheService<T, ID> getCacheService();


}
