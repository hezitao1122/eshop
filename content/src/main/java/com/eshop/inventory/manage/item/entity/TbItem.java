package com.eshop.inventory.manage.item.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.eshop.inventory.common.entity.BaseEntity;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author Administrator
 * @description: TODO
 * ```````````````````````````
 * @title: TbItem
 * @projectName inventory
 * @date 2019/5/3021:41
 */
@Entity
@Table(name = "tb_item", schema = "eshop")
@Proxy(lazy = false)
@TableName(value = "tb_item")
public class TbItem extends BaseEntity  {
    @TableId(value = "id", type = IdType.AUTO)
    private long id;
    private String title;
    @TableField(value = "sell_Point")
    private String sellPoint;
    private long price;
    private int num;
    private String barcode;
    private String image;
    private long cid;
    private byte status;
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
    @Column(name = "title", nullable = false, length = 100)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "sell_point", nullable = true, length = 500)
    public String getSellPoint() {
        return sellPoint;
    }

    public void setSellPoint(String sellPoint) {
        this.sellPoint = sellPoint;
    }

    @Basic
    @Column(name = "price", nullable = false)
    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    @Basic
    @Column(name = "num", nullable = false)
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Basic
    @Column(name = "barcode", nullable = true, length = 30)
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Basic
    @Column(name = "image", nullable = true, length = 500)
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Basic
    @Column(name = "cid", nullable = false)
    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "created", nullable = false)
    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Basic
    @Column(name = "updated", nullable = false)
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
        TbItem tbItem = (TbItem) o;
        return id == tbItem.id &&
                price == tbItem.price &&
                num == tbItem.num &&
                cid == tbItem.cid &&
                status == tbItem.status &&
                Objects.equals(title, tbItem.title) &&
                Objects.equals(sellPoint, tbItem.sellPoint) &&
                Objects.equals(barcode, tbItem.barcode) &&
                Objects.equals(image, tbItem.image) &&
                Objects.equals(created, tbItem.created) &&
                Objects.equals(updated, tbItem.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, sellPoint, price, num, barcode, image, cid, status, created, updated);
    }
}
