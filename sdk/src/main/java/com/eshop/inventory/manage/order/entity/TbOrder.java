package com.eshop.inventory.manage.order.entity;

import com.eshop.inventory.common.entity.BaseEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author zeryts
 * @description: TODO
 * ```````````````````````````
 * @title: TbOrder
 * @projectName inventory
 * @date 2019/5/3021:41
 */
@Entity
@Table(name = "tb_order", schema = "eshop", catalog = "")
public class TbOrder extends BaseEntity {
    private String orderId;
    private String payment;
    private Integer paymentType;
    private String postFee;
    private Integer status;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Timestamp paymentTime;
    private Timestamp consignTime;
    private Timestamp endTime;
    private Timestamp closeTime;
    private String shippingName;
    private String shippingCode;
    private Long userId;
    private String buyerMessage;
    private String buyerNick;
    private Integer buyerRate;

    @Id
    @Column(name = "order_id", nullable = false, length = 50)
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "payment", nullable = true, length = 50)
    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    @Basic
    @Column(name = "payment_type", nullable = true)
    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    @Basic
    @Column(name = "post_fee", nullable = true, length = 50)
    public String getPostFee() {
        return postFee;
    }

    public void setPostFee(String postFee) {
        this.postFee = postFee;
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
    @Column(name = "create_time", nullable = true)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "update_time", nullable = true)
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "payment_time", nullable = true)
    public Timestamp getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Timestamp paymentTime) {
        this.paymentTime = paymentTime;
    }

    @Basic
    @Column(name = "consign_time", nullable = true)
    public Timestamp getConsignTime() {
        return consignTime;
    }

    public void setConsignTime(Timestamp consignTime) {
        this.consignTime = consignTime;
    }

    @Basic
    @Column(name = "end_time", nullable = true)
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "close_time", nullable = true)
    public Timestamp getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Timestamp closeTime) {
        this.closeTime = closeTime;
    }

    @Basic
    @Column(name = "shipping_name", nullable = true, length = 20)
    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    @Basic
    @Column(name = "shipping_code", nullable = true, length = 20)
    public String getShippingCode() {
        return shippingCode;
    }

    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode;
    }

    @Basic
    @Column(name = "user_id", nullable = true)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "buyer_message", nullable = true, length = 100)
    public String getBuyerMessage() {
        return buyerMessage;
    }

    public void setBuyerMessage(String buyerMessage) {
        this.buyerMessage = buyerMessage;
    }

    @Basic
    @Column(name = "buyer_nick", nullable = true, length = 50)
    public String getBuyerNick() {
        return buyerNick;
    }

    public void setBuyerNick(String buyerNick) {
        this.buyerNick = buyerNick;
    }

    @Basic
    @Column(name = "buyer_rate", nullable = true)
    public Integer getBuyerRate() {
        return buyerRate;
    }

    public void setBuyerRate(Integer buyerRate) {
        this.buyerRate = buyerRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TbOrder tbOrder = (TbOrder) o;
        return Objects.equals(orderId, tbOrder.orderId) &&
                Objects.equals(payment, tbOrder.payment) &&
                Objects.equals(paymentType, tbOrder.paymentType) &&
                Objects.equals(postFee, tbOrder.postFee) &&
                Objects.equals(status, tbOrder.status) &&
                Objects.equals(createTime, tbOrder.createTime) &&
                Objects.equals(updateTime, tbOrder.updateTime) &&
                Objects.equals(paymentTime, tbOrder.paymentTime) &&
                Objects.equals(consignTime, tbOrder.consignTime) &&
                Objects.equals(endTime, tbOrder.endTime) &&
                Objects.equals(closeTime, tbOrder.closeTime) &&
                Objects.equals(shippingName, tbOrder.shippingName) &&
                Objects.equals(shippingCode, tbOrder.shippingCode) &&
                Objects.equals(userId, tbOrder.userId) &&
                Objects.equals(buyerMessage, tbOrder.buyerMessage) &&
                Objects.equals(buyerNick, tbOrder.buyerNick) &&
                Objects.equals(buyerRate, tbOrder.buyerRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, payment, paymentType, postFee, status, createTime, updateTime, paymentTime, consignTime, endTime, closeTime, shippingName, shippingCode, userId, buyerMessage, buyerNick, buyerRate);
    }
}
