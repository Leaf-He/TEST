package com.app.core.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.core.dao.UserDao;
import com.app.core.entity.User;
import com.app.core.mapper.UserMapper;
import com.app.core.service.UserService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

/**
 * Created by lucky on 2017/7/4.
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserDao userDao;

    public List<User> findAll(){
        return userDao.findAll();
    }

    /*
    public PageInfo findAll(Integer  pageNum , Integer pageSize){
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = baseMapper.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }
    */

    public User findByUserName(String userName){
        return userDao.findByUserName(userName);
    }
    
    @Override
	public Page<User> findAll(Page<User> page) {

		return page.setRecords(baseMapper.findAll(page));
	}

	@Override
	public Page<User> findWithParams(Page<User> page) {
		
		return page.setRecords(baseMapper.findWithParams(page, page.getCondition()));
	}

}
