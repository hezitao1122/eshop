package com.eshop.inventory.manage.item.feign.rollback;

import com.eshop.inventory.common.dto.ResultDto;
import com.eshop.inventory.manage.item.entity.TbItem;
import com.eshop.inventory.manage.item.feign.ItemFeign;

/**
 * @author zeryts
 * @description: feign操作失败的类
 * ```````````````````````````
 * @title: ItemFeignRollback
 * @projectName inventory
 * @date 2019/6/18 23:20
 */
public class ItemFeignRollback implements ItemFeign {
    @Override
    public ResultDto<TbItem> add(TbItem tbItem) {
        return null;
    }

    @Override
    public ResultDto<TbItem> delete(Long aLong) {
        return null;
    }

    @Override
    public ResultDto<TbItem> find(Long aLong) {
        return null;
    }

    @Override
    public ResultDto<TbItem> update(TbItem tbItem) {
        return null;
    }
}
