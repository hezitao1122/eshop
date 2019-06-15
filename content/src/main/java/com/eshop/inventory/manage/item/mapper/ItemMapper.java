package com.eshop.inventory.manage.item.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.eshop.inventory.manage.item.entity.TbItem;
import org.springframework.stereotype.Repository;

/**
 * @author zeryts
 * @description: 商品操作的mapper接口
 * ```````````````````````````
 * @title: ItemMapper
 * @projectName inventory
 * @date 2019/6/118:45
 */
@Repository
public interface ItemMapper extends BaseMapper<TbItem> {
}
