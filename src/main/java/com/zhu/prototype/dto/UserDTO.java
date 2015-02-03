package com.zhu.prototype.dto;

import com.zhu.prototype.entity.User;

public class UserDTO extends User {
	private String password2;

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

}
