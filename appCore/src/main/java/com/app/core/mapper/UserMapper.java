package com.app.core.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.app.core.entity.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import java.util.List;
import java.util.Map;

/**
 * Created by lucky on 2017/7/5.
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {
	
    public List<User> findAll();
    
    public List<User> findAll(Pagination page);
    
    public List<User> findWithParams(Pagination page, Map<String, Object> params);
}
