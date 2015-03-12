package com.zhu.prototype.service.impl;

import javax.annotation.Resource;

import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.stereotype.Service;

import com.zhu.prototype.dao.UserMapper;
import com.zhu.prototype.dto.UserPreferences;
import com.zhu.prototype.entity.User;
import com.zhu.prototype.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;

	@Resource
	private PasswordService passwordService;

	@Override
	public boolean register(User user) {
		user.setPassword(passwordService.encryptPassword(user.getPassword()));
		return userMapper.insert(user) > 0;
	}

	@Override
	public User getUserByUsername(String username) {
		return userMapper.selectByPrimaryKey(username);
	}

	@Override
	public UserPreferences getUserPreferences(User user) {
		UserPreferences preferences = new UserPreferences();
		preferences.setUsername(user.getUsername());
		return preferences;
	}
	
	

}
