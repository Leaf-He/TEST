package com.app.admin.service;

import com.app.admin.entity.AdminMenu;
import java.util.List;

public interface AdminMenuService {

    /**
     * 获取全部菜单
     * @param showRoot 是否显示根节点
     * @return
     */
    List<AdminMenu> findAllMenu(boolean showRoot);

}
