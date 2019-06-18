package com.eshop.inventory.manage.item.dto;

import com.eshop.inventory.common.dto.BaseDTO;
import com.eshop.inventory.manage.item.entity.TbItem;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author zeryts
 * @description: DTO的传输类
 * ```````````````````````````
 * @title: TbItemDTO
 * @projectName inventory
 * @date 2019/6/12 22:29
 */
@Data
public class TbItemDTO extends BaseDTO<TbItem> {


    private TbItem entity;
    private long id;
    private String title;
    private String sellPoint;
    private long price;
    private int num;
    private String barcode;
    private String image;
    private long cid;
    private byte status;
    private Timestamp created;
    private Timestamp updated;

    public TbItemDTO() {
    }
    public TbItemDTO(TbItem tbItem) {
        if(entity ==null)
            entity = new TbItem(id,title,sellPoint,price,num,barcode,image,cid,status,created,updated);
    }



}
