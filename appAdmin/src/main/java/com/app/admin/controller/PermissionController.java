package com.app.admin.controller;

import com.app.core.common.ResultVo;
import com.app.core.entity.Permission;
import com.app.core.service.PermissionService;
import com.app.core.vo.Option;
import com.app.core.vo.TreeNode;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Controller
@RequestMapping("/admin/permission")
public class PermissionController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PermissionService permissionService;

    @RequiresPermissions(value={"permission:all","permission:search"},logical= Logical.OR)
    @RequestMapping("")
     public String index(){
        return  "admin/system/permission/list";
     }

    /**
     * 获取权限树数据
     * @return
     */
    @RequiresPermissions(value={"permission:all","permission:search"},logical= Logical.OR)
    @RequestMapping(value ="ajax/treeList",method= RequestMethod.POST)
    @ResponseBody
    public List<TreeNode> treeList(){
        List<TreeNode> treeNodes = null;
        try {
            treeNodes = permissionService.getPermissionTreeNode();
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return treeNodes;
    }

    @RequestMapping(value ="ajax/permissionOptions",method= RequestMethod.POST)
    @ResponseBody
    public ResultVo permissionOptions(){
        ResultVo rusultVo = new ResultVo();
        try {
            List<Option> options = permissionService.getPermissionOption();
            rusultVo.pushDataMap("options",options);
            rusultVo.setOperationFlag(ResultVo.OPERATION_FLAG_SUCCESS);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            rusultVo.setOperationFlag(ResultVo.OPERATION_FLAG_FAIL);
        }
        return rusultVo;
    }

    @RequestMapping(value ="ajax/validatePermission",method= RequestMethod.POST)
    @ResponseBody
    public ResultVo findPermission(Permission permission){
        ResultVo rusultVo = new ResultVo();
        rusultVo.pushDataMap("exist",false);
        try {
            List<Permission> list = permissionService.findPermission(permission);
            if(null!=list && list.size()>0){
                if (!StringUtils.isEmpty(permission.getId())){
                    for(Permission p : list){
                        if(p.getId().equals(permission.getId())){
                            continue;
                        }else {
                            rusultVo.pushDataMap("exist",true);
                            return rusultVo;
                        }
                    }
                }else {
                    rusultVo.pushDataMap("exist",true);
                }
            }
            rusultVo.setOperationFlag(ResultVo.OPERATION_FLAG_SUCCESS);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            rusultVo.setOperationFlag(ResultVo.OPERATION_FLAG_FAIL);
        }
        return rusultVo;
    }

    @RequiresPermissions(value={"permission:all","permission:add"},logical= Logical.OR)
    @RequestMapping(value ="ajax/save",method= RequestMethod.POST)
    @ResponseBody
    public ResultVo save(Permission permission){
        ResultVo resultVo =null;
        try {
            resultVo = permissionService.validate(permission,false);
            if (ResultVo.OPERATION_FLAG_SUCCESS==resultVo.getOperationFlag()){
                permissionService.savePermission(permission);
                resultVo.pushDataMap("addPermission",permission);
                resultVo.setRetMessage("添加权限成功");
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            resultVo = new ResultVo();
            resultVo.setRetMessage("添加权限失败");
            resultVo.setOperationFlag(ResultVo.OPERATION_FLAG_FAIL);
        }
        return resultVo;
    }

    @RequiresPermissions(value={"permission:all","permission:edit"},logical= Logical.OR)
    @RequestMapping(value ="ajax/update",method= RequestMethod.POST)
    @ResponseBody
    public ResultVo update(Permission permission){
        ResultVo resultVo =null;
        try {
            resultVo = permissionService.validate(permission,true);
            if (ResultVo.OPERATION_FLAG_SUCCESS==resultVo.getOperationFlag()){
                permissionService.updatePermission(permission);
                resultVo.pushDataMap("updatePermission",permission);
                resultVo.setRetMessage("修改权限成功");
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            resultVo = new ResultVo();
            resultVo.setRetMessage("修改权限失败");
            resultVo.setOperationFlag(ResultVo.OPERATION_FLAG_FAIL);
        }
        return resultVo;
    }

    @RequiresPermissions(value={"permission:all","permission:delete"},logical= Logical.OR)
    @RequestMapping(value ="ajax/del",method= RequestMethod.POST)
    @ResponseBody
    public ResultVo delPermission(@RequestParam(value = "ids[]") String[] ids){
        ResultVo resultVo =new ResultVo();
        try{
            permissionService.delPermission(ids);
            resultVo.setOperationFlag(ResultVo.OPERATION_FLAG_SUCCESS);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            resultVo.setRetMessage("删除权限失败");
            resultVo.setOperationFlag(ResultVo.OPERATION_FLAG_FAIL);
        }
        return resultVo;
    }

}
