package com.eshop.inventory.manage.item.dto;

import com.eshop.inventory.common.dto.BaseDTO;
import com.eshop.inventory.manage.item.entity.TbItemDesc;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author zeryts
 * @description: 商品描述使用的controller
 * ```````````````````````````
 * @title: TbItemDescDTO
 * @projectName inventory
 * @date 2019/6/29 11:46
 */
@Data
public class TbItemDescDTO extends BaseDTO<TbItemDesc> {
    private Long itemId;
    private String itemDesc;
    private Timestamp created;
    private Timestamp updated;


}
