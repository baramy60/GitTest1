package com.green.user.dao;

import java.util.HashMap;
import java.util.List;

import com.green.user.vo.UserVo;

public interface UserDao {

	List<UserVo> getUserList();

	UserVo getView(String id);

	List<UserVo> getViewName(String name);

	UserVo login(HashMap<String, Object> map);

}
