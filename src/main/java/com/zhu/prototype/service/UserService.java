package com.zhu.prototype.service;

import com.zhu.prototype.entity.User;

public interface UserService {
	public boolean register(User user);
	
	public boolean validateUser(User user);
}
