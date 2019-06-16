package com.eshop.inventory.manage.order.entity;

import com.eshop.inventory.common.entity.BaseEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author zeryts
 * @description: TODO
 * ```````````````````````````
 * @title: TbOrderShipping
 * @projectName inventory
 * @date 2019/5/3021:41
 */
@Entity
@Table(name = "tb_order_shipping", schema = "eshop", catalog = "")
public class TbOrderShipping extends BaseEntity  {
    private String orderId;
    private String receiverName;
    private String receiverPhone;
    private String receiverMobile;
    private String receiverState;
    private String receiverCity;
    private String receiverDistrict;
    private String receiverAddress;
    private String receiverZip;
    private Timestamp created;
    private Timestamp updated;

    @Id
    @Column(name = "order_id", nullable = false, length = 50)
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "receiver_name", nullable = true, length = 20)
    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    @Basic
    @Column(name = "receiver_phone", nullable = true, length = 20)
    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    @Basic
    @Column(name = "receiver_mobile", nullable = true, length = 30)
    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    @Basic
    @Column(name = "receiver_state", nullable = true, length = 10)
    public String getReceiverState() {
        return receiverState;
    }

    public void setReceiverState(String receiverState) {
        this.receiverState = receiverState;
    }

    @Basic
    @Column(name = "receiver_city", nullable = true, length = 10)
    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    @Basic
    @Column(name = "receiver_district", nullable = true, length = 20)
    public String getReceiverDistrict() {
        return receiverDistrict;
    }

    public void setReceiverDistrict(String receiverDistrict) {
        this.receiverDistrict = receiverDistrict;
    }

    @Basic
    @Column(name = "receiver_address", nullable = true, length = 200)
    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    @Basic
    @Column(name = "receiver_zip", nullable = true, length = 6)
    public String getReceiverZip() {
        return receiverZip;
    }

    public void setReceiverZip(String receiverZip) {
        this.receiverZip = receiverZip;
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
        TbOrderShipping that = (TbOrderShipping) o;
        return Objects.equals(orderId, that.orderId) &&
                Objects.equals(receiverName, that.receiverName) &&
                Objects.equals(receiverPhone, that.receiverPhone) &&
                Objects.equals(receiverMobile, that.receiverMobile) &&
                Objects.equals(receiverState, that.receiverState) &&
                Objects.equals(receiverCity, that.receiverCity) &&
                Objects.equals(receiverDistrict, that.receiverDistrict) &&
                Objects.equals(receiverAddress, that.receiverAddress) &&
                Objects.equals(receiverZip, that.receiverZip) &&
                Objects.equals(created, that.created) &&
                Objects.equals(updated, that.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, receiverName, receiverPhone, receiverMobile, receiverState, receiverCity, receiverDistrict, receiverAddress, receiverZip, created, updated);
    }
}
