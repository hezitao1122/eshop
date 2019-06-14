package com.eshop.inventory.manage.order.entity;

import com.eshop.inventory.common.entity.BaseEntity;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author zeryts
 * @description: TODO
 * ```````````````````````````
 * @title: TbOrderItem
 * @projectName inventory
 * @date 2019/5/3021:41
 */
@Entity
@Table(name = "tb_order_item", schema = "eshop", catalog = "")
public class TbOrderItem extends BaseEntity  {
    private String id;
    private String itemId;
    private String orderId;
    private Integer num;
    private String title;
    private Long price;
    private Long totalFee;
    private String picPath;

    @Id
    @Column(name = "id", nullable = false, length = 20)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "item_id", nullable = false, length = 50)
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Basic
    @Column(name = "order_id", nullable = false, length = 50)
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "num", nullable = true)
    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Basic
    @Column(name = "title", nullable = true, length = 200)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "price", nullable = true)
    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @Basic
    @Column(name = "total_fee", nullable = true)
    public Long getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Long totalFee) {
        this.totalFee = totalFee;
    }

    @Basic
    @Column(name = "pic_path", nullable = true, length = 200)
    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TbOrderItem that = (TbOrderItem) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(itemId, that.itemId) &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(num, that.num) &&
                Objects.equals(title, that.title) &&
                Objects.equals(price, that.price) &&
                Objects.equals(totalFee, that.totalFee) &&
                Objects.equals(picPath, that.picPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, itemId, orderId, num, title, price, totalFee, picPath);
    }
}
