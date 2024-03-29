package com.eshop.inventory.manage.item.dto;

import com.eshop.inventory.common.dto.BaseDTO;
import com.eshop.inventory.manage.item.entity.TbItemCat;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @ClassName: TbItemCartDTO
 * @Title: TbItemCartDTO
 * @ProjectName: inventory
 * @Description: 商品类目的操作DTO类
 * @Author: zeryts
 * @Date: Create in 2019/6/1814:43
 */
@Data
public class TbItemCartDTO extends BaseDTO<TbItemCat> {

    private long id;
    private Long parentId;
    private String name;
    private Integer status;
    private Integer sortOrder;
    private Byte isParent;
    private Timestamp created;
    private Timestamp updated;

}
