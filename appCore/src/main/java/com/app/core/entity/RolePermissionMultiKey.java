package com.app.core.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RolePermissionMultiKey implements Serializable {

    private String permissionId;

    private String roleId;

    //Constructor
    public RolePermissionMultiKey() {

    }

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

    //  ***重写hashCode与equals方法***  划重点！
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((permissionId == null) ? 0 : permissionId.hashCode());
        result = PRIME * result + ((roleId == null) ? 0 : roleId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(obj == null){
            return false;
        }
        if(getClass() != obj.getClass()){
            return false;
        }

        final RolePermissionMultiKey other = (RolePermissionMultiKey)obj;
        if(permissionId == null){
            if(other.permissionId != null){
                return false;
            }
        }else if(!permissionId.equals(other.permissionId)){
            return false;
        }
        if(roleId == null){
            if(other.roleId != null){
                return false;
            }
        }else if(!roleId.equals(other.roleId)){
            return false;
        }

        return true;
    }

}
