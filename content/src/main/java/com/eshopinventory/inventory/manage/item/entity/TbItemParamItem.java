package com.eshopinventory.inventory.manage.item.entity;

import com.eshopinventory.inventory.common.entity.BaseEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author Administrator
 * @description: TODO
 * ```````````````````````````
 * @title: TbItemParamItem
 * @projectName inventory
 * @date 2019/5/3021:41
 */
@Entity
@Table(name = "tb_item_param_item", schema = "eshop", catalog = "")
public class TbItemParamItem extends BaseEntity  {
    private long id;
    private Long itemId;
    private String paramData;
    private Timestamp created;
    private Timestamp updated;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "item_id", nullable = true)
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    @Basic
    @Column(name = "param_data", nullable = true, length = -1)
    public String getParamData() {
        return paramData;
    }

    public void setParamData(String paramData) {
        this.paramData = paramData;
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
        TbItemParamItem that = (TbItemParamItem) o;
        return id == that.id &&
                Objects.equals(itemId, that.itemId) &&
                Objects.equals(paramData, that.paramData) &&
                Objects.equals(created, that.created) &&
                Objects.equals(updated, that.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, itemId, paramData, created, updated);
    }
}
