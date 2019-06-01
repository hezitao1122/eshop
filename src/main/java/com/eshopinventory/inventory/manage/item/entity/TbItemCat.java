package com.eshopinventory.inventory.manage.item.entity;

import com.eshopinventory.inventory.common.entity.BaseEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author Administrator
 * @description: TODO
 * ```````````````````````````
 * @title: TbItemCat
 * @projectName inventory
 * @date 2019/5/3021:41
 */
@Entity
@Table(name = "tb_item_cat", schema = "eshop", catalog = "")
public class TbItemCat extends BaseEntity  {
    private long id;
    private Long parentId;
    private String name;
    private Integer status;
    private Integer sortOrder;
    private Byte isParent;
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
    @Column(name = "parent_id", nullable = true)
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "status", nullable = true)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "sort_order", nullable = true)
    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Basic
    @Column(name = "is_parent", nullable = true)
    public Byte getIsParent() {
        return isParent;
    }

    public void setIsParent(Byte isParent) {
        this.isParent = isParent;
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
        TbItemCat tbItemCat = (TbItemCat) o;
        return id == tbItemCat.id &&
                Objects.equals(parentId, tbItemCat.parentId) &&
                Objects.equals(name, tbItemCat.name) &&
                Objects.equals(status, tbItemCat.status) &&
                Objects.equals(sortOrder, tbItemCat.sortOrder) &&
                Objects.equals(isParent, tbItemCat.isParent) &&
                Objects.equals(created, tbItemCat.created) &&
                Objects.equals(updated, tbItemCat.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parentId, name, status, sortOrder, isParent, created, updated);
    }
}
