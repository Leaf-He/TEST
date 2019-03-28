package com.app.core.dao;


import com.app.core.entity.Permission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 * Created by lucky on 2017/7/4.
 */
@Repository
public interface PermissionDao extends CrudRepository<Permission,String> {

}
