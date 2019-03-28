package com.app.core.service;

import com.app.core.entity.User;
import com.baomidou.mybatisplus.plugins.Page;


import java.util.List;

public interface UserService {

    public List<User> findAll();

   // public PageInfo findAll(Integer  pageNum , Integer pageSize);

    public User findByUserName(String userName);
    
    public Page<User> findAll(Page<User> page);
    
    public Page<User> findWithParams(Page<User> page);
}
