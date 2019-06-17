package com.eshop.inventory.manage.content.entity;

import com.eshop.inventory.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * @author zeryts
 * @description: 内容分类的实体
 * ```````````````````````````
 * @title: TbContent
 * @projectName inventory
 * @date 2019/5/3021:41
 */
@Entity
@Table(name = "tb_content_category", schema = "eshop", catalog = "")
@Data
public class TbContentCategory extends BaseEntity {
    private long id;
    private long parentId;
    private String name;
    private int status;
    private int sortOrder;
    private boolean parent;
    private Timestamp created;
    private Timestamp updated;


}
