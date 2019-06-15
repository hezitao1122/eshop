package com.eshop.inventory.manage.item.dto;

import com.eshop.inventory.common.dto.BaseDTO;

import java.sql.Timestamp;

/**
 * @author zeryts
 * @description: DTO的传输类
 * ```````````````````````````
 * @title: TbItemDTO
 * @projectName inventory
 * @date 2019/6/12 22:29
 */
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

    public long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getSellPoint() {
        return this.sellPoint;
    }

    public long getPrice() {
        return this.price;
    }

    public int getNum() {
        return this.num;
    }

    public String getBarcode() {
        return this.barcode;
    }

    public String getImage() {
        return this.image;
    }

    public long getCid() {
        return this.cid;
    }

    public byte getStatus() {
        return this.status;
    }

    public Timestamp getCreated() {
        return this.created;
    }

    public Timestamp getUpdated() {
        return this.updated;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSellPoint(String sellPoint) {
        this.sellPoint = sellPoint;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof TbItemDTO)) return false;
        final TbItemDTO<?, ?> other = (TbItemDTO<?, ?>) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getId() != other.getId()) return false;
        final Object this$title = this.getTitle();
        final Object other$title = other.getTitle();
        if (this$title == null ? other$title != null : !this$title.equals(other$title)) return false;
        final Object this$sellPoint = this.getSellPoint();
        final Object other$sellPoint = other.getSellPoint();
        if (this$sellPoint == null ? other$sellPoint != null : !this$sellPoint.equals(other$sellPoint)) return false;
        if (this.getPrice() != other.getPrice()) return false;
        if (this.getNum() != other.getNum()) return false;
        final Object this$barcode = this.getBarcode();
        final Object other$barcode = other.getBarcode();
        if (this$barcode == null ? other$barcode != null : !this$barcode.equals(other$barcode)) return false;
        final Object this$image = this.getImage();
        final Object other$image = other.getImage();
        if (this$image == null ? other$image != null : !this$image.equals(other$image)) return false;
        if (this.getCid() != other.getCid()) return false;
        if (this.getStatus() != other.getStatus()) return false;
        final Object this$created = this.getCreated();
        final Object other$created = other.getCreated();
        if (this$created == null ? other$created != null : !this$created.equals(other$created)) return false;
        final Object this$updated = this.getUpdated();
        final Object other$updated = other.getUpdated();
        if (this$updated == null ? other$updated != null : !this$updated.equals(other$updated)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof TbItemDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $id = this.getId();
        result = result * PRIME + (int) ($id >>> 32 ^ $id);
        final Object $title = this.getTitle();
        result = result * PRIME + ($title == null ? 43 : $title.hashCode());
        final Object $sellPoint = this.getSellPoint();
        result = result * PRIME + ($sellPoint == null ? 43 : $sellPoint.hashCode());
        final long $price = this.getPrice();
        result = result * PRIME + (int) ($price >>> 32 ^ $price);
        result = result * PRIME + this.getNum();
        final Object $barcode = this.getBarcode();
        result = result * PRIME + ($barcode == null ? 43 : $barcode.hashCode());
        final Object $image = this.getImage();
        result = result * PRIME + ($image == null ? 43 : $image.hashCode());
        final long $cid = this.getCid();
        result = result * PRIME + (int) ($cid >>> 32 ^ $cid);
        result = result * PRIME + this.getStatus();
        final Object $created = this.getCreated();
        result = result * PRIME + ($created == null ? 43 : $created.hashCode());
        final Object $updated = this.getUpdated();
        result = result * PRIME + ($updated == null ? 43 : $updated.hashCode());
        return result;
    }

    public String toString() {
        return "TbItemDTO(id=" + this.getId() + ", title=" + this.getTitle() + ", sellPoint=" + this.getSellPoint() + ", price=" + this.getPrice() + ", num=" + this.getNum() + ", barcode=" + this.getBarcode() + ", image=" + this.getImage() + ", cid=" + this.getCid() + ", status=" + this.getStatus() + ", created=" + this.getCreated() + ", updated=" + this.getUpdated() + ")";
    }
}
