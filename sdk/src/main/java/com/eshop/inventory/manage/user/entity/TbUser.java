package com.eshop.inventory.manage.user.entity;

import com.eshop.inventory.common.entity.BaseEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author zeryts
 * @description: TODO
 * ```````````````````````````
 * @title: TbUser
 * @projectName inventory
 * @date 2019/5/3021:41
 */
@Entity
@Table(name = "tb_user", schema = "eshop", catalog = "")
public class TbUser extends BaseEntity {
    private long id;
    private String username;
    private String password;
    private String phone;
    private String email;
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
    @Column(name = "username", nullable = false, length = 50)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 32)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "phone", nullable = true, length = 20)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "email", nullable = true, length = 50)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        TbUser tbUser = (TbUser) o;
        return id == tbUser.id &&
                Objects.equals(username, tbUser.username) &&
                Objects.equals(password, tbUser.password) &&
                Objects.equals(phone, tbUser.phone) &&
                Objects.equals(email, tbUser.email) &&
                Objects.equals(created, tbUser.created) &&
                Objects.equals(updated, tbUser.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, phone, email, created, updated);
    }
}
