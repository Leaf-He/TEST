package com.app.core.service;

import com.app.core.common.ResultVo;
import com.app.core.entity.Permission;
import com.app.core.vo.Option;
import com.app.core.vo.TreeNode;

import java.util.List;

public interface PermissionService {

    public List<Permission> findPermissionByRoleIds(List<String> roleIds);

    /**
     * 获取全部权限节点树数据
     * @return
     */
    public List<TreeNode> getPermissionTreeNode();

    public List<Option> getPermissionOption();

    public List<Permission> findPermission(Permission permission);

    public Permission savePermission(Permission permission);

    /**
     * 校验权限
     * @param permission
     * @return
     */
    public ResultVo validate(Permission permission,boolean isUpdate);

    /**
     * 更新权限
     * @param permission
     */
    public void updatePermission(Permission permission);

    /**
     * 删除权限
     * @param ids
     */
    public void delPermission(String[] ids);


}
