package com.app.admin.entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="app_admin_menu")
public class AdminMenu {

    @Id
    @Column(name = "menu_id")
    private String menuId;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_order")
    private Integer menuOrder;

    @Column(name = "menu_pid")
    private String menuPid;

    @Column(name = "menu_url")
    private String menuUrl;

    @Transient
    private List<AdminMenu> children; //子菜单

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }

    public String getMenuPid() {
        return menuPid;
    }

    public void setMenuPid(String menuPid) {
        this.menuPid = menuPid;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public List<AdminMenu> getChildren() {
        return children;
    }

    public void setChildren(List<AdminMenu> children) {
        this.children = children;
    }
}
