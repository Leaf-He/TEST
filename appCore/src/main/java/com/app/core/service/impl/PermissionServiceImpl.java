package com.app.core.service.impl;

import com.app.core.common.CommonUtil;
import com.app.core.common.ResultVo;
import com.app.core.convertion.TreeNodeConvertion;
import com.app.core.dao.PermissionDao;
import com.app.core.entity.Permission;
import com.app.core.mapper.PermissionMapper;
import com.app.core.service.PermissionService;
import com.app.core.vo.Option;
import com.app.core.vo.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.util.*;

/**
 * Created by lucky on 2017/7/4.
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;

    @Autowired
    private PermissionDao permissionDao;

    /**
     * 根据角色ID查询权限
     * @param roleIds
     * @return
     */
    public List<Permission> findPermissionByRoleIds(List<String> roleIds){
        Map param = new HashMap<String,Object>();
        param.put("roleIds",roleIds);
        return permissionMapper.findByRoleIds(param);
    }

    @Override
    public List<TreeNode> getPermissionTreeNode() {
        //按照等级、显示ID排序
        List<Permission> permissions = permissionMapper.findAll();
        List<TreeNode> allNodeList = null;
        if(null!=permissions && permissions.size()>0){
            allNodeList = new ArrayList<TreeNode>();
            // 根节点等级
           // Integer rootLevel = null;
            // 根据结果集构造节点列表（存入散列表）
            for (Permission p : permissions) {
                TreeNode node = TreeNodeConvertion.permission2TreeNode(p);
                /*
                if(null==rootLevel){
                    node.setParent(true);
                    //由于是按照等级 显示ID排序 那么第一个节点肯定是根节点
                    rootLevel=p.getLevel();
                }else if(rootLevel==p.getLevel()){
                    node.setParent(true);
                }
                */
                allNodeList.add(node);
            }
        }
        return allNodeList;
    }

    @Override
    public List<Option> getPermissionOption() {
        List<Option> options = null;
        List<Permission> list = permissionMapper.findAll();
        if(null!=list && list.size()>0){
            options = new ArrayList<Option>();
            for(Permission p : list){
                Option option = new Option();
                option.setId(p.getId());
                option.setText(p.getName());
                options.add(option);
            }
        }
        return options;
    }

    @Override
    public List<Permission> findPermission(Permission permission) {
        return permissionMapper.findPermission(permission);
    }

    /**
     * 新增或更新校验权限
     * @param permission
     * @return
     */
    @Override
    public ResultVo validate(Permission permission,boolean isUpdate){
        ResultVo resultVo = new ResultVo();
        resultVo.setOperationFlag(ResultVo.OPERATION_FLAG_SUCCESS);
        if(StringUtils.isEmpty(permission.getName())){
            resultVo.setOperationFlag(ResultVo.OPERATION_FLAG_FAIL);
            resultVo.setRetMessage("权限名称不能为空");
            return resultVo;
        }
        if(StringUtils.isEmpty(permission.getPermission())){
            resultVo.setOperationFlag(ResultVo.OPERATION_FLAG_FAIL);
            resultVo.setRetMessage("权限不能为空");
            return resultVo;
        }
        if(StringUtils.isEmpty(permission.getParentId())){
            resultVo.setOperationFlag(ResultVo.OPERATION_FLAG_FAIL);
            resultVo.setRetMessage("父权限不能为空");
            return resultVo;
        }
        Permission param = new Permission();
        param.setName(permission.getName());
        List<Permission> list = findPermission(param);
        if(null!=list && list.size()>0){
            if(isUpdate){
                for(Permission p : list){
                    if(p.getId().equals(permission.getId())){
                        continue;
                    }else {
                        resultVo.setOperationFlag(ResultVo.OPERATION_FLAG_FAIL);
                        resultVo.setRetMessage("已存在权限名称");
                        return resultVo;
                    }
                }
            }else{
                resultVo.setOperationFlag(ResultVo.OPERATION_FLAG_FAIL);
                resultVo.setRetMessage("已存在权限名称");
                return resultVo;
            }

        }

        param = new Permission();
        param.setPermission(permission.getPermission());
        list = findPermission(param);
        if(null!=list && list.size()>0){
            if(isUpdate){
                for(Permission p : list){
                    if(p.getId().equals(permission.getId())){
                        continue;
                    }else {
                        resultVo.setOperationFlag(ResultVo.OPERATION_FLAG_FAIL);
                        resultVo.setRetMessage("已存在权限名称");
                        return resultVo;
                    }
                }
            }else{
                resultVo.setOperationFlag(ResultVo.OPERATION_FLAG_FAIL);
                resultVo.setRetMessage("已存在相同权限");
                return resultVo;
            }
        }

        return resultVo;
    }

    @Override
    public Permission savePermission(Permission permission) {
        permission.setId(CommonUtil.genUUID());
        if(null==permission.getOrder() || 0==permission.getOrder()){
            permission.setOrder(1);
        }
        permissionDao.save(permission);
        return permission;
    }

    @Override
    public void updatePermission(Permission permission) {
        permissionMapper.updateById(permission);
    }

    @Transactional
    @Override
    public void delPermission(String[] ids) {
        if(null!=ids && ids.length>0){
            for(int i=0;i<ids.length;i++){
                permissionMapper.delById(ids[i]);
            }
        }
    }
}
