package com.eshopinventory.inventory.manage.content.entity;

import com.eshopinventory.inventory.common.entity.BaseEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author Administrator
 * @description: TODO
 * ```````````````````````````
 * @title: TbContent
 * @projectName inventory
 * @date 2019/5/3021:41
 */
@Entity
@Table(name = "tb_content", schema = "eshop", catalog = "")
public class TbContent extends BaseEntity {
    private long id;
    private long categoryId;
    private String title;
    private String subTitle;
    private String titleDesc;
    private String url;
    private String pic;
    private String pic2;
    private String content;
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
    @Column(name = "category_id", nullable = false)
    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
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
    @Column(name = "sub_title", nullable = true, length = 100)
    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    @Basic
    @Column(name = "title_desc", nullable = true, length = 500)
    public String getTitleDesc() {
        return titleDesc;
    }

    public void setTitleDesc(String titleDesc) {
        this.titleDesc = titleDesc;
    }

    @Basic
    @Column(name = "url", nullable = true, length = 500)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "pic", nullable = true, length = 300)
    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Basic
    @Column(name = "pic2", nullable = true, length = 300)
    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    @Basic
    @Column(name = "content", nullable = true, length = -1)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        TbContent tbContent = (TbContent) o;
        return id == tbContent.id &&
                categoryId == tbContent.categoryId &&
                Objects.equals(title, tbContent.title) &&
                Objects.equals(subTitle, tbContent.subTitle) &&
                Objects.equals(titleDesc, tbContent.titleDesc) &&
                Objects.equals(url, tbContent.url) &&
                Objects.equals(pic, tbContent.pic) &&
                Objects.equals(pic2, tbContent.pic2) &&
                Objects.equals(content, tbContent.content) &&
                Objects.equals(created, tbContent.created) &&
                Objects.equals(updated, tbContent.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categoryId, title, subTitle, titleDesc, url, pic, pic2, content, created, updated);
    }
}
