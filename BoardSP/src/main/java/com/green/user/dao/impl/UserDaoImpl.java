package com.green.user.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.user.dao.UserDao;
import com.green.user.vo.UserVo;

@Repository("userDao")
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private SqlSession  sqlSession;

	@Override
	public List<UserVo> getUserList() {
		
		List<UserVo>  userList  =  sqlSession.selectList("User.UserList");
		return  userList;
		
	}

	@Override
	public UserVo getView(String id) {
		UserVo  vo  = sqlSession.selectOne("User.getView", id);
		return  vo;
	}

	@Override
	public List<UserVo> getViewName(String name) {
		
		List<UserVo>  userList  =  sqlSession.selectList("User.getViewName", name);
		
		return     userList;
	}

	@Override
	public UserVo login(HashMap<String, Object> map) {
		System.out.println("userdao map:" + map);
		UserVo  vo  =  sqlSession.selectOne("User.login", map); 
		
		return  vo;
	}

}





