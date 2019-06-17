package com.eshop.inventory.manage.item.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.eshop.inventory.manage.item.entity.TbItemParamItem;
import org.springframework.stereotype.Repository;

/**
 * @author zeryts
 * @description: 商品规格和商品的关系操作的DAO层接口
 * ```````````````````````````
 * @title: ItemParamItemMapper
 * @projectName inventory
 * @date 2019/6/1722:42
 */
@Repository
public interface ItemParamItemMapper extends BaseMapper<TbItemParamItem> {
}
