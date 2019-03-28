package com.app.admin.service.impl;

import com.app.admin.constant.AdminDictConstant;
import com.app.admin.dao.AdminMenuDao;
import com.app.admin.entity.AdminMenu;
import com.app.admin.mapper.AdminMenuMapper;
import com.app.admin.service.AdminMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucky on 2017/7/4.
 */
@Service
public class AdminMenuServiceImpl implements AdminMenuService {

    @Resource
    private AdminMenuMapper adminMenuMapper;

    @Autowired
    private AdminMenuDao adminMenuDao;

    @Override
    public List<AdminMenu> findAllMenu(boolean showRoot) {
        List<AdminMenu> menusList = adminMenuMapper.findAll();
        AdminMenu root = getMenuRoot();
        List<AdminMenu> treeMenusList = new ArrayList<AdminMenu>();
        sortMenu(root,menusList,treeMenusList);
        if(null!=treeMenusList && treeMenusList.size()>0){
            if(!showRoot){
                treeMenusList = treeMenusList.get(0).getChildren();
            }
        }
        return treeMenusList;
    }

    private AdminMenu getMenuRoot(){
       return adminMenuDao.findOne(AdminDictConstant.ROOT_MENU_ID);
    }

    private void sortMenu(AdminMenu root , List<AdminMenu> allMenusList , List<AdminMenu> treeMenusList){

        if(null==root){
            return;
        }
        if(null!=allMenusList && allMenusList.size()>0){
            if(treeMenusList.size()<=0){
                treeMenusList.add(root);
            }
            List<AdminMenu> children = new ArrayList<AdminMenu>();
            for(AdminMenu menu : allMenusList){
                if(root.getMenuId().equals(menu.getMenuPid())){
                    children.add(menu);
                }
            }
            root.setChildren(children);
            allMenusList.removeAll(children);
            for(AdminMenu child : children){
                sortMenu(child,allMenusList,treeMenusList);
            }
        }
    }
}
