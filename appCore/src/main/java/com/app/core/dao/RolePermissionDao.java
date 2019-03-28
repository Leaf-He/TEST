package com.app.core.dao;


import com.app.core.entity.RolePermission;
import com.app.core.entity.RolePermissionMultiKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by lucky on 2017/7/4.
 */
@Repository
public interface RolePermissionDao extends CrudRepository<RolePermission,RolePermissionMultiKey> {

    @Transactional
    public Integer deleteByRoleId(String roleId);
}
