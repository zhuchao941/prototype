package com.zhu.prototype.dto;

import java.io.Serializable;

public class UserPreferences implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String email;
	private String username;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
