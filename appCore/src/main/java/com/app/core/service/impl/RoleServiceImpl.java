package com.app.core.service.impl;

import com.app.core.common.ResultVo;
import com.app.core.dao.RoleDao;
import com.app.core.dao.RolePermissionDao;
import com.app.core.entity.Role;
import com.app.core.entity.RolePermission;
import com.app.core.mapper.PermissionMapper;
import com.app.core.mapper.RoleMapper;
import com.app.core.service.RoleService;
import com.app.core.vo.IPage;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lucky on 2017/7/4.
 */
@Service
public class RoleServiceImpl  extends ServiceImpl<RoleMapper, Role> implements RoleService {


    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Role> findRolesByUserId(Long userId){
        return baseMapper.findByUserId(userId);
    }


    @Override
    public IPage<Role> findRolePage(IPage<Role> rolePage , Role role ) {
        if(null!=role){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("id",role.getId());
            map.put("description",role.getDescription());
            map.put("name",role.getName());
            map.put("state",role.getState());
            rolePage.setCondition(map);
        }
        rolePage.setRecords(baseMapper.findPage(rolePage,rolePage.getCondition()));
        return rolePage;
    }

    @Override
    public ResultVo validate(Role role, boolean isUpdate) {
        ResultVo resultVo = new ResultVo();
        resultVo.setOperationFlag(ResultVo.OPERATION_FLAG_SUCCESS);
        if(StringUtils.isEmpty(role.getName())){
            resultVo.setOperationFlag(ResultVo.OPERATION_FLAG_FAIL);
            resultVo.setRetMessage("角色名称不能为空");
            return resultVo;
        }
        if(null==role.getState()){
            resultVo.setOperationFlag(ResultVo.OPERATION_FLAG_FAIL);
            resultVo.setRetMessage("角色状态为空");
            return resultVo;
        }

        List<Role> list = roleDao.findByName(role.getName());
        if(null!=list && list.size()>0){
            if(isUpdate){
                for(Role r : list){
                    if(r.getId().equals(r.getId())){
                        continue;
                    }else {
                        resultVo.setOperationFlag(ResultVo.OPERATION_FLAG_FAIL);
                        resultVo.setRetMessage("已存在角色名称");
                        return resultVo;
                    }
                }
            }else{
                resultVo.setOperationFlag(ResultVo.OPERATION_FLAG_FAIL);
                resultVo.setRetMessage("已存在角色名称");
                return resultVo;
            }

        }
        return resultVo;
    }

    @Override
    public Role save(Role role,String[] permissionIds) {
        roleDao.save(role);
        if(null!=permissionIds && permissionIds.length>0){
            for(String id : permissionIds){
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleId(role.getId());
                rolePermission.setPermissionId(id);
                rolePermissionDao.save(rolePermission);
            }
        }
        return role;
    }

    @Override
    @Transactional
    public void delById(String[] ids) {
        if(null!=ids && ids.length>0){
            for(String id : ids){
                roleDao.delete(id);
                rolePermissionDao.deleteByRoleId(id);
            }
        }
    }

    @Override
    public Role findById(String id) {
        Role role = roleDao.findOne(id);
        if(null!=role){
            role.setPermissions(permissionMapper.findByRoleId(id));
        }
        return role;
    }
}
