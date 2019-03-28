package com.app.admin.controller;

import com.app.core.common.ResultVo;
import com.app.core.entity.Role;
import com.app.core.service.RoleService;
import com.app.core.vo.IPage;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/role")
public class RoleController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RoleService roleService;

    @RequiresPermissions(value={"permission:all","permission:search"},logical= Logical.OR)
    @RequestMapping("")
    public String index(){
        return  "admin/system/role/list";
    }


    @RequestMapping(value ="ajax/list",method= RequestMethod.POST)
    @ResponseBody
    public ResultVo list(IPage<Role> iPage){
        ResultVo rusultVo = new ResultVo();
        try {
            iPage = roleService.findRolePage(iPage, null);
            rusultVo.pushDataMap("page", iPage);
            rusultVo.setOperationFlag(ResultVo.OPERATION_FLAG_SUCCESS);
        }catch (Exception e){
            rusultVo.setOperationFlag(ResultVo.OPERATION_FLAG_FAIL);
            e.printStackTrace();
        }
        return rusultVo;
    }

    @RequiresPermissions(value={"permission:all","permission:add"},logical= Logical.OR)
    @RequestMapping(value ="ajax/save",method= RequestMethod.POST)
    @ResponseBody
    public ResultVo save(Role role,@RequestParam(value = "permissionIds[]",required = false) String[] permissionIds){
        ResultVo resultVo =null;
        try {
            resultVo = roleService.validate(role,false);
            if (ResultVo.OPERATION_FLAG_SUCCESS==resultVo.getOperationFlag()){
                roleService.save(role,permissionIds);
                resultVo.setRetMessage("添加角色成功");
            }
        }catch (Exception e){
            resultVo = new ResultVo();
            resultVo.setRetMessage("添加角色失败");
            resultVo.setOperationFlag(ResultVo.OPERATION_FLAG_FAIL);
        }
        return resultVo;
    }

    @RequiresPermissions(value={"permission:all","permission:delete"},logical= Logical.OR)
    @RequestMapping(value ="ajax/del",method= RequestMethod.POST)
    @ResponseBody
    public ResultVo del(@RequestParam(value = "ids[]") String[] ids){
        ResultVo resultVo =new ResultVo();
        try{
            roleService.delById(ids);
            resultVo.setOperationFlag(ResultVo.OPERATION_FLAG_SUCCESS);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            resultVo.setRetMessage("删除角色失败");
            resultVo.setOperationFlag(ResultVo.OPERATION_FLAG_FAIL);
        }
        return resultVo;
    }

    @RequiresPermissions(value={"permission:all","permission:delete"},logical= Logical.OR)
    @RequestMapping(value ="{id}",method= RequestMethod.POST)
    @ResponseBody
    public ResultVo get(@PathVariable("id") String id){
        ResultVo resultVo =new ResultVo();
        try{

            Role role = roleService.findById(id);
            resultVo.pushDataMap("role",role);
            resultVo.setOperationFlag(ResultVo.OPERATION_FLAG_SUCCESS);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            resultVo.setRetMessage("删除角色失败");
            resultVo.setOperationFlag(ResultVo.OPERATION_FLAG_FAIL);
        }
        return resultVo;
    }
}
