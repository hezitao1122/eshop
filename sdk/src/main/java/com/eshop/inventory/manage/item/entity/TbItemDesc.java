package com.eshop.inventory.manage.item.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.eshop.inventory.common.entity.BaseEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author zeryts
 * @description: TODO
 * ```````````````````````````
 * @title: TbItemDesc
 * @projectName inventory
 * @date 2019/5/3021:41
 */
@Entity
@Table(name = "tb_item_desc", schema = "eshop", catalog = "")
@TableName(value = "tb_item_desc")
public class TbItemDesc extends BaseEntity {
    @TableId(value = "item_id", type = IdType.ID_WORKER)
    private long itemId;
    @TableField(value = "item_desc")
    private String itemDesc;
    private Timestamp created;
    private Timestamp updated;

    @Id
    @Column(name = "item_id", nullable = false)
    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    @Basic
    @Column(name = "item_desc", nullable = true, length = -1)
    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    @Basic
    @Column(name = "created", nullable = true)
    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Basic
    @Column(name = "updated", nullable = true)
    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TbItemDesc that = (TbItemDesc) o;
        return itemId == that.itemId &&
                Objects.equals(itemDesc, that.itemDesc) &&
                Objects.equals(created, that.created) &&
                Objects.equals(updated, that.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, itemDesc, created, updated);
    }
}
