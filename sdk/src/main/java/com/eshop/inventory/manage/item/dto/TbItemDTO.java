package com.eshop.inventory.manage.item.dto;

import com.eshop.inventory.common.dto.BaseDTO;
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
public class TbItemDTO<E, D extends BaseDTO> extends BaseDTO<E , D> {

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
}
