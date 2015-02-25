package com.zhu.prototype.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhu.prototype.dao.UserMapper;
import com.zhu.prototype.entity.User;
import com.zhu.prototype.entity.UserExample;
import com.zhu.prototype.service.UserService;
import com.zhu.prototype.utils.CollectionUtils;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;

	@Override
	public boolean register(User user) {
		return userMapper.insert(user) > 0;
	}

	@Override
	public boolean validateUser(User user) {
		if (user == null || user.getUsername() == null
				|| user.getPassword() == null) {
			return false;
		}
		UserExample example = new UserExample();
		example.createCriteria().andUsernameEqualTo(user.getUsername())
				.andPasswordEqualTo(user.getPassword());
		List<User> userList = userMapper.selectByExample(example);
		if(CollectionUtils.isEmpty(userList)){
			return false;
		}
		user = userList.get(0);
		return true;
	}

}
