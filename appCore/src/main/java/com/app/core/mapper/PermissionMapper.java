package com.app.core.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by lucky on 2017/7/5.
 */

@Mapper
public interface PermissionMapper<Permission>{


    /**
     * 根据角色ID查询权限
     * @param Map
     * @return
     */
    public List<Permission> findByRoleIds(Map param);


    /**
     *根据角色ID查询权限
     * @param id
     * @return
     */
    public List<Permission> findByRoleId(String id);

    /**
     * 获取全部权限
     * @return
     */
    public List<Permission> findAll();

    /**
     * 根据条件获取权限
     * @return
     */
    public List<Permission> findPermission(Permission permission);

    /**
     * 根据id修改
     * @param permission
     */
    public void updateById(Permission permission);

    /**
     * 根据ID删除
     * @param id
     */
    public void delById(String id);
}
