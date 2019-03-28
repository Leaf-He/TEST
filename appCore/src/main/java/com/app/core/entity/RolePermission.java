package com.app.core.entity;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="app_role_permission")
@IdClass(RolePermissionMultiKey.class)
public class RolePermission   implements Serializable {

    @Id
    @Column(name = "permission_id")
    private String permissionId;

    @Id
    @Column(name = "role_id")
    private String roleId;


    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
