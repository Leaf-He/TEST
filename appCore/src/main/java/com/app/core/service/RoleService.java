package com.app.core.service;


import com.app.core.common.ResultVo;
import com.app.core.entity.Permission;
import com.app.core.entity.Role;
import com.app.core.vo.IPage;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoleService {

    public IPage<Role> findRolePage(IPage<Role> rolePage, Role role);

    public List<Role> findRolesByUserId(Long userId);

    /**
     * 校验角色
     * @param role
     * @param isUpdate
     * @return
     */
    public ResultVo validate(Role role, boolean isUpdate);


    public Role save(Role role,String[] permissionIds);

    public void delById(String[] ids);

    public Role findById(String id);


}
