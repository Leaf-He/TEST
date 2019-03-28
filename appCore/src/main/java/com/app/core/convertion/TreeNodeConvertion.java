package com.app.core.convertion;

import com.app.core.entity.Permission;
import com.app.core.vo.TreeNode;

import java.util.HashMap;
import java.util.Map;

public class TreeNodeConvertion {

    public static TreeNode permission2TreeNode(Permission permission){
        TreeNode node = new TreeNode();
        node.setId (permission.getId());
        node.setName(permission.getName());
        node.setpId(permission.getParentId());
        Map dataMap = new HashMap<String,Object>();
        dataMap.put("permission",permission.getPermission());
        dataMap.put("order",permission.getOrder());
        dataMap.put("desc",permission.getDesc());
        dataMap.put("level",permission.getLevel());
        node.setDataMap(dataMap);
        return node;
    }
}
