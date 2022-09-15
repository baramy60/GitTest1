package com.green.user.service;

import java.util.HashMap;
import java.util.List;

import com.green.user.vo.UserVo;

public interface UserService {

	List<UserVo> getUserList();

	UserVo getView(String id);

	List<UserVo> getViewName(String name);

	UserVo login(HashMap<String, Object> map);

}
