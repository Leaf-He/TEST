package com.app.core.dao;


import com.app.core.entity.Role;
import com.app.core.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by lucky on 2017/7/4.
 */
@Repository
public interface RoleDao extends CrudRepository<Role,String> {

    List<Role> findByName(String roleName);
}
