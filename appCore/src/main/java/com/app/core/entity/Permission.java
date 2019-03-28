package com.app.core.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="app_permission")
public class Permission implements Serializable {

    @Id
    @Column(name = "pms_id")
    private String id;

    @Column(name = "pms_name")
    private String name;

    @Column(name = "pms_permission")
    private String permission;

    @Column(name = "pms_parent_id")
    private String parentId;

    @Column(name = "pms_display_order")
    private Integer order;

    @Column(name = "pms_level")
    private Integer level;

    @Column(name = "pms_desc")
    private String desc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
