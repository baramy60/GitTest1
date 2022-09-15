package com.green.user.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.user.dao.UserDao;
import com.green.user.service.UserService;
import com.green.user.vo.UserVo;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private  UserDao  userDao;
	
	@Override
	public List<UserVo> getUserList() {
		
		List<UserVo> userList =  userDao.getUserList();
		return    userList;
		
	}

	@Override
	public UserVo getView(String id) {
		UserVo  vo  =  userDao.getView( id ); 
		return  vo;
	}

	@Override
	public List<UserVo> getViewName(String name) {
		
		List<UserVo> userList = userDao.getViewName( name );
		return       userList;
		
	}

	@Override
	public UserVo login(HashMap<String, Object> map) {
		UserVo    vo   =   userDao.login( map ); 
		return    vo;
	}

}





