package com.zhu.prototype.dto;

import java.io.Serializable;

public class UserPreferences implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
