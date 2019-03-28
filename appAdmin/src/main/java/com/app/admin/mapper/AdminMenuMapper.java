package com.app.admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface AdminMenuMapper<AdminMenu> {

    public List<AdminMenu> findAll();
}
