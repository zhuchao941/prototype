package com.zhu.prototype.service;

import com.zhu.prototype.dto.UserPreferences;
import com.zhu.prototype.entity.User;

public interface UserService {
	public boolean register(User user);

	public User getUserByUsername(String username);
	
	public UserPreferences getUserPreferences(User user);
}
