package com.eshop.inventory.common.base;

import com.eshop.inventory.common.dto.BaseDTO;
import com.eshop.inventory.common.dto.ResultDto;

import java.io.Serializable;
import java.util.List;

/**
 * @author zeryts
 * @description: 用于Redis公用的存储Cache的公共controller接口
 * ```````````````````````````
 * @title: IBaseCacheController
 * @projectName inventory
 * @date 2019/6/2023:12
 */
public interface IBaseCacheController<T extends BaseDTO, ID extends Serializable> {
    /**
     * 功能描述: 从redis中获取缓存<br>
     * 〈〉
     *
     * @param id 主键
     * @return: com.eshop.inventory.common.dto.ResultDto<T>
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2020/5/14 21:45
     */
    ResultDto<T> getCache(ID id);

    /**
     * 功能描述: 从redis中删除缓存<br>
     * 〈〉
     *
     * @param id 主键
     * @return: com.eshop.inventory.common.dto.ResultDto<T>
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2020/5/14 21:45
     */
    ResultDto<T> deleteCache(ID id);

    /**
     * 功能描述: 缓存的service<br>
     * 〈〉
     *
     * @return: com.eshop.inventory.common.dto.ResultDto<T>
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2020/5/14 21:45
     */
    ICacheService<T, ID> getCacheService();

    /**
     * 功能描述: 批量从redis中获取缓存<br>
     * 〈〉
     *
     * @param ids 主键集合
     * @return: com.eshop.inventory.common.dto.ResultDto<T>
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2020/5/14 21:45
     */
    ResultDto<List<T>> getBatchCache(List<ID> ids);
}
