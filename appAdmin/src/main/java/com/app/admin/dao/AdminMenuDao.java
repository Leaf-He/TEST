package com.app.admin.dao;


import com.app.admin.entity.AdminMenu;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lucky on 2017/7/4.
 */
@Repository
public interface AdminMenuDao extends CrudRepository<AdminMenu,String> {

}
